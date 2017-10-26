package edu.sjsu.cmpe275.aop.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;  // if needed

import edu.sjsu.cmpe275.aop.exceptions.NetworkException;

@Aspect
public class RetryAspect{

	private static int retryNum = 0;
	@Around("execution(public void edu.sjsu.cmpe275.aop.BlogService.*(..))")
	public void retryAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		
		System.out.printf("Before execution of the Method ", joinPoint.getSignature().getName());

		for(int retryNum=1;retryNum<4;++retryNum)
		{
			try
			{
				joinPoint.proceed();
				System.out.println("Method "+ joinPoint.getSignature().getName() + " invoked successfully");
				break;
			}
			catch(NetworkException e)
			{
				if(retryNum==3)
					throw new NetworkException("Failed on final retry!!...on the" + retryNum +" time");
			}

			System.out.println("Retrying for the " + retryNum + " time");
		}
	}
}
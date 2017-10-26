package edu.sjsu.cmpe275.aop.aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;  // if needed
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;

import edu.sjsu.cmpe275.aop.BlogService;
import edu.sjsu.cmpe275.aop.exceptions.AccessDeniedExeption;

@Aspect
public class ValidationAspect{
	
	@Before("execution(public * edu.sjsu.cmpe275.aop.BlogService.readBlog(..)) && args(userId,blogUserId)")
	public void validateReadBlog(JoinPoint jp,String userId, String blogUserId)
	{
		if(userId!=null && blogUserId!=null)
		{
			if(!userId.isEmpty() && !blogUserId.isEmpty())
			{
				System.out.println("Read blog User Id Validation");

				if(userId.length()<3 || userId.length()>16)
				{
					throw new IllegalArgumentException("User id invalid.Length is less than 3 or greater than 16");
				}
				if(blogUserId.length()<3 || blogUserId.length()>16)
					throw new IllegalArgumentException("Blog User Id invalid.Length is less than 3 or greater than 16");

				System.out.println("User Validated successfully");	
			}
			else
				throw new IllegalArgumentException("Blog User Id invalid.Length is less than 3 or greater than 16");
		}
		else
			throw new IllegalArgumentException("Blog User Id invalid.Length is less than 3 or greater than 16");
	}

	@Before("execution(public void edu.sjsu.cmpe275.aop.BlogService.unshareBlog(..)) && args(userId,targetUserId)")
	public void validateUnshareBlog(JoinPoint jp,String userId, String targetUserId)
	{
		if(userId!=null && targetUserId!=null)
		{
			if(!userId.isEmpty() && !targetUserId.isEmpty())
			{
				System.out.println("Unshare blog User Id Validation");
				if(userId.length()<3 || userId.length()>16)
				{
					throw new IllegalArgumentException("User id invalid.Length is less than 3 or greater than 16");

				}
				if(targetUserId.length()<3 || targetUserId.length()>16)
					throw new IllegalArgumentException("Target User id invalid.Length is less than 3 or greater than 16");
				System.out.println("User Validated successfully");
			}
			else
				throw new IllegalArgumentException("Target User id invalid.Length is less than 3 or greater than 16");
		}
		else
			throw new IllegalArgumentException("Target User id invalid.Length is less than 3 or greater than 16");
	}

	@Before("execution(public void edu.sjsu.cmpe275.aop.BlogService.shareBlog(..)) && args(userId,blogUserId,targetUserId)")
	public void validateShareBlog(JoinPoint jp,String userId, String blogUserId, String targetUserId)
	{
		if(userId!=null && targetUserId!=null && blogUserId!=null)
		{
			if(!userId.isEmpty() && !targetUserId.isEmpty() && !blogUserId.isEmpty())
			{
				System.out.println("Share blog User Id Validation");
				if(userId.length()<3 || blogUserId.length()>16)
				{
					throw new IllegalArgumentException("User id invalid.Length is less than 3 or greater than 16");

				}
				if(blogUserId.length()<3 || blogUserId.length()>16)
					throw new IllegalArgumentException("User id invalid.Length is less than 3 or greater than 16");
				System.out.println("User Validated successfully");
			}
			else
				throw new IllegalArgumentException("User id invalid.Length is less than 3 or greater than 16");
		}
		else
			throw new IllegalArgumentException("User id invalid.Length is less than 3 or greater than 16");
		
	}

	@Before("execution(public void edu.sjsu.cmpe275.aop.BlogService.commentOnBlog(..)) && args(userId,blogUserId,message)")
	public void validateCommentBlog(JoinPoint jp,String userId, String blogUserId, String message) throws AccessDeniedExeption, IllegalAccessException
	{
		if(userId!=null && blogUserId!=null)
		{
			if(!userId.isEmpty() && !blogUserId.isEmpty())
			{
				System.out.println("Comment blog User Id Validation");

				if((userId.toString().length()<3 || userId.toString().length()>16) || (blogUserId.toString().length()<3 || blogUserId.toString().length()>16))
				{
					throw new IllegalArgumentException(" User id invalid.Length is less than 3 or greater than 16");
				}
				if(blogUserId.toString().length()<3 || blogUserId.toString().length()>16)
					throw new IllegalAccessException("user id invalid.");
			}
			else
				throw new IllegalAccessException("user id invalid.");
		}
		else
			throw new IllegalAccessException("user id invalid.");
		
		if(message!=null)
		{
			if(message.length()>100 || message.isEmpty())
			{
				throw new IllegalArgumentException(" message length not valid");
			}
		}
		else
			throw new IllegalArgumentException(" message length not valid");
		
		System.out.println("User Validated successfully");
	}

}
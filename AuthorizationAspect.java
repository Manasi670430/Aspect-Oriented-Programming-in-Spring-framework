package edu.sjsu.cmpe275.aop.aspect;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;  // if needed
import org.aspectj.lang.annotation.Before;
import edu.sjsu.cmpe275.aop.exceptions.AccessDeniedExeption;
import edu.sjsu.cmpe275.aop.exceptions.NetworkException;


@Aspect
public class AuthorizationAspect{

	private java.util.List<String> sharedInfoList = new ArrayList<String>();

	@Before("execution(public void edu.sjsu.cmpe275.aop.BlogService.shareBlog(..)) && args(userId,blogUserId,targetUserId)")
	public void shareBlogAuthAdvice(JoinPoint jp,String userId, String blogUserId, String targetUserId) throws AccessDeniedExeption, NetworkException, AccessDeniedException
	{
		System.out.println("****Inside share Blog Advice of Authorization Aspect****");
		if(userId!=null && blogUserId!=null && targetUserId!=null)
		{
			if(!blogUserId.isEmpty() && !userId.isEmpty() && !targetUserId.isEmpty())
			{
				if(blogUserId.equalsIgnoreCase(userId))
				{
					sharedInfoList.add(blogUserId+":"+targetUserId+":true");
				}
				else
				{
					if(sharedInfoList.contains(blogUserId+":"+userId+":true"))
					{
						sharedInfoList.add(blogUserId+":"+targetUserId+":true");
					}
					else
						throw new AccessDeniedExeption(userId + " can't share blog of "+ blogUserId + " with "+ targetUserId);
				}
			}
		}
	}

	@Before("execution(public * edu.sjsu.cmpe275.aop.BlogService.readBlog(..)) && args(userId,blogUserId)")
	public void readBlogAuthAdvice(JoinPoint jp, String userId, String blogUserId) throws AccessDeniedExeption
	{
		if(userId!=null && blogUserId!=null)
		{
			if(!blogUserId.isEmpty() && !userId.isEmpty())
			{
				System.out.println("****Inside Read Blog Advice of Authorization Aspect****");

				if(!userId.isEmpty() || !blogUserId.isEmpty())
				{
					if(sharedInfoList.contains(blogUserId+":"+userId+":true"))
					{
						System.out.println(userId + "can read the blog of " +blogUserId);
					}
					else
						throw new AccessDeniedExeption(userId + "can't read the blog of " +blogUserId + "as it is not shared");
				}
			}
		}
		
	}

	@Before("execution(public void edu.sjsu.cmpe275.aop.BlogService.unshareBlog(..)) && args(userId,targetUserId)")
	public void unshareBlogAuthAdvice(JoinPoint jp, String userId, String targetUserId) throws AccessDeniedExeption
	{
		if(userId!=null && targetUserId!=null)
		{
			if(!userId.isEmpty() && !targetUserId.isEmpty())
			{
				System.out.println("****Inside unshare Blog Advice of Authorization Aspect****");

				if(sharedInfoList.contains(userId+":"+ targetUserId+":true"))
				{
					System.out.println(userId +"can unshare his blog");
					sharedInfoList.remove(userId+":"+ targetUserId+":true");
				}
				else
					throw new AccessDeniedExeption("can't unshare a blog which doesnt belong to him/her");
			}
		}
		
	}
	
	@Before("execution(public void edu.sjsu.cmpe275.aop.BlogServiceImpl.commentOnBlog(..)) && args(userId,blogUserId,message)")
	public void commentBlogAuthAdvice(JoinPoint jp, String userId, String blogUserId, String message) throws AccessDeniedExeption
	{
		if(userId!=null && blogUserId!=null)
		{
			if(!userId.isEmpty() && !blogUserId.isEmpty())
			{
				System.out.println("****Inside Comment Blog Advice  of Authorization Aspect****");
					if(sharedInfoList.contains(blogUserId+":"+ userId+":true"))
					{
						System.out.println(userId+ "can comment on the blog");
					}
					else
						throw new AccessDeniedExeption("can't comment a blog which is not shared with him/her");
			}
		}
	}
}

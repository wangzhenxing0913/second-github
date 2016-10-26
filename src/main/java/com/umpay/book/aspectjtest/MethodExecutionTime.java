package com.umpay.book.aspectjtest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class MethodExecutionTime  {
	@Around("execution(* *.*(..)) && @annotation(com.umpay.book.aspectjtest.MonitorMethod)")
	    public Object profile(ProceedingJoinPoint  pjp) throws Throwable {
		 System.err.println("hah");
		 return pjp.proceed();
	 }
}

package com.io.api.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class RepositoryAspect {

  private static final Logger logger = LoggerFactory.getLogger(RepositoryAspect.class);

  @Value("${application.repository.query-limit-warning-ms:30}")
  private int executionLimitMs;

  @Around("execution(* com.io.api.repository.*.*.*(..))")
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();
    Object proceed = joinPoint.proceed();
    long executionTime = System.currentTimeMillis() - start;
    String message = joinPoint.getSignature() + " exec in " + executionTime + " ms";
    if (executionTime >= executionLimitMs) {
      logger.warn(message + " : SLOW QUERY");
    }
    return proceed;
  }
}

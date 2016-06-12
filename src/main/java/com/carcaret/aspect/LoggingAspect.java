package com.carcaret.aspect;

import com.carcaret.web.ContextHolder;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
  private final static Logger LOG = LoggerFactory.getLogger(LoggingAspect.class);

  @Pointcut("execution(* com.carcaret..*.*(..))")
  protected void loggingOperation() {}

  @AfterThrowing(pointcut = "loggingOperation()", throwing = "e")
  @Order(1)
  public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
    LOG.error("[{}] An exception has been thrown in {}()", ContextHolder.getUUID(),
        joinPoint.getSignature().getName(), e);
  }

  @Around("loggingOperation()")
  @Order(2)
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    LOG.info("[{}] The method {}() begins with {}", ContextHolder.getUUID(),
        joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    try {
      Object result = joinPoint.proceed();
      return result;
    } catch (IllegalArgumentException e) {
      LOG.error("[{}] Illegal argument {} in {}()" + ContextHolder.getUUID(),
          Arrays.toString(joinPoint.getArgs()), joinPoint.getSignature().getName());
      throw e;
    }
  }

}

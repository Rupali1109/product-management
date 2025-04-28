package com.nowvertical.management.product.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAdvice {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* nowvertical.management.product.service.*.*(..))")
    private void servicePointcut() {

    }

    @Pointcut("execution(* com.nowvertical.management.product.controller.*.*(..))")
    private void controllerPointcut() {

    }

    @Before("controllerPointcut()")
    public void infoLog(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        logger.info("Entering: " + joinPoint.getTarget().getClass().getSimpleName() + ": " + signature.getName());
    }

    @Around("servicePointcut()")
    public Object runAround(ProceedingJoinPoint jp) {
        Object returnValue = null;
        try {
            StringBuilder logMessage = new StringBuilder();
            MethodSignature signature = (MethodSignature) jp.getSignature();
            logMessage.append(
                    "Entering: " + jp.getTarget().getClass().getSimpleName() + ": " + signature.getName() + ": ");

            for (int i = 0; i < signature.getParameterNames().length; i++) {
                logMessage.append(signature.getParameterNames()[i] + ":" + jp.getArgs()[i]);
                if (i != signature.getParameterNames().length - 1)
                    logMessage.append(", ");
            }

            logger.info(logMessage.toString());
            returnValue = jp.proceed();
        } catch (Throwable ex) {
            logger.error("Exception: ", ex);
        }
        StringBuilder logMessage = new StringBuilder();
        MethodSignature signature = (MethodSignature) jp.getSignature();
        logMessage
                .append("Returning: " + jp.getTarget().getClass().getSimpleName() + ": " + signature.getName() + ": ");
        if (returnValue != null) {
            logMessage.append((returnValue).toString());
        } else {
            logMessage.append(" Return value is null.");
        }
        logger.info(logMessage.toString());
        return returnValue;
    }

}
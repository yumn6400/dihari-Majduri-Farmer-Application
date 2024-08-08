package com.dihari.majduri.DihariMajduri.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    public static final Logger LOGGER=LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.dihari.majduri.DihariMajduri.mobile.service.*.*(..))|| execution(* com.dihari.majduri.DihariMajduri.web.service.*.*(..))")
    public void logMethodCall(JoinPoint jp){
    LOGGER.info("*****"+jp.getSignature().getName()+" Method got called"+"*****");
    }

    @After("execution(* com.dihari.majduri.DihariMajduri.mobile.service.*.*(..))|| execution(* com.dihari.majduri.DihariMajduri.web.service.*.*(..))")
    public void logMethodExecuted(JoinPoint jp){
        LOGGER.info("*****"+jp.getSignature().getName()+" Method got executed completely"+"*****");
    }
    @AfterThrowing("execution(* com.dihari.majduri.DihariMajduri.mobile.service.*.*(..))|| execution(* com.dihari.majduri.DihariMajduri.web.service.*.*(..))")
    public void logMethodCrashed(JoinPoint jp){
            LOGGER.info("*****"+jp.getSignature().getName()+" Method got crashed"+"*****");
    }
    @AfterReturning("execution(* com.dihari.majduri.DihariMajduri.mobile.service.*.*(..))|| execution(* com.dihari.majduri.DihariMajduri.web.service.*.*(..))")
    public void logMethodExecutedSuccess(JoinPoint jp){
        LOGGER.info("*****"+jp.getSignature().getName()+" Method got executed and return successfully"+"*****");
    }

}

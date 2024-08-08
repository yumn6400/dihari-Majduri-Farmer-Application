package com.dihari.majduri.DihariMajduri.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidationAspect {
    public static final Logger LOGGER= LoggerFactory.getLogger(ValidationAspect.class);
    @Around("execution(* com.dihari.majduri.DihariMajduri.mobile.service.FarmerService.getFarmerById(..))&& args(id)")
    public Object ValidationAndUpdate(ProceedingJoinPoint jp, int id)throws  Throwable
    {
        if(id<0)
        {
            LOGGER.info("Id is negative,updating it");
            id=-id;
            LOGGER.info("New value :"+id);
        }
        Object obj=jp.proceed(new Object[]{id});
        return obj;
    }
}

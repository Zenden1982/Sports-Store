package com.zenden.sports_store.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class ControllerAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restController() {
    }

    @Pointcut("execution(public * com.zenden.sports_store.Controllers.*Controller.read(*))")
    public void read() {
    }

    @Pointcut("execution(public * com.zenden.sports_store.Controllers.*Controller.create(*))")
    public void create() {
    }

    @Pointcut("execution(public * com.zenden.sports_store.Controllers.*Controller.update(*))")
    public void update() {
    }

    @Pointcut("execution(public * com.zenden.sports_store.Controllers.*Controller.delete(*))")
    public void delete() {
    }

    @Pointcut("execution(public * com.zenden.sports_store.Controllers.*Controller.readAll(..))")
    public void readAll() {
    }

    @Around("restController() && (read() || create() || update() || delete() || readAll())")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("Executing method: {} with arguments: {}", methodName, args);

        try {
            Object result = joinPoint.proceed();
            log.info("Method {} executed successfully with result: {}", methodName, result);
            return result;
        } catch (Throwable throwable) {
            log.error("Error occurred while executing method: {}", methodName, throwable);
            throw throwable;
        }
    }
}

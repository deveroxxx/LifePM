package bakos.life_pm.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

//FIXME: only enable for development
@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* bakos.life_pm.service..*(..)) || " +
            "execution(* bakos.life_pm.controller..*(..)) ||" +
            "execution(* bakos.life_pm.repository..*(..))")
    public void applicationMethods() {}


    @Before("applicationMethods()")
    public void logMethodEntry(JoinPoint joinPoint) {
        log.info(">>: {} -- {}",
                joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "applicationMethods()", returning = "result")
    public void logMethodExit(JoinPoint joinPoint, Object result) {
        log.info("<< {} -- {}",
                joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "applicationMethods()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        log.error("Exception in method: {} with message: {}",
                joinPoint.getSignature(), ex.getMessage(), ex);
    }
}

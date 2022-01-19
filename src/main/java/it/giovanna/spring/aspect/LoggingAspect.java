package it.giovanna.spring.aspect;

import java.util.Arrays;
import java.util.Collection;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

	@Pointcut("@annotation(Loggable)")
	public void executeLogging() {}
	
	@Before("executeLogging()")
    public void logMethodCall(JoinPoint joinPoint){
        StringBuilder message = new StringBuilder("Method: ");
        message.append(joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        if (null!=args && args.length>0){
            message.append(" args=[ | ");
            Arrays.asList(args).forEach(arg->{
                message.append(arg).append(" | ");
            });
            message.append("]");
        }
        LOGGER.info(message.toString());
    }
	
	@SuppressWarnings("rawtypes")
	@AfterReturning(value = "executeLogging()", returning = "returnValue")
	public void logMethodCall(JoinPoint joinPoint, Object returnValue) {
		StringBuilder msg = new StringBuilder("Method: ");
		msg.append(joinPoint.getSignature().getName());
		Object[] args = joinPoint.getArgs();
		if (null!=args && args.length > 0) {
			msg.append(" args = [ | ");
			Arrays.asList(args).forEach(arg -> {
				msg.append(arg).append(" |  ");
			});
			msg.append("]");
		}
		msg.append(", returning:  ");
		if (returnValue instanceof Collection) {
			msg.append(((Collection)(returnValue)).size()).append(" instance(s)");
		} else {
			msg.append(returnValue.toString());
		}
		LOGGER.info(msg.toString());
	}
	
	@SuppressWarnings("rawtypes")
	@Around(value = "executeLogging()")
	public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		Object returnValue = joinPoint.proceed();
		StringBuilder msg = new StringBuilder("Method: ");
		msg.append(joinPoint.getSignature().getName());
		long totalTime = System.currentTimeMillis() - startTime;
		msg.append(" totalTime: ").append(totalTime).append(" ms");
		Object[] args = joinPoint.getArgs();
		if (null!=args && args.length > 0) {
			msg.append(" args = [ | ");
			Arrays.asList(args).forEach(arg -> {
				msg.append(arg).append(" |  ");
			});
			msg.append("]");
		}
		msg.append(", returning:  ");
		if (returnValue instanceof Collection) {
			msg.append(((Collection)(returnValue)).size()).append(" instance(s)");
		} else {
			msg.append(returnValue.toString());
		}
		LOGGER.info(msg.toString());
		return returnValue;
	}
}

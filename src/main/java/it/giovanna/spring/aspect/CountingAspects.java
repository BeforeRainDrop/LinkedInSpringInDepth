package it.giovanna.spring.aspect;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CountingAspects {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

	private static Map<String, Integer> countMap = new HashMap<String, Integer>();
	
	@Pointcut("@annotation(Countable)")
	public void executeCounting() {}
	
	@Around(value = "executeCounting()")
	public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
		Object returnValue = joinPoint.proceed();
		StringBuilder msg = new StringBuilder("Method: ");
		String methodName = joinPoint.getSignature().getDeclaringTypeName();
		Integer count = countMap.computeIfAbsent(methodName, m -> 0);
		countMap.put(methodName, ++count);
		msg.append(methodName);
		msg.append(" calls: ").append(countMap.get(methodName));
		
		LOGGER.info(msg.toString());
		return returnValue;
	}

}

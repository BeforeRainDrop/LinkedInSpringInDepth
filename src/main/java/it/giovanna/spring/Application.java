package it.giovanna.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import it.giovanna.spring.config.ApplicationConfig;
import it.giovanna.spring.service.OutputService;

public class Application {

	public static void main(String[] args) throws Exception {
//		GreetingService greetingService = new GreetingService("Hello");
//		TimeService timeService = new TimeService(true);
//		OutputService outputService = new OutputService(greetingService, timeService);
//		for (int i = 0; i < 5; i++) {
//			outputService.generateOutput("Giovanna");
//			Thread.sleep(5000);
//		}
		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		OutputService outputService = context.getBean(OutputService.class);

		for (int i = 0; i < 5; i++) {
			outputService.generateOutput();
			Thread.sleep(5000);
		}
		((ConfigurableApplicationContext) context).close();
	}

}

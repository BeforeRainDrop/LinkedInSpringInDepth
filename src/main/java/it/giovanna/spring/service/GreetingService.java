package it.giovanna.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.giovanna.spring.aspect.Countable;
import it.giovanna.spring.aspect.Loggable;

@Service
public class GreetingService {

	@Value("${app.greeting}")
    private String greeting;

    public GreetingService(){
        super();
    }

    @Loggable
    @Countable
    public String getGreeting(String name){
        return greeting + " " + name;
    }
}

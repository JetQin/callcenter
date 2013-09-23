package com.egoonet.callcenter.controller;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.egoonet.callcenter.controller.configserver.SubscribeRequest;

public class Startup
{
	private static Logger log = Logger.getLogger(Startup.class);
    private static ApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans.xml");
	private static SubscribeRequest subscribeRequest;
	
    public static void main(String[] args)
	{
		StatServerController statController = new StatServerController();
		//ConfigServerController confController = new ConfigServerController(context);
		log.info("[StatServer Startup ........]");
		statController.startup();
		//confController.startup();
		log.info("[StatServer Startup Successfully........]");
	}
}

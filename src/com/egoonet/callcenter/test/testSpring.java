package com.egoonet.callcenter.test;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.egoonet.callcenter.conf.ConfigParameter;
import com.egoonet.callcenter.controller.statserver.SubscribeMessage;
import com.egoonet.callcenter.controller.statserver.SubscribeMessageTask;

public class testSpring extends TestCase
{

	ApplicationContext context = null;
	SubscribeMessageTask subscribeQueueTask = null;
	SubscribeMessage subscribeAgentMessage = null;
	SubscribeMessage subscribeGroupMessage = null;
	SubscribeMessage subscribeQueueMessage = null;
	ConfigParameter parameter = null;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		context = new ClassPathXmlApplicationContext("classpath:beans.xml");
		subscribeQueueTask = (SubscribeMessageTask) context.getBean("subscribeQueueTask");
		subscribeAgentMessage = (SubscribeMessage) context.getBean("subscribeAgentMessage");
		subscribeGroupMessage = (SubscribeMessage) context.getBean("subscribeGroupMessage");
		subscribeQueueMessage = (SubscribeMessage) context.getBean("subscribeQueueMessage");
		parameter = (ConfigParameter) context.getBean("configParameter");
	}

	public void testData()
	{
		// System.out.println(subscribeQueueTask.toString());
		// System.out.println("*********Agent  Message********");
		// System.out.println(subscribeAgentMessage.toString());
		// System.out.println("*********Group  Message********");
		// System.out.println(subscribeGroupMessage.toString());
		// System.out.println("*********Queue  Message********");
		// System.out.println(subscribeQueueMessage.toString());

		System.out.println("uriServer=" + parameter.getOpentime());
		System.out.println("uriServer=" + parameter.getTimeout());
		System.out.println("uriServer=" + parameter.getUriServer());
	}
	
	public void testGetStr(){
		String s = "T=D/S";
		Map<String,Map<String,String>> map = new HashMap<String,Map<String,String>>();
		
		String target = s.substring(0,s.indexOf("="));
		String temp = s.substring(s.indexOf("="));
//		String v1 = temp.s
		
	}

}

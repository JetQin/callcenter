package com.egoonet.callcenter.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

public class testThreadPool extends TestCase
{

	ApplicationContext context = null;
	TaskExecutorExample example = null;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		context = new ClassPathXmlApplicationContext("classpath:beans.xml");
		example = (TaskExecutorExample) context.getBean("taskExecutorExample");
	}

	public void testExample()
	{
		example.printMessages();
	}
}

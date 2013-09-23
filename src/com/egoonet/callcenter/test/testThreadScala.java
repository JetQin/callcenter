package com.egoonet.callcenter.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.timer.TimerTaskExecutor;

import com.egoonet.callcenter.controller.statserver.ProcessSelfDefinitionMessageTask;
import com.egoonet.callcenter.service.StatService;

public class testThreadScala
{
	public static void main(String[] args)
	{
		ApplicationContext context = null;
		TimerTaskExecutor scheduledExecutor;
		ProcessSelfDefinitionMessageTask task;
		context = new ClassPathXmlApplicationContext("classpath:beans.xml");
		scheduledExecutor = (TimerTaskExecutor) context.getBean("scheduledTask");

		StatService service = (StatService) context.getBean("statService");
//		service.setByName("from QueueStatistics where stattype = :stattype and subscribeName = :subscribeName and subscribeType = :subscribeType");
//		QueueStatistics statics = (QueueStatistics) service.getModelByName("QUEUE", "KM_ZongHeVIP_APQ@alipayHZSIPSwitch", "Total_Calls_Entered");
//		
//		System.out.println(statics.toString());
		task = (ProcessSelfDefinitionMessageTask) context.getBean("processDefinitionTask");
//		scheduledExecutor.execute(task);
	}

//	ApplicationContext context = null;
//	TaskExecutor scheduledExecutor;
//	ProcessSelfDefinitionMessageTask task;

	// @Override
	// protected void setUp() throws Exception
	// {
	// super.setUp();
	// context = new ClassPathXmlApplicationContext("classpath:beans.xml");
	// scheduledExecutor = (TaskExecutor) context.getBean("processExecutor");
	// task =
	// (ProcessSelfDefinitionMessageTask)context.getBean("processDefinitionTask");
	// }

//	public void testScheduleTask()
//	{
//		scheduledExecutor.execute(task);
//	}
}

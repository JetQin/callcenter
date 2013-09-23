package com.egoonet.callcenter.controller;

import java.net.URI;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.task.TaskExecutor;

import com.egoonet.callcenter.conf.ConfigParameter;
import com.egoonet.callcenter.controller.statserver.ProcessMessageTask;
import com.egoonet.callcenter.controller.statserver.ProcessSelfDefinitionMessageTask;
import com.egoonet.callcenter.controller.statserver.SubscribeMessageTask;
import com.genesyslab.platform.commons.protocol.ChannelState;
import com.genesyslab.platform.commons.protocol.Endpoint;
import com.genesyslab.platform.commons.protocol.Message;
import com.genesyslab.platform.reporting.protocol.StatServerProtocol;

public class StatServerController
{

	private StatServerProtocol statServerProtocol;
	private ChannelState state;
	private ConfigParameter parameter;
	private TaskExecutor subscribeExecutor;
	private TaskExecutor processExecutor;
	private TaskExecutor scheduledExecutor;
	private ApplicationContext context = null;

	private Logger log = Logger.getLogger(StatServerController.class);

	public StatServerController()
	{
		context = new ClassPathXmlApplicationContext("classpath:beans.xml");
		setSubscribeExecutor((TaskExecutor) context.getBean("subscribeExecutor"));
		setProcessExecutor((TaskExecutor) context.getBean("processExecutor"));
		setScheduledExecutor((TaskExecutor) context.getBean("scheduledTask"));
		setParameter((ConfigParameter)context.getBean("statParameter"));

	}
	
	public StatServerController(ApplicationContext context)
	{
		setSubscribeExecutor((TaskExecutor) context.getBean("subscribeExecutor"));
		setProcessExecutor((TaskExecutor) context.getBean("processExecutor"));
		setScheduledExecutor((TaskExecutor) context.getBean("scheduledTask"));
		setParameter((ConfigParameter)context.getBean("statParameter"));
		
	}

	public void startup()
	{

		while ( true )
		{
			try
			{
				if ( statServerProtocol == null )
				{
					URI uri = new URI(parameter.getUriServer());
					Endpoint endp = new Endpoint(uri);
					log.debug("config parameter="+parameter.toString());
					statServerProtocol = new StatServerProtocol(endp);
					statServerProtocol.setTimeout(parameter.getTimeout());
					statServerProtocol.open(parameter.getTimeout());
					subscribeMessage();
					scheduledTaskStart();
					
				}

				state = statServerProtocol.getState();

				if ( state == ChannelState.Opened )
				{
					Message event = statServerProtocol.receive(5000);
					if ( event != null )
					{
						processMessage(event);
					}
				}
				else if ( state == ChannelState.Closed )
				{
					statServerProtocol = null;
				}
			}
			catch ( Exception ex )
			{
			}
		}
	}

	private void subscribeMessage()
	{
		log.info(" Subscribe Message ");
		SubscribeMessageTask subscribeAgentTask = (SubscribeMessageTask) context.getBean("subscribeAgentTask");
		SubscribeMessageTask subscribeGroupTask = (SubscribeMessageTask) context.getBean("subscribeGroupTask");
		SubscribeMessageTask subscribeQueueTask = (SubscribeMessageTask) context.getBean("subscribeQueueTask");

		if ( null != subscribeAgentTask )
		{
			log.debug(" Subscribe Agent Message");
			subscribeAgentTask.setStatServerProtocol(statServerProtocol);
			subscribeExecutor.execute(subscribeAgentTask);
		}
		if ( null != subscribeGroupTask )
		{
			log.debug(" Subscribe Group Message");
			subscribeGroupTask.setStatServerProtocol(statServerProtocol);
			subscribeExecutor.execute(subscribeGroupTask);
		}
		if ( null != subscribeQueueTask )
		{
			log.debug(" Subscribe Queue Message");
			subscribeQueueTask.setStatServerProtocol(statServerProtocol);
			subscribeExecutor.execute(subscribeQueueTask);
		}

	}

	private void processMessage(Message event)
	{
		log.info(" Process Message ");
		ProcessMessageTask task = (ProcessMessageTask)context.getBean("processTask");
		task.setMessage(event);
		processExecutor.execute(task);
	}

	private void scheduledTaskStart()
	{
		log.info(" Scheduled Task Start ");
		ProcessSelfDefinitionMessageTask task = (ProcessSelfDefinitionMessageTask) context.getBean("processDefinitionTask");
//		scheduledExecutor.execute(task);
	}
	
	public void setProcessExecutor(TaskExecutor processExecutor)
	{
		this.processExecutor = processExecutor;
	}
	
	public TaskExecutor getProcessExecutor()
	{
		return processExecutor;
	}
	
	public void setSubscribeExecutor(TaskExecutor subscribeExecutor)
	{
		this.subscribeExecutor = subscribeExecutor;
	}
	
	public TaskExecutor getSubscribeExecutor()
	{
		return subscribeExecutor;
	}
	
	public void setScheduledExecutor(TaskExecutor scheduledExecutor)
	{
		this.scheduledExecutor = scheduledExecutor;
	}
	
	public TaskExecutor getScheduledExecutor()
	{
		return scheduledExecutor;
	}

	public ConfigParameter getParameter()
	{
		return parameter;
	}

	public void setParameter(ConfigParameter parameter)
	{
		this.parameter = parameter;
	}

	
}

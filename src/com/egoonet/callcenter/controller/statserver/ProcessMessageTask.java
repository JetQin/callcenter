package com.egoonet.callcenter.controller.statserver;

import java.util.Date;

import org.apache.log4j.Logger;

import com.egoonet.callcenter.model.StatData;
import com.egoonet.callcenter.service.StatService;
import com.genesyslab.platform.commons.protocol.Message;
import com.genesyslab.platform.reporting.protocol.statserver.events.EventInfo;

public class ProcessMessageTask implements Runnable
{

	// 订阅信息类型,座席组,座席,队列

	private Message message = null;
	private StatService service;
	private Logger log = Logger.getLogger(ProcessMessageTask.class);

	public ProcessMessageTask()
	{
	}

	public ProcessMessageTask(Message message)
	{
		setMessage(message);
	}

	private void processMessage()
	{
		EventInfo eventInfo = (EventInfo) message;
		int refID = eventInfo.getReferenceId();
		int value = eventInfo.getIntValue();
		log.info("[recieve msg from statserver] refID=" + refID + ",value=" + value);
		StatData data = null;
		if ( null != service )
		{
			data = (StatData) service.getModel(refID);
		}
		if ( null != data )
		{
			data.setUpdateTime(new Date());
			data.setStatvalue(value);
			service.updateModel(data);
		}
	}

	public Message getMessage()
	{
		return message;
	}

	public void setMessage(Message message)
	{
		this.message = message;
	}

	public void setService(StatService service)
	{
		this.service = service;
	}

	public StatService getService()
	{
		return service;
	}

	@Override
	public void run()
	{
		processMessage();
	}

}

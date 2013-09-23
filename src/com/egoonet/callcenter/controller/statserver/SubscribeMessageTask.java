package com.egoonet.callcenter.controller.statserver;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.egoonet.callcenter.model.StatData;
import com.egoonet.callcenter.service.AbstractModelService;
import com.genesyslab.platform.commons.protocol.Message;
import com.genesyslab.platform.commons.protocol.ProtocolException;
import com.genesyslab.platform.reporting.protocol.StatServerProtocol;
import com.genesyslab.platform.reporting.protocol.statserver.events.EventStatisticOpened;
import com.genesyslab.platform.reporting.protocol.statserver.requests.RequestOpenStatistic;
import com.genesyslab.platform.reporting.protocol.statserver.requests.RequestPeekStatistic;

public class SubscribeMessageTask implements Runnable
{

	// 订阅信息类型,座席组,座席,队列

	private StatServerProtocol statServerProtocol;
	private AbstractMessage msg;
	private String[] subscribeMsgs;
	private AbstractModelService service;
	private StatData data;
	private Logger log = Logger.getLogger(SubscribeMessageTask.class);

	public SubscribeMessageTask()
	{
	}

	public void sendRequest()
	{

		try
		{
			service.deleteAllModel();
			data.setCreateTime(new Date());
			data.setUpdateTime(new Date());

			for ( String ID : getStatObjectID() )
			{
				for ( String info : getSubscribeMsgs() )
				{

					msg.setStaticsName(ID);
					msg.setSubscribeType(info);
					RequestOpenStatistic request = msg.makeRequest(info);
					Message event = statServerProtocol.request(request);
					data.setReferenceID(request.getReferenceId());
					data.setSubscribeType(info);
					data.setSubscribeName(ID);

					if ( event != null && event.messageId() == EventStatisticOpened.ID )
					{
						EventStatisticOpened eventStat = (EventStatisticOpened) event;
						int refid = eventStat.getReferenceId();

						RequestPeekStatistic req = RequestPeekStatistic.create();
						req.setStatisticId(refid);
						statServerProtocol.send(req);
						log.info("[send data]=" + data.toString());
						service.saveModel(data);
					}
					else
					{
						log.error("create request event error!");
					}
				}
			}
		}
		catch ( ProtocolException e )
		{
			e.printStackTrace();
		}
		catch ( IllegalStateException e )
		{
			e.printStackTrace();
		}
	}

	@Override
	public void run()
	{
		sendRequest();
		log.debug("[subscribe msg]"+toString());
	}

	public List<String> getStatObjectID()
	{
		log.info("[get subscribe ID] = " + service.getSubscribeSQL());
		return service.getSubscribeName();
	}

	public String[] getSubscribeMsgs()
	{
		return subscribeMsgs;
	}

	public void setSubscribeMsgs(String[] subscribeMsgs)
	{
		this.subscribeMsgs = subscribeMsgs;
	}

	public void setMsg(AbstractMessage msg)
	{
		this.msg = msg;
	}

	public AbstractMessage getMsg()
	{
		return msg;
	}

	public void setStatServerProtocol(StatServerProtocol statServerProtocol)
	{
		this.statServerProtocol = statServerProtocol;
	}

	public StatServerProtocol getStatServerProtocol()
	{
		return statServerProtocol;
	}

	public void setService(AbstractModelService service)
	{
		this.service = service;
	}

	public AbstractModelService getService()
	{
		return service;
	}

	public void setData(StatData data)
	{
		this.data = data;
	}

	public StatData getData()
	{
		return data;
	}

	@Override
	public String toString()
	{
		StringBuffer buffer = new StringBuffer();
		for ( String msg : getSubscribeMsgs() )
		{
			buffer.append(msg);
		}
		return buffer.toString() + "," + getMsg().toString();
	}

}

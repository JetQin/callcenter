package com.egoonet.callcenter.controller.statserver;

import org.apache.log4j.Logger;

import com.genesyslab.platform.reporting.protocol.statserver.Notification;
import com.genesyslab.platform.reporting.protocol.statserver.NotificationMode;
import com.genesyslab.platform.reporting.protocol.statserver.StatisticMetric;
import com.genesyslab.platform.reporting.protocol.statserver.StatisticObject;
import com.genesyslab.platform.reporting.protocol.statserver.StatisticObjectType;
import com.genesyslab.platform.reporting.protocol.statserver.requests.RequestOpenStatistic;

public class SubscribeMessage extends AbstractMessage
{

	private Logger log = Logger.getLogger(SubscribeMessage.class);
	
	private int referenceID = 0;
	private static int base = 0;

	private StatisticObject makeStaticObject()
	{
		StatisticObject statObjAG = StatisticObject.create();
		if ( isEmpty(getStaticsName()) )
		{
			statObjAG.setObjectId(getStaticsName());
		}
		if ( getStaticsType().equals("QUEUE") )
		{
			statObjAG.setObjectType(StatisticObjectType.Queue);
		}
		else if ( getStaticsType().equals("GROUP") )
		{
			statObjAG.setObjectType(StatisticObjectType.GroupAgents);
		}
		else if ( getStaticsType().equals("AGENT") )
		{
			statObjAG.setObjectType(StatisticObjectType.Agent);
		}
		else
		{
			log.error("Not know what type it is subscribe,plesase set statics name");
		}

		if ( isEmpty(getTenantName()) )
		{
			statObjAG.setTenantName(getTenantName());
		}
		else
		{
			log.error("Tenant name not set!");
		}
		log.info("[make statObjAG]staticsName=" + getStaticsName() + ",staticsType=" + getStaticsType() + ",tenantName=" + getTenantName());
		return statObjAG;
	}

	private StatisticMetric makeStaticMetrics(String info)
	{
		StatisticMetric statMetric = StatisticMetric.create();
		if ( isEmpty(getTimeprofile()) )
		{
			statMetric.setTimeProfile(getTimeprofile());
		}
		else
		{
			log.info("not set timeprofile config parameter");
		}
		if ( isEmpty(getFilter(info)) )
		{
			statMetric.setFilter(getFilter(info));
		}
		else
		{
			log.info("not set filter config parameter");
		}
		statMetric.setStatisticType(getSubscribeType());
		log.info("[make StaticMetrics]timeprofile=" + getTimeprofile() + ",subscribeType=" + getSubscribeType());
		return statMetric;
	}

	public RequestOpenStatistic makeRequest(String info)
	{
		RequestOpenStatistic requestOpenStat = RequestOpenStatistic.create();
		Notification notification = Notification.create();
		if ( getNotification() > 0 )
		{
			notification.setMode(NotificationMode.Periodical);
			notification.setFrequency(getNotification());
			requestOpenStat.setNotification(notification);

		}
		else
		{
			notification.setMode(NotificationMode.Immediate);
			requestOpenStat.setNotification(notification);
		}
		requestOpenStat.setStatisticMetric(makeStaticMetrics(info));
		requestOpenStat.setStatisticObject(makeStaticObject());
		requestOpenStat.setReferenceId(getRefID());

		log.info("[make RequestOpenStatistic] notification=" + getNotification());

		return requestOpenStat;
	}

	private synchronized int getRefID()
	{
		return base = base + 1;
	}

	private boolean isEmpty(String msg)
	{
		return (null != msg && !msg.equals(""));
	}

	public void setReferenceID(int referenceID)
	{
		this.referenceID = referenceID;
	}

	public int getReferenceID()
	{
		return referenceID;
	}

}

package com.egoonet.callcenter.controller.statserver;

import java.util.HashMap;
import java.util.Map;

import com.genesyslab.platform.reporting.protocol.statserver.requests.RequestOpenStatistic;

public abstract class AbstractMessage
{

	private String filter;
	private String timeprofile;
	private String timerange;
	private String tenantName;
	private int frequency;
	private String staticsName;
	private String subscribeType;
	private String staticsType;
	private int notification;
	private static Map<String, String> filterMap = new HashMap<String, String>();

	public String getFilter(String info)
	{
		return filterMap.get(info);
	}

	public void setFilter(String filter)
	{
		this.filter = filter;
		parseFilterMap(filter);
	}

	public String getTimeprofile()
	{
		return timeprofile;
	}

	public void setTimeprofile(String timeprofile)
	{
		this.timeprofile = timeprofile;
	}

	public String getTimerange()
	{
		return timerange;
	}

	public void setTimerange(String timerange)
	{
		this.timerange = timerange;
	}

	public String getTenantName()
	{
		return tenantName;
	}

	public void setTenantName(String tenantName)
	{
		this.tenantName = tenantName;
	}

	public int getFrequency()
	{
		return frequency;
	}

	public void setFrequency(int frequency)
	{
		this.frequency = frequency;
	}

	public String getStaticsName()
	{
		return staticsName;
	}

	public void setStaticsName(String staticsName)
	{
		this.staticsName = staticsName;
	}

	public String getStaticsType()
	{
		return staticsType;
	}

	public void setStaticsType(String staticsType)
	{
		this.staticsType = staticsType;
	}

	public int getNotification()
	{
		return notification;
	}

	public void setNotification(int notification)
	{
		this.notification = notification;
	}

	public String getSubscribeType()
	{
		return subscribeType;
	}

	public void setSubscribeType(String subscribeType)
	{
		this.subscribeType = subscribeType;
	}

	public void setFilterMap(Map<String, String> filterMap)
	{
		this.filterMap = filterMap;
	}

	public Map<String, String> getFilterMap()
	{
		return filterMap;
	}
	
	private void parseFilterMap(String filter)
	{
		String[] filters;
		if ( null != filter )
		{
			filters = filter.split(",");
			if ( null != filter && filter.length() > 0 )
			{
				for ( String key : filters )
				{

					int mark = key.indexOf("=");
					if ( -1 != mark )
					{
						filterMap.put(key.substring(0, mark), key.substring(mark + 1));
					}
				}
			}

		}
	}

	public abstract RequestOpenStatistic makeRequest(String info);

	@Override
	public String toString()
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n");
		buffer.append("timeprofile=" + getTimeprofile());
		buffer.append("\n");
		buffer.append("frequency=" + getFrequency());
		buffer.append("\n");
		buffer.append("notification=" + getNotification());
		buffer.append("\n");
		buffer.append("staticsName=" + getStaticsName());
		buffer.append("\n");
		buffer.append("staticsType=" + getStaticsType());
		buffer.append("\n");
		buffer.append("subscribeType=" + getSubscribeType());
		buffer.append("\n");
		buffer.append("tenantName=" + getTenantName());
		buffer.append("\n");
		buffer.append("timeRange=" + getTimerange());
		return buffer.toString();
	}
}

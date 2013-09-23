package com.egoonet.callcenter.model;

import java.util.Date;

public abstract class StatData
{
	private int id;
	private int rangevalue;
	private int referenceID;
	private double statvalue;
	private String subscribeType;
	private String subscribeName;
	private String properties;
	private Date createTime;
	private Date updateTime;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getReferenceID()
	{
		return referenceID;
	}

	public void setReferenceID(int referenceID)
	{
		this.referenceID = referenceID;
	}

	public double getValue()
	{
		return statvalue;
	}

	public void setSubscribeType(String subscribeType)
	{
		this.subscribeType = subscribeType;
	}

	public String getSubscribeName()
	{
		return subscribeName;
	}

	public void setSubscribeName(String subscribeName)
	{
		this.subscribeName = subscribeName;
	}

	public String getProperties()
	{
		return properties;
	}

	public void setProperties(String properties)
	{
		this.properties = properties;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	public int getRangevalue()
	{
		return rangevalue;
	}

	public void setRangevalue(int rangevalue)
	{
		this.rangevalue = rangevalue;
	}

	public double getStatvalue()
	{
		return statvalue;
	}

	public void setStatvalue(double statvalue)
	{
		this.statvalue = statvalue;
	}

	public String getSubscribeType()
	{
		return subscribeType;
	}

	@Override
	public String toString()
	{
		return "[referenceID]="+getReferenceID()+",[subscribeName]=" + getSubscribeName() + "[subscribeType]=" + getSubscribeType() + "[value]=" + getStatvalue();
	}

}

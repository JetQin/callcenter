package com.egoonet.callcenter.model;

import java.util.Date;

public class Region
{

	private int regionID;
	private String regionName;
	private String regionDescription;
	private Date createTime;
	private Date updateTime;

	public int getRegionID()
	{
		return regionID;
	}

	public void setRegionID(int regionID)
	{
		this.regionID = regionID;
	}

	public String getRegionName()
	{
		return regionName;
	}

	public void setRegionName(String regionName)
	{
		this.regionName = regionName;
	}

	public String getRegionDescription()
	{
		return regionDescription;
	}

	public void setRegionDescription(String regionDescription)
	{
		this.regionDescription = regionDescription;
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

}

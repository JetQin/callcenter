package com.egoonet.callcenter.service;


public class AgentGroupService extends AbstractModelService
{
	
	public String sql;
	
	public void setSql(String sql)
	{
		this.sql = sql;
	}
	
	public String getSql()
	{
		return sql;
	}

	@Override
	public String getRequestSQL()
	{
		return getSql();
	}

	@Override
	public String getSubscribeSQL()
	{
		// TODO Auto-generated method stub
		return null;
	}

}

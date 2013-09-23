package com.egoonet.callcenter.service;

public class StatService extends AbstractModelService
{
	private String sql;
	private String subscribe;

	public void setSql(String sql)
	{
		this.sql = sql;
	}

	public String getSql()
	{
		return sql;
	}

	public void setSubscribe(String subscribe)
	{
		this.subscribe = subscribe;
	}

	public String getSubscribe()
	{
		return subscribe;
	}

	@Override
	public String getRequestSQL()
	{
		return getSql();
	}

	@Override
	public String getSubscribeSQL()
	{
		return getSubscribe();
	}

}

package com.egoonet.callcenter.conf;

public class ConfigParameter
{
	private String uriServer;
	private long timeout;
	private long opentime;
	private String clientName;
	private String password;
	private String userName;
	private int port;

	public String getUriServer()
	{
		return uriServer;
	}

	public void setUriServer(String uriServer)
	{
		this.uriServer = uriServer;
	}

	public long getTimeout()
	{
		return timeout;
	}

	public void setTimeout(long timeout)
	{
		this.timeout = timeout;
	}

	public long getOpentime()
	{
		return opentime;
	}

	public void setOpentime(long opentime)
	{
		this.opentime = opentime;
	}

	public String getClientName()
	{
		return clientName;
	}

	public void setClientName(String clientName)
	{
		this.clientName = clientName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	@Override
	public String toString()
	{
		return "[uriServer]=" + getUriServer() + ",[openTime]=" + getOpentime();
	}

}

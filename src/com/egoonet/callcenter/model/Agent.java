package com.egoonet.callcenter.model;

import java.util.Set;

public class Agent
{
	private int agentID;
	private String agentName;
	private String agentAlias;
	private String agentNo;
	private AgentStatus agentStatus;
	private Set<AgentGroup> groups;

	public int getAgentID()
	{
		return agentID;
	}

	public void setAgentID(int agentID)
	{
		this.agentID = agentID;
	}

	public String getAgentName()
	{
		return agentName;
	}

	public void setAgentName(String agentName)
	{
		this.agentName = agentName;
	}

	public String getAgentAlias()
	{
		return agentAlias;
	}

	public void setAgentAlias(String agentAlias)
	{
		this.agentAlias = agentAlias;
	}

	public String getAgentNo()
	{
		return agentNo;
	}

	public void setAgentNo(String agentNo)
	{
		this.agentNo = agentNo;
	}

	public AgentStatus getAgentStatus()
	{
		return agentStatus;
	}

	public void setAgentStatus(AgentStatus agentStatus)
	{
		this.agentStatus = agentStatus;
	}

	public Set<AgentGroup> getGroups()
	{
		return groups;
	}

	public void setGroups(Set<AgentGroup> groups)
	{
		this.groups = groups;
	}

}

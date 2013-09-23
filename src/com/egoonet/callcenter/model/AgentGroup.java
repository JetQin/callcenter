package com.egoonet.callcenter.model;

import java.util.List;
import java.util.Set;

public class AgentGroup
{

	private int groupID;
	private String groupName;
	private String groupDescription;
	private Set<Agent> agents;
	private List<Region> region;
	private Set<AgentQueue> queues;
	public int getGroupID()
	{
		return groupID;
	}
	public void setGroupID(int groupID)
	{
		this.groupID = groupID;
	}
	public String getGroupName()
	{
		return groupName;
	}
	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}
	public String getGroupDescription()
	{
		return groupDescription;
	}
	public void setGroupDescription(String groupDescription)
	{
		this.groupDescription = groupDescription;
	}
	public Set<Agent> getAgents()
	{
		return agents;
	}
	public void setAgents(Set<Agent> agents)
	{
		this.agents = agents;
	}
	public List<Region> getRegion()
	{
		return region;
	}
	public void setRegion(List<Region> region)
	{
		this.region = region;
	}
	public Set<AgentQueue> getQueues()
	{
		return queues;
	}
	public void setQueues(Set<AgentQueue> queues)
	{
		this.queues = queues;
	}
	
	
}

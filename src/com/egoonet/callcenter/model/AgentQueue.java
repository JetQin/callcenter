package com.egoonet.callcenter.model;

import java.util.List;
import java.util.Set;

public class AgentQueue
{

	 private int queueID;
	 private String queueName;
	 private String queueDescription;
	 private Set<AgentGroup> groups;
	 private List<Region> regions;
}

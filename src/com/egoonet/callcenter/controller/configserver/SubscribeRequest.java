package com.egoonet.callcenter.controller.configserver;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.egoonet.callcenter.model.Agent;
import com.genesyslab.platform.applicationblocks.com.CfgQuery;
import com.genesyslab.platform.applicationblocks.com.ConfigException;
import com.genesyslab.platform.applicationblocks.com.IConfService;
import com.genesyslab.platform.applicationblocks.com.objects.CfgAgentGroup;
import com.genesyslab.platform.applicationblocks.com.objects.CfgFolder;
import com.genesyslab.platform.applicationblocks.com.objects.CfgPerson;
import com.genesyslab.platform.applicationblocks.com.queries.CfgAgentGroupQuery;
import com.genesyslab.platform.applicationblocks.com.queries.CfgFolderQuery;
import com.genesyslab.platform.applicationblocks.com.queries.CfgPersonQuery;

public class SubscribeRequest
{
	private Logger log = Logger.getLogger(SubscribeRequest.class);

	private IConfService confService;

	public void addAgent()
	{
		try
		{
			CfgAgentGroupQuery qAgentGroups = new CfgAgentGroupQuery();
			qAgentGroups.setName("EmptyGroup");
			CfgAgentGroup group = confService.retrieveObject(CfgAgentGroup.class, qAgentGroups);
			List<CfgPerson> agents = (List<CfgPerson>) group.getAgents();
			CfgPersonQuery qAgent = new CfgPersonQuery();
			qAgent.setEmployeeId("Developer001");

			CfgPerson agent = confService.retrieveObject(CfgPerson.class, qAgent);

			agents.add(agent);
			group.save();
		}
		catch ( ConfigException e )
		{
			e.printStackTrace();
		}
	}

	public void getAgent()
	{
		List<Agent> allAgent = new ArrayList<Agent>();
		try
		{
			CfgPersonQuery personQuery = new CfgPersonQuery();
			CfgAgentGroup group = confService.retrieveObject(CfgAgentGroup.class, personQuery);

			List<CfgPerson> agents = (List<CfgPerson>) group.getAgents();
			for ( CfgPerson cfgPerson : agents )
			{
				Agent agent = new Agent();
				agent.setAgentAlias(cfgPerson.getFirstName());
				agent.setAgentName(cfgPerson.getLastName());
				agent.setAgentNo(cfgPerson.getEmployeeID());
				allAgent.add(agent);
			}
		}
		catch ( ConfigException e )
		{
			e.printStackTrace();
		}
	}

	public String getFolder()
	{
		CfgFolder folder = null;
		try
		{
			CfgFolderQuery folderQuery = new CfgFolderQuery();
			folderQuery.setName("Persons");
			folder = (CfgFolder) confService.retrieveObject(folderQuery);
		}
		catch ( ConfigException e )
		{
			e.printStackTrace();
		}
		if ( null != folder )
		{

			log.info("folder name=" + folder.getName() + ",folder description=" + folder.getDescription());
			return folder.getName();
		}
		else
		{
			return "";

		}
	}

	public List<String> getPerson()
	{
		List<CfgPerson> persons = null;
		List<String> personID = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		try
		{
			CfgPersonQuery personQuery = new CfgPersonQuery();
			persons = (List<CfgPerson>) confService.retrieveMultipleObjects(CfgPerson.class, personQuery);
			for ( CfgPerson cfgPerson : persons )
			{
				log.info("[CFGPERSON]");
				log.error(cfgPerson.getFirstName() + "-" + cfgPerson.getEmployeeID());
				buffer.append(cfgPerson.getFirstName() + "-" + cfgPerson.getEmployeeID());
				if ( null != cfgPerson.getEmployeeID() && !"".equals(cfgPerson.getEmployeeID()) )
				{
					personID.add(cfgPerson.getEmployeeID());
				}
			}
		}
		catch ( ConfigException e )
		{
			e.printStackTrace();
		}
		catch ( InterruptedException ex )
		{
			log.error("[ERROR]=" + ex.getMessage());
		}

		return personID;
	}

	public List<String> getGroups()
	{

		List<CfgAgentGroup> groups = null;
		List<String> groupID = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		try
		{
			CfgAgentGroupQuery groupQuery = new CfgAgentGroupQuery();
			groups = (List<CfgAgentGroup>) confService.retrieveMultipleObjects(CfgAgentGroup.class, groupQuery);
			for ( CfgAgentGroup group : groups )
			{
				log.debug("[CFGGROUP]");
				if ( null != group.getGroupInfo().getName() && !"".equals(group.getGroupInfo().getName()) )
				{
					groupID.add(group.getGroupInfo().getName());
				}
			}
		}
		catch ( ConfigException e )
		{
			e.printStackTrace();
		}
		catch ( InterruptedException ex )
		{
			log.error("[ERROR]=" + ex.getMessage());
		}

		return groupID;
	}

	public List<String> getQueues()
	{

		List<CfgQuery> queues = null;
		List<String> queueID = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		try
		{
			// CfgQuery groupQuery = new CfgAgentGroupQuery();
			// groups = (List<CfgAgentGroup>)
			// confService.retrieveMultipleObjects(CfgAgentGroup.class,
			// groupQuery);
			// for ( CfgAgentGroup group : groups )
			// {
			// log.debug("[CFGGROUP]");
			// if ( null != group.getGroupInfo().getName() &&
			// !"".equals(group.getGroupInfo().getName()) )
			// {
			// groupID.add(group.getGroupInfo().getName());
			// }
			// }
		}
		catch ( Exception ex )
		{
			log.error("[ERROR]=" + ex.getMessage());
		}

		return queueID;
	}

	public void setConfService(IConfService confService)
	{
		this.confService = confService;
	}

	public IConfService getConfService()
	{
		return confService;
	}
}

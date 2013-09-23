package com.egoonet.callcenter.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.jboss.logging.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import bsh.EvalError;
import bsh.Interpreter;

import com.egoonet.callcenter.model.Agent;
import com.egoonet.callcenter.model.AgentGroup;
import com.egoonet.callcenter.model.AgentStatistics;
import com.egoonet.callcenter.model.AgentStatus;
import com.egoonet.callcenter.model.Region;
import com.egoonet.callcenter.model.StatData;
import com.egoonet.callcenter.service.AbstractModelService;
import com.egoonet.callcenter.service.AgentService;
import com.egoonet.callcenter.service.CommonModelService;

public class testAgentService extends TestCase
{
	ApplicationContext context = null;
	AgentService service = null;
	CommonModelService modelService = null;
	AbstractModelService statAgentDataService;
	AbstractModelService statGroupDataService;
	AbstractModelService statQueueDataService;
	AbstractModelService statService;

	Logger log = Logger.getLogger(testAgentService.class);

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		context = new ClassPathXmlApplicationContext("classpath:beans.xml");
		// modelService = (CommonModelService) context.getBean("agentService");
		// statAgentDataService = (AbstractModelService)
		// context.getBean("statAgentDataService");
		statService = (AbstractModelService) context.getBean("statService");
		// modelService =
		// (CommonModelService)context.getBean("agentGroupService");
		// modelService =
		// (CommonModelService)context.getBean("agentQueueService");
	}

	public void testAddAgent()
	{

		Agent agent = new Agent();
		agent.setAgentName("JetQin1");
		agent.setAgentAlias("秦枫");
		agent.setAgentNo("655256");
		agent.setAgentStatus(AgentStatus.BUSY);

		modelService.saveModel(agent);
		agent = new Agent();
		agent.setAgentName("JetQin43");
		agent.setAgentAlias("秦枫");
		agent.setAgentNo("655256");
		agent.setAgentStatus(AgentStatus.BUSY);
		modelService.saveModel(agent);

		List<Object> agents = modelService.getModels();
		for ( Object a : agents )
		{
			Agent a2 = (Agent) a;
			System.out.println(a2.getAgentAlias());
		}
	}

	public void testModelService()
	{
		List<Region> regions = new ArrayList<Region>();

		Region region = new Region();
		region.setRegionName("xi'an");
		region.setRegionDescription("xi'an");
		region.setCreateTime(new Date());
		region.setUpdateTime(new Date());
		regions.add(region);

		AgentGroup group = new AgentGroup();
		group.setAgents(null);
		group.setGroupDescription("alipay money");
		group.setGroupName("xian");
		group.setQueues(null);
		group.setRegion(regions);

		modelService.saveModel(group);
	}

	public void testAddStat()
	{
		StatData data = new AgentStatistics();
		data.setCreateTime(new Date());
		data.setUpdateTime(new Date());
		data.setProperties("properties");
		// data.setRangevalue("NaN");
		data.setReferenceID(30);
		data.setSubscribeName("65525");
		data.setSubscribeType("Total_Calls");
		data.setStatvalue(50);

		statService.saveModel(data);
	}

	public void testGetStat()
	{
		modelService.setRequestSql("from AgentStatistics where referenceID = :id");
		StatData data = (StatData) modelService.getModel(30);
		System.out.println(data.toString());
	}

	public void testGetSubscribe()
	{
		List<String> object = statAgentDataService.getSubscribeName();
		for ( String msg : object )
		{
			System.out.println("msg=" + msg);
		}
	}

	public void testUpdateStat()
	{
		modelService.setRequestSql("from AgentStatistics where referenceID = :id");
		StatData data = (StatData) modelService.getModel(30);
		System.out.println(data.toString());
		data.setUpdateTime(new Date());
		data.setStatvalue(500);
		System.out.println(data.toString());
	}

	public void testGetData()
	{
		// StatData data = (StatData) statService.getModel(2);
		statService.setSelectSQL("from QueueStatistics where  referenceID > 0");
		List data = statService.getModels();
		for ( Object d : data )
		{
			System.out.println(d.toString());
		}
		// if ( null != data )
		// {
		// data.setUpdateTime(new Date());
		// data.setStatvalue(123);
		// statService.updateModel(data);
		// }

	}

	public void testInterpreter()
	{
		Interpreter interpreter = new Interpreter();
		StatData data = null;
		try
		{
			interpreter.eval("result = 30 /0");
			interpreter.get("result");
			data = new AgentStatistics();
			data.setCreateTime(new Date());
			data.setUpdateTime(new Date());
			data.setProperties("properties");
			data.setRangevalue(2000);
			data.setReferenceID(30);
			data.setSubscribeName("65525");
			data.setSubscribeType("Total_Calls");
			data.setStatvalue(Double.parseDouble(interpreter.get("result").toString()));
		}
		catch ( NumberFormatException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch ( EvalError e )
		{
			data = new AgentStatistics();
			data.setCreateTime(new Date());
			data.setUpdateTime(new Date());
			data.setProperties("properties");
			data.setRangevalue(2000);
			data.setReferenceID(30);
			data.setSubscribeName("65525");
			data.setSubscribeType("Total_Calls");
			data.setStatvalue(0);
		}

		statService.saveModel(data);
	}

	public void testNan()
	{
		Interpreter interpreter = new Interpreter();
		StatData data = null;
		try
		{
//			interpreter.eval("result = 30 /0");
//			interpreter.get("result");
			String parse = "NaN";
			double s = Double.parseDouble(parse) + 20.0;
			System.out.println(s);
			System.out.println(Double.MAX_EXPONENT);
			System.out.println(Double.MAX_VALUE);
			System.out.println(Double.NaN);
			double d = 0.0 / 0.0;
//			parse = interpreter.get("result").toString();
			if ( Double.isNaN(Double.parseDouble("NaN")))
			{
				System.out.println("true");
			}
			else
			{
				System.out.println("false");
			}
		}
		catch ( Exception Ex )
		{
		}
	}

	public void testDeleteModels()
	{
		statService.setDeleteAllSQL("delete from AgentStatistics where referenceID > 0 ");
		statService.deleteAllModel();
	}

	public void testPaseFilter()
	{
		String filter = "D=S,F=K";
		Map<String, String> filterMap = new HashMap<String, String>();

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
						System.out.println("key=" + key.substring(0, mark) + ",value=" + key.substring(mark + 1));
					}
				}
			}

		}
	}
}

package com.egoonet.callcenter.controller.statserver;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import bsh.EvalError;
import bsh.Interpreter;

import com.egoonet.callcenter.model.GroupStatistics;
import com.egoonet.callcenter.model.QueueStatistics;
import com.egoonet.callcenter.service.StatService;

public class ProcessSelfDefinitionMessageTask
{

	// 订阅信息类型,座席组,座席,队列

	private static int DEFINITIONID = 0;
	private final static int FIRST = 0;
	private final static double DEFAULTVALUE = 0.0;

	private StatService service;
	private String queueValue;
	private String groupValue;

	private String definitionQueueMsg;
	private String definitionGroupMsg;
	private static Map<String, Double> queueValueMap = new HashMap<String, Double>();
	private static Map<String, Double> groupValueMap = new HashMap<String, Double>();

	private Interpreter interpreter = new Interpreter();
	private Logger log = Logger.getLogger(ProcessSelfDefinitionMessageTask.class);

	public ProcessSelfDefinitionMessageTask()
	{
	}

	public void storeQueueMapValue()
	{
		service.setSelectSQL(getQueueValue());
		List queueValues = service.getModels();
		queueValueMap.clear();
		if ( null == queueValues )
		{
			return;
		}
		for ( Object value : queueValues )
		{
			QueueStatistics statics = (QueueStatistics) value;
			queueValueMap.put(statics.getSubscribeType(), statics.getStatvalue());
		}

	}

	public void storeGroupMapValue()
	{
		service.setSelectSQL(getGroupValue());
		List queueValues = service.getModels();
		groupValueMap.clear();
		if ( null == queueValues )
		{
			return;
		}
		for ( Object value : queueValues )
		{
			GroupStatistics statics = (GroupStatistics) value;
			groupValueMap.put(statics.getSubscribeType(), statics.getStatvalue());
		}

	}

	private Map parseMsg(String expression, Map values)
	{
		Map<String, String> expressionMap = new HashMap<String, String>();
		if ( null != expression && !"".equals(expression) )
		{
			String[] tokens = getDefinitionQueueMsg().split(",");
			if ( null != tokens && tokens.length > 0 )
			{
				for ( String token : tokens )
				{
					String[] elements = token.split(" ");
					String temp = token;
					for ( String value : elements )
					{
						if ( values.containsKey(value) )
						{
							temp = temp.replace(value, values.get(value).toString());
						}
					}
					expressionMap.put(elements[FIRST], temp);
				}
			}
		}
		return expressionMap;
	}

	public void storeQueueValues(String target)
	{
		log.info("process store queue msg in database");
		Map<String, String> queueMap = parseMsg(getDefinitionQueueMsg(), queueValueMap);

		Iterator it = queueMap.entrySet().iterator();
		while ( it.hasNext() )
		{
			Map.Entry<String, Double> entry = (Entry<String, Double>) it.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			service.setByName("from QueueStatistics where stattype = :stattype and subscribeName = :subscribeName and subscribeType = :subscribeType");
			QueueStatistics statics = (QueueStatistics) service.getModelByName("QUEUE", target, key.toString());
			try
			{
				interpreter.eval(value.toString());
				if ( null == statics )
				{
					statics = new QueueStatistics();
					statics.setSubscribeName(target);
					statics.setSubscribeType(key.toString());
					statics.setReferenceID(getRefID());
					String parsevalue = interpreter.get(key.toString()).toString();
					double doubleValue = Double.parseDouble(parsevalue);
					if ( null != parsevalue && !"".equals(parsevalue) && !Double.isNaN(doubleValue) )
					{
						statics.setStatvalue(doubleValue);
					}
					else
					{
						statics.setStatvalue(DEFAULTVALUE);
					}
					statics.setUpdateTime(new Date());
					statics.setCreateTime(new Date());
					service.saveModel(statics);
					log.info("[definition queue message]" + statics.toString());
				}
				else
				{
					statics.setUpdateTime(new Date());
					String parsevalue = interpreter.get(key.toString()).toString();
					double doubleValue = Double.parseDouble(parsevalue);
					if ( null != parsevalue && !"".equals(parsevalue) && !Double.isNaN(doubleValue) )
					{
						statics.setStatvalue(doubleValue);
					}
					else
					{
						statics.setStatvalue(DEFAULTVALUE);
					}
					service.updateModel(statics);
				}

				log.debug(key + "=" + interpreter.get(key.toString()));
			}
			catch ( EvalError e )
			{
				log.error(e.getMessage());
			}
		}

	}

	public void storeGroupValues(String target)
	{
		log.info("process store group msg in database");
		Map<String, String> groupMap = parseMsg(getDefinitionGroupMsg(), groupValueMap);
		Iterator it = groupMap.entrySet().iterator();
		while ( it.hasNext() )
		{
			Map.Entry<String, Double> entry = (Entry<String, Double>) it.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			service.setByName("from GroupStatistics where stattype = :stattype and subscribeName = :subscribeName and subscribeType = :subscribeType");
			GroupStatistics statics = (GroupStatistics) service.getModelByName("GROUP", target, key.toString());
			try
			{
				interpreter.eval(value.toString());

				if ( null == statics )
				{
					statics = new GroupStatistics();
					statics.setSubscribeName(target);
					statics.setSubscribeType(key.toString());
					statics.setReferenceID(getRefID());
					String parsevalue = interpreter.get(key.toString()).toString();
					double doubleValue = Double.parseDouble(parsevalue);
					if ( null != parsevalue && !"".equals(parsevalue) && !Double.isNaN(doubleValue) )
					{
						statics.setStatvalue(doubleValue);
					}
					else
					{
						statics.setStatvalue(DEFAULTVALUE);
					}
					statics.setUpdateTime(new Date());
					statics.setCreateTime(new Date());
					service.saveModel(statics);
					log.info("[definition group message]" + statics.toString());
				}
				else
				{
					statics.setUpdateTime(new Date());
					String parsevalue = interpreter.get(key.toString()).toString();
					double doubleValue = Double.parseDouble(parsevalue);
					if ( null != parsevalue && !"".equals(parsevalue) && !Double.isNaN(doubleValue) )
					{
						statics.setStatvalue(doubleValue);
					}
					else
					{
						statics.setStatvalue(DEFAULTVALUE);
					}
					service.updateModel(statics);
				}
				log.info(key + "=" + interpreter.get(key.toString()));

			}
			catch ( EvalError e )
			{
				log.error(e.getMessage());
			}
		}

	}

	public String getDefinitionGroupMsg()
	{
		return definitionGroupMsg;
	}

	public void setDefinitionGroupMsg(String definitionGroupMsg)
	{
		this.definitionGroupMsg = definitionGroupMsg;
	}

	public String getDefinitionQueueMsg()
	{
		return definitionQueueMsg;
	}

	public void setDefinitionQueueMsg(String definitionQueueMsg)
	{
		this.definitionQueueMsg = definitionQueueMsg;
	}

	public String getQueueValue()
	{
		return queueValue;
	}

	public void setQueueValue(String queueValue)
	{
		this.queueValue = queueValue;
	}

	public String getGroupValue()
	{
		return groupValue;
	}

	public void setGroupValue(String groupValue)
	{
		this.groupValue = groupValue;
	}

	public void setService(StatService service)
	{
		this.service = service;
	}

	public StatService getService()
	{
		return service;
	}

	private void startProcessMsg()
	{
		log.info("start process group definition Msg");
		service.setSubscribe("select groupName from AgentGroup");
		List<String> groupNames = service.getSubscribeName();
		for ( String group : groupNames )
		{
			service.setTargetName(group);
			storeGroupMapValue();
			storeGroupValues(group);
		}

		log.info("start process queue definition Msg");
		service.setSubscribe("select queueName from AgentQueue");
		List<String> queueNames = service.getSubscribeName();
		for ( String queue : queueNames )
		{
			service.setTargetName(queue);
			storeQueueMapValue();
			storeQueueValues(queue);
		}
	}

	private synchronized int getRefID()
	{
		DEFINITIONID = DEFINITIONID + 1;
		return (0 - DEFINITIONID);
	}

	protected void execute()
	{
		startProcessMsg();
	}

}

package com.egoonet.callcenter.controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.context.ApplicationContext;

import com.egoonet.callcenter.conf.ConfigParameter;
import com.egoonet.callcenter.controller.configserver.ProcessHandler;
import com.egoonet.callcenter.controller.configserver.SubscribeRequest;
import com.genesyslab.platform.applicationblocks.com.ConfServiceFactory;
import com.genesyslab.platform.applicationblocks.com.IConfService;
import com.genesyslab.platform.applicationblocks.commons.broker.BrokerServiceFactory;
import com.genesyslab.platform.applicationblocks.commons.broker.EventBrokerService;
import com.genesyslab.platform.commons.protocol.ChannelState;
import com.genesyslab.platform.commons.protocol.Endpoint;
import com.genesyslab.platform.commons.protocol.Message;
import com.genesyslab.platform.configuration.protocol.ConfServerProtocol;

public class ConfigServerController
{
	private ConfServerProtocol confServerProtocol;
	private ChannelState state;
	private ConfigParameter parameter;
	private IConfService confService = null;
	private EventBrokerService confMessageBroker;
	private SubscribeRequest subscribeRequest = new SubscribeRequest();
	private Logger log = Logger.getLogger(StatServerController.class);

	public ConfigServerController()
	{

	}

	public ConfigServerController(ApplicationContext context)
	{
		setParameter((ConfigParameter) context.getBean("confParameter"));
	}

	public void startup()
	{
		while ( true )
		{
			try
			{
				if ( confServerProtocol == null )
				{
					URI uri = new URI(parameter.getUriServer());
					Endpoint endp = new Endpoint(uri);
					log.debug("config parameter=" + parameter.toString());
					confServerProtocol = new ConfServerProtocol(endp);

					confServerProtocol.setTimeout(parameter.getTimeout());
					confServerProtocol.setClientName(parameter.getClientName());
					confServerProtocol.setUserName(parameter.getUserName());
					confServerProtocol.setUserPassword(parameter.getPassword());
					confServerProtocol.setUseForwardCompatibility(true);
					confServerProtocol.setUseSession(true);
					confServerProtocol.open(parameter.getTimeout());

					confMessageBroker = BrokerServiceFactory.CreateEventBroker(confServerProtocol);
					confService = ConfServiceFactory.createConfService(confServerProtocol, confMessageBroker);

					subscribeRequest.setConfService(confService);
					startListener();
				}

				state = confServerProtocol.getState();

				if ( state == ChannelState.Opened )
				{
					Message event = confServerProtocol.receive(5000);
					if ( event != null )
					{
						//log.info("[Event]="+event.toString());
					}
				}
				else if ( state == ChannelState.Closed )
				{
					confServerProtocol = null;
				}
			}
			catch ( Exception ex )
			{
			}
		}
	}

	public void startListener()
	{
		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		LoggingFilter logFilter = new LoggingFilter();
		ProcessHandler handler = new ProcessHandler();
		handler.setRequest(subscribeRequest);
		acceptor.getFilterChain().addLast("logger", logFilter);
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		acceptor.setHandler(handler);
		log.info("------------------listener start---------------------");
		try
		{
			acceptor.bind(new InetSocketAddress(parameter.getPort()));
		}
		catch ( IOException e )
		{
			log.error(e.getMessage());
		}
	}

	public void setParameter(ConfigParameter parameter)
	{
		this.parameter = parameter;
	}

	public ConfigParameter getParameter()
	{
		return parameter;
	}
}

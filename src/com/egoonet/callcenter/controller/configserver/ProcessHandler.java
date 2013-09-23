package com.egoonet.callcenter.controller.configserver;

import java.util.List;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class ProcessHandler extends IoHandlerAdapter
{
	private SubscribeRequest request = null;

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception
	{
		super.exceptionCaught(session, cause);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception
	{
		String msg = (String) message;
		System.out.println("Server recv message :" + msg);
		String str = getMsg();
		session.write("size=" + str);
		if ( msg.equals("quit") )
		{
			session.close();
		}
	}

	private String getMsg()
	{
		List<String> groups = request.getGroups();
		StringBuffer buffer = new StringBuffer();
		for ( String group : groups )
		{
			buffer.append(group);
			buffer.append("-");
		}
		return buffer.toString();
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception
	{
		session.close(true);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception
	{
		super.sessionCreated(session);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception
	{
		session.write("response");
	}

	public void setRequest(SubscribeRequest request)
	{
		this.request = request;
	}

	public SubscribeRequest getRequest()
	{
		return request;
	}
}

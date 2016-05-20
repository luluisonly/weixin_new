package com.zaofans.utils;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueSession;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.bokesoft.myerp.common.SharedBundle;

public class MsgQueueSender {

	private static String jmsUrl = null;
	private static String jmsName = null;
	private static String jmsPass = null;
	private static String queueName = null;

	private static MsgQueueSender ms = null;

	private Connection connection;

	private MsgQueueSender() {
		initMsgQueue();
	}

	private void initMsgQueue() {
		ConnectionFactory factory;
		queueName = SharedBundle.getProperties("server.soa.weixin.jms.queueName");
		jmsUrl = SharedBundle.getProperties("server.soa.weixin.jms.url");
		jmsName = SharedBundle.getProperties("server.soa.weixin.jms.name");
		jmsPass = SharedBundle.getProperties("server.soa.weixin.jms.pass");
		try {
			System.out.println(jmsName + "," + jmsPass + "," + jmsUrl);
			factory = new ActiveMQConnectionFactory(jmsName, jmsPass, jmsUrl);
			connection = factory.createConnection();
			connection.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static MsgQueueSender getInstance() {
		if (ms == null) {
			synchronized (MsgQueueSender.class) {
				ms = new MsgQueueSender();
			}
		}
		return ms;
	}

	public void sendTextMsg(String msg) throws JMSException {
		QueueSession session = (QueueSession) connection.createSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		try {
			Queue que = session.createQueue(queueName);
			Message message = session.createTextMessage(msg);
			message.setStringProperty("__action", "CRM_WEIXIN");
			session.createSender(que).send(message);
		} catch (JMSException e) {
			throw e;
		}finally{
			session.close();
		}
	}

	public void sendObjMsg(Serializable obj) throws JMSException {
		QueueSession session = (QueueSession) connection.createSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		try {
			Queue que = session.createQueue(queueName);
			Message message = session.createObjectMessage(obj);
			message.setStringProperty("__action", "CRM_WEIXIN");
			session.createSender(que).send(message);
		} catch (JMSException e) {
			throw e;
		}finally{
			session.close();
		}
	}

}

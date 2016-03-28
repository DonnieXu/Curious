package com.cn.curious.alerts;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.cn.curious.bean.User;

public class AlertServiceImpl implements AlertService{
	@Autowired
	JmsTemplate jmsTemplate;

	@Override
	public void sendUserAlert(final User user) {
		// TODO Auto-generated method stub
		jmsTemplate.send(new MessageCreator() {			
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(user);
			}
		});
	}
	
	

}

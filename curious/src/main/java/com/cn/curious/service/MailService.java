package com.cn.curious.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.ServletContextResource;

import com.cn.curious.bean.MailBean;

@Component("mailService")
public class MailService {
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private SimpleMailMessage simpleMailMessage;

	@Autowired
	private JmsTemplate jmsTemplate;

	/**
	 * 发送只含有普通文本的邮件
	 * 
	 * @param from
	 * @param to
	 * @param subject
	 * @param content
	 * @return
	 */
	public boolean sendSimpleMail(String from, String to, String subject,
			String content) {
		final MailBean mailBean = new MailBean(from, to, subject, content,
				MailBean.SIMPLE_MAIL, null, null);
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				return session.createObjectMessage(mailBean);
			}
		});
		return true;
	}

	/**
	 * 发送带有富文本内容和/或带有附件的邮件
	 * 
	 * @param from
	 * @param to
	 * @param title
	 * @param content
	 * @param inlines
	 *            富文本HashMap
	 * @param attachments
	 *            附件列表HashMap
	 * @return
	 */
	public boolean sendHtmlMail(String from, String to, String subject,
			String content, HashMap<String, String> inlines,
			HashMap<String, String> attachments) {
		final MailBean mailBean = new MailBean(from, to, subject, content,
				MailBean.HTML_MAIL, inlines, attachments);
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				return session.createObjectMessage(mailBean);
			}
		});
		return true;
	}

	/**
	 * 消息监听方法，见配置文件curious-message.xml
	 */
	public void mailHandler(MailBean mailBean) {
		if (mailBean.getType() == MailBean.SIMPLE_MAIL) {
			simpleMailMessage.setTo(mailBean.getTo());
			simpleMailMessage.setText(mailBean.getContent());
			simpleMailMessage.setSubject(mailBean.getSubject());
			mailSender.send(simpleMailMessage);
		} else if (mailBean.getType() == MailBean.HTML_MAIL) {
			try {
				MimeMessage mailMessage = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mailMessage,
						true);
				helper.setFrom(mailBean.getFrom());
				helper.setTo(mailBean.getTo());
				helper.setSubject(mailBean.getSubject());
				helper.setText(mailBean.getContent(), true);
				// 如果含有富文本
				HashMap<String, String> inlines = mailBean.getInlines();
				if (inlines != null) {
					Iterator<Entry<String, String>> iterator = inlines
							.entrySet().iterator();
					while (iterator.hasNext()) {
						Entry<String, String> entry = iterator.next();
//						ClassPathResource classPathResource = new ClassPathResource(
//								entry.getValue());
//						helper.addInline(entry.getKey(), classPathResource);
//						FileSystemResource fileSystemResource = new FileSystemResource(
//								entry.getValue());
//						helper.addInline(entry.getKey(), fileSystemResource);
						ServletContext servletContext= ContextLoader.getCurrentWebApplicationContext().getServletContext();
						ServletContextResource servletContextResource = new ServletContextResource(servletContext, entry.getValue());
						helper.addInline(entry.getKey(), servletContextResource);
					}
				}
				// 如果含有附件
				HashMap<String, String> attachments = mailBean.getAttachments();
				if (attachments != null) {
					Iterator<Entry<String, String>> iterator = attachments
							.entrySet().iterator();
					while (iterator.hasNext()) {
						Entry<String, String> entry = iterator.next();
//						FileSystemResource fileSystemResource = new FileSystemResource(
//								entry.getValue());
//						helper.addAttachment(entry.getKey(), fileSystemResource);
						ServletContext servletContext= ContextLoader.getCurrentWebApplicationContext().getServletContext();
						ServletContextResource servletContextResource = new ServletContextResource(servletContext, entry.getValue());
						helper.addAttachment(entry.getKey(), servletContextResource);
					}
				}
				mailSender.send(mailMessage);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}

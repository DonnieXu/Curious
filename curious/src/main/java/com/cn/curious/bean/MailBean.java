package com.cn.curious.bean;

import java.io.Serializable;
import java.util.HashMap;

public class MailBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3616704907813843729L;
	public static final int SIMPLE_MAIL = 1;
	public static final int HTML_MAIL = 2;
	private String from;
	private String to;
	private String subject;
	private String content;
	private int type;
	private HashMap<String, String> inlines;
	private HashMap<String, String> attachments;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public HashMap<String, String> getInlines() {
		return inlines;
	}

	public void setInlines(HashMap<String, String> inlines) {
		this.inlines = inlines;
	}

	public HashMap<String, String> getAttachments() {
		return attachments;
	}

	public void setAttachments(HashMap<String, String> attachments) {
		this.attachments = attachments;
	}

	public MailBean(String from, String to, String subject, String content,
			int type, HashMap<String, String> inlines,
			HashMap<String, String> attachments) {
		super();
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.content = content;
		this.type = type;
		this.inlines = inlines;
		this.attachments = attachments;
	}

}
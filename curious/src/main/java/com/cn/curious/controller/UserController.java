package com.cn.curious.controller;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;  
import org.springframework.ui.Model;  
import org.springframework.web.bind.annotation.RequestMapping;  

import com.cn.curious.bean.User;
import com.cn.curious.service.MailService;
import com.cn.curious.service.UserService;
  
@Controller  
@RequestMapping("/user")  
public class UserController {  
    @Autowired  
    private UserService userService;  
    
    @Autowired
    private MailService mailService;
      
    @RequestMapping("/showUser")  
    public String toIndex(HttpServletRequest request,Model model){  
        int userId = Integer.parseInt(request.getParameter("id"));  
        User user = this.userService.getUserById(userId);  
        model.addAttribute("user", user);  
        return "showUser";  
    }
    
    @RequestMapping("/sendMail")
    public String sendMail(){
    	String from ="ch2_27@sina.com";
    	String subject = "Hello";
    	String to = "604865895@qq.com";
    	String content = "Click "
    			+ "<a href=\""
    			+ "http://donniexu.github.io/"
    			+ "\">DonnieXu</a> to read some blogs.";
    	mailService.sendHtmlMail(from, to, subject, content, null, null);
    	return "sendMailResult";
    }
    
    @RequestMapping("/sendImageMail")
    public String sendImageMail(){
    	String from = "ch2_27@sina.com";
    	String subject = "Images";
    	String to = "604865895@qq.com";
    	String content = "Hay baby~<br/>"
    			+ "<img src='cid:dog'>";
    	HashMap<String, String> inlines = new HashMap<String, String>();
    	inlines.put("dog", "/images/dog.jpg");
    	mailService.sendHtmlMail(from, to, subject, content, inlines, null);
    	return "sendMailResult";
    }
    
    @RequestMapping("/sendAttachmentMail")
    public String sendAttachmentMail(){
    	String from = "ch2_27@sina.com";
    	String subject = "Images";
    	String to = "604865895@qq.com";
    	String content = "Hay baby~<br/>";
    	HashMap<String, String> attachments = new HashMap<String, String>();
    	attachments.put("dog.jpg", "/images/dog.jpg");
    	mailService.sendHtmlMail(from, to, subject, content, null, attachments);
    	return "sendMailResult";
    }
}  
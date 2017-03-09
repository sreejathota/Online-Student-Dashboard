package com.java.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Service("mailservice")
public class EmailConfirm {
	@Autowired
	private JavaMailSender mailSender;  
	public void setMailSender(JavaMailSender mailSender) {  
        this.mailSender = mailSender;  
    } 
	
	 /*@Autowired
	    private SimpleMailMessage preConfiguredMessage;*/
	 public void sendMail(final String to,final String msg) {  
	        try{  
	        MimeMessage message = mailSender.createMimeMessage();  
	  
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);  
	        helper.setFrom("sreejap19@gmail.com");  
	        helper.setTo(to);  
	        helper.setSubject("Email Confirmation");  
	        helper.setText(msg,true); 
	  
	        // attach the file  
	       // FileSystemResource file = new FileSystemResource(new File("D:/ucm.txt"));  
	       // helper.addAttachment("myucm.txt", file);//image will be sent by this name  
	  
	        mailSender.send(message);  
	        }catch(MessagingException e){e.printStackTrace();}  
	    }  
    
   /* public void sendMail(final String from, final String to,final String subject,final String msg) {  
              
        MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {  
              
                public void prepare(MimeMessage mimeMessage) throws Exception {  
                   mimeMessage.setRecipient(Message.RecipientType.TO,new InternetAddress(to));  
                   mimeMessage.setFrom(new InternetAddress(from));  
                   mimeMessage.setSubject(subject);  
                   mimeMessage.setText(msg,"text/html");  
                }  
        };  
        mailSender.send(messagePreparator);  
    }  */
  /* public void sendPreConfiguredMail(String to,String message) 
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage(preConfiguredMessage);
        //mailMessage.setTo(to);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }*/
}

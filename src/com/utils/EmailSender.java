package com.utils;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.utils.concurrent.ThreadPool;

import java.util.Properties;

public class EmailSender {

	private static EmailSender INTANCE = null;

	private String username = "no.reply.acm.iscte@gmail.com";
	private String password = "VLl6Tg4fdZZlCh7t2vmPhwqoMkw3Mx5H1EvpsGrVl7";

	private ThreadPool threadPool = new ThreadPool(10);

	private Session session;

	private static String activationLink = "http://localhost:8081/WebSite_ACM/activate";



	public EmailSender() {

		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		session = Session.getInstance(prop,new Authenticator() {
			
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
			
		});
	}





	/**
	 * @return the INTANCE
	 */
	public static EmailSender getInstance() {
		if(INTANCE == null) INTANCE = new EmailSender();
		return INTANCE;
	}





	/**
	 * Sends an email
	 * 
	 * @param receiver receiver email
	 * @param subject email subject
	 * @param content email content
	 * 
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void sendTextEmail(String receiver, String subject, String content) {
		threadPool.submit(()-> {
			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(username));
				message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(receiver));
				message.setSubject(subject);
				message.setText(content);
				System.out.println("oi");
				Transport.send(message);
				System.out.println("boi");
			}catch(MessagingException e ) {
				e.printStackTrace();
			}

			System.out.println("Email send to: "+receiver+"  --  "+Thread.currentThread());
		});
	}





	/**
	 * Sends the Activation email 
	 * 
	 * @param email
	 * @param activationKey
	 */
	public void sendActivationMail(String email, String activationKey) {
		String subject = "Account Activation Email";
		String content = "Please to activate your account click this link = "+activationLink +"?key="+activationKey;
		sendTextEmail(email, subject, content);
	}
	
	
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			EmailSender.getInstance().sendTextEmail("paripac927@xmailweb.com","efvfv", "fbfbegfgg");
		}
	}
}


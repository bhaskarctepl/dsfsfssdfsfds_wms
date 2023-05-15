package com.hillspet.wearables.common.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailUtil {

	private String mailFrom;
	private String username;
	private String key;
	private String host;
	private String port;
	private String sslEnable;
	private String smtpAuth;
	private String tlsEnable;

	public boolean sendMail(String recipient, String subject, String messageBody) {

		Properties props = new Properties();
		props.put("mail.smtp.auth", smtpAuth);
		// Uncomment this for UAT & PROD
		props.put("mail.smtp.ssl.enable", sslEnable);
		// Uncomment this for DEV
		// props.put("mail.smtp.starttls.enable", tlsEnable);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, key);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailFrom));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
			message.setSubject(subject);
			message.setContent(messageBody, "text/html");

			Transport.send(message);
			return true;
		} catch (MessagingException e) {
			return false;
		}
	}
}

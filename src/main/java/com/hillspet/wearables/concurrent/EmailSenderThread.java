package com.hillspet.wearables.concurrent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hillspet.wearables.common.utils.BeanUtil;
import com.hillspet.wearables.common.utils.SendGridEmailUtil;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EmailSenderThread implements Runnable {

	private static final Logger LOGGER = LogManager.getLogger(EmailSenderThread.class);

	private SendGridEmailUtil emailUtil = BeanUtil.getBean(SendGridEmailUtil.class);

	private String email;
	private String subject;
	private String messageBody;

	public EmailSenderThread(String email, String subject, String messageBody) {
		this.email = email;
		this.subject = subject;
		this.messageBody = messageBody;
	}

	@Override
	public void run() {
		try {
			LOGGER.info("Sending an email started to {}", email);
			boolean flag = emailUtil.sendMail(email, subject, messageBody);

			if (flag) {
				LOGGER.info("Email has been sent successfully to {}", email);
			} else {
				LOGGER.error("Mail sending failed for email {}", email);
			}
		} catch (Exception e) {
			LOGGER.error("Error while sedningan email to {}", e);
		}
	}
}
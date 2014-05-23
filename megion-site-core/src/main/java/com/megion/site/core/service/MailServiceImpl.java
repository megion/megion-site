package com.megion.site.core.service;

import info.magnolia.module.mail.MailModule;
import info.magnolia.module.mail.MgnlMailFactory;
import info.magnolia.module.mail.handlers.MgnlMailHandler;
import info.magnolia.module.mail.templates.MgnlEmail;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.megion.site.core.exception.MegionsiteException;
import com.megion.site.core.model.EmailHeader;

@Service
public class MailServiceImpl implements MailService {

	@Override
	public void sendMail(EmailHeader header, String templateId,
			Map<String, Object> params) throws Exception {
		@SuppressWarnings("deprecation")
		MgnlMailFactory mailFactory = MailModule.getInstance().getFactory();

		MgnlEmail mail = mailFactory.getEmailFromTemplate(templateId, params);
		if (mail == null) {
			throw new MegionsiteException("Mail template for id [" + templateId
					+ "] not found");
		}

		mail.setToList(header.getToList());
		mail.setFrom(header.getFrom());
		mail.setSubject(header.getSubject());
		mail.setBodyFromResourceFile();

		MgnlMailHandler mmh = mailFactory.getEmailHandler();
		mmh.prepareAndSendMail(mail);
	}
}
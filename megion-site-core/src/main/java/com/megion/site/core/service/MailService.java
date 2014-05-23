package com.megion.site.core.service;

import java.util.Map;

import com.megion.site.core.model.EmailHeader;

public interface MailService {

	void sendMail(EmailHeader header, String templateId,
			Map<String, Object> params) throws Exception;

}

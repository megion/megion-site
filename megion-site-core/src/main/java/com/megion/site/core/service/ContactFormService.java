package com.megion.site.core.service;

import info.magnolia.module.blossom.dialog.TabBuilder;

import javax.jcr.Node;
import javax.servlet.http.HttpServletRequest;

import com.megion.site.core.enums.TabNumber;
import com.megion.site.core.model.ContactForm;
import com.megion.site.core.model.ContactFormNotification;
import com.megion.site.core.model.yandex.cleanweb.CheckSpamResult;
import com.megion.site.core.model.yandex.cleanweb.GetCaptchaResult;

public interface ContactFormService {

	void sendMail(ContactFormNotification contactFormNotification,
			ContactForm contactForm) throws Exception;

	void addContactFormDialogControls(TabBuilder tabBuilder, TabNumber tabNumber);

	ContactForm getContactForm(Node contactFormComponent);

	/**
	 * Послать GET для проверки на спам и получить ответ ответ в виде XML.
	 * 
	 * <a href="http://api.yandex.ru/cleanweb/doc/dg/concepts/check-spam.xml">
	 * Документация</a>
	 */
	CheckSpamResult checkSpam(ContactFormNotification contactFormNotification, String yandexKey,
			HttpServletRequest request) throws Exception;

	GetCaptchaResult getCaptcha(CheckSpamResult checkSpamResult, String yandexKey)
			throws Exception;
	
	boolean checkCaptcha(ContactFormNotification contactFormNotification, String yandexKey) throws Exception;

}

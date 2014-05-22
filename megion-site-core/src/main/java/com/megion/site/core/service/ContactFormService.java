package com.megion.site.core.service;

import info.magnolia.module.blossom.dialog.TabBuilder;

import javax.jcr.Node;

import com.megion.site.core.enums.TabNumber;
import com.megion.site.core.model.ContactForm;
import com.megion.site.core.model.ContactFormNotification;

public interface ContactFormService {

	void sendMail(ContactFormNotification contactFormNotification,
			ContactForm contactForm) throws Exception;

	void addContactFormDialogControls(TabBuilder tabBuilder, TabNumber tabNumber);

	ContactForm getContactForm(Node contactFormComponent);

}

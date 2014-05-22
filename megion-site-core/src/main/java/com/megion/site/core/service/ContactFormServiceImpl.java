package com.megion.site.core.service;

import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.module.blossom.dialog.TabBuilder;
import info.magnolia.module.mail.MailModule;
import info.magnolia.module.mail.MgnlMailFactory;
import info.magnolia.module.mail.handlers.MgnlMailHandler;
import info.magnolia.module.mail.templates.MgnlEmail;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megion.site.core.enums.TabNumber;
import com.megion.site.core.exception.MegionsiteException;
import com.megion.site.core.model.ContactForm;
import com.megion.site.core.model.ContactFormNotification;

@Service
public class ContactFormServiceImpl implements ContactFormService {

	@Autowired
	private TemplatingService templatingService;

	@Override
	public void sendMail(ContactFormNotification contactFormNotification,
			ContactForm contactForm) throws Exception {
		@SuppressWarnings("deprecation")
		MgnlMailFactory mailFactory = MailModule.getInstance().getFactory();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("notification", contactFormNotification);

		MgnlEmail mail = mailFactory.getEmailFromTemplate(
				"contactFormNotification", params);
		if (mail == null) {
			throw new MegionsiteException(
					"Mail template for id [contactFormNotification] not found");
		}

		mail.setToList(contactForm.getToEmailList());
		if (contactForm.isHasFromEmail()) {
			mail.setFrom(contactForm.getFromEmail());
		} else {
			mail.setFrom(contactFormNotification.getEmail());
		}
		if (contactForm.isHasEmailSubject()) {
			mail.setSubject(contactForm.getEmailSubject());
		} else {
			mail.setSubject(contactFormNotification.getMessageTitle());
		}
		mail.setBodyFromResourceFile();

		MgnlMailHandler mmh = mailFactory.getEmailHandler();
		mmh.prepareAndSendMail(mail);
	}

	@Override
	public void addContactFormDialogControls(TabBuilder tabBuilder,
			TabNumber tabNumber) {
		if (TabNumber.FIRST.equals(tabNumber)) {
			tabBuilder.addEdit("title", "Заголовок формы", "");
			tabBuilder
					.addEdit("fromEmail", "От",
							"Email адреса отправителя. Если оставить пустым тогда назначится с формы");
			tabBuilder.addEdit("toEmailList", "Кому",
					"Email адреса для отправки разделенные через ';'");
			tabBuilder
					.addEdit("emailSubject", "Тема",
							"Тема письма. Если оставить пустым тогда назначится с формы");
			tabBuilder
					.addEdit(
							"yandexKey",
							"Ключ yandex",
							"Ключ API yandex для проверки Captcha. Подробнее http://api.yandex.ru/cleanweb/")
					.setRequired(true);
		} else {
			tabBuilder.addEdit("successTitle",
					"Заголовок успешности отправки сообщения", "");
			tabBuilder.addFckEditor("successText",
					"Текст успешности отправки сообщения", "");
		}
	}

	@Override
	public ContactForm getContactForm(Node contactFormComponent) {
		ContactForm dialogData = new ContactForm();
		dialogData.setTitle(PropertyUtil.getString(contactFormComponent,
				"title"));
		dialogData.setFromEmail(PropertyUtil.getString(contactFormComponent,
				"fromEmail"));
		dialogData.setToEmailList(PropertyUtil.getString(contactFormComponent,
				"toEmailList"));
		dialogData.setEmailSubject(PropertyUtil.getString(contactFormComponent,
				"emailSubject"));
		dialogData.setSuccessTitle(PropertyUtil.getString(contactFormComponent,
				"successTitle"));

		dialogData.setSuccessText(templatingService.getNodePropertyAsHtml(
				contactFormComponent, "successText"));

		dialogData.setYandexKey(PropertyUtil.getString(contactFormComponent,
				"yandexKey"));

		return dialogData;
	}

}
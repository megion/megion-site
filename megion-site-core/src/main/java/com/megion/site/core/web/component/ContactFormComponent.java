package com.megion.site.core.web.component;

import info.magnolia.module.blossom.annotation.TabFactory;
import info.magnolia.module.blossom.annotation.Template;
import info.magnolia.module.blossom.annotation.TemplateDescription;
import info.magnolia.module.blossom.dialog.TabBuilder;

import javax.jcr.Node;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.megion.site.core.enums.TabNumber;
import com.megion.site.core.model.ContactForm;
import com.megion.site.core.model.ContactFormNotification;
import com.megion.site.core.model.yandex.cleanweb.CheckSpamResult;
import com.megion.site.core.model.yandex.cleanweb.GetCaptchaResult;
import com.megion.site.core.service.ContactFormService;
import com.megion.site.core.web.validator.ContactFormValidator;

/**
 * Displays a contact form and a "Thank You" page after the contact form is
 * submitted.
 */
@Controller
@Template(title = "Contact form", id = "megion-site:components/contactForm")
@TemplateDescription("Задать вопрос")
public class ContactFormComponent {

	private static final Logger log = LoggerFactory
			.getLogger(ContactFormComponent.class);

	@Autowired
	private ContactFormService contactFormService;

	@RequestMapping("/contactForm")
	public String handleRequest(ModelMap model,
			@ModelAttribute ContactFormNotification contactFormNotification,
			BindingResult result, HttpServletRequest request, Node node) {

		ContactForm contactForm = contactFormService.getContactForm(node);
		model.put("contactForm", contactForm);

		try {
			// проверить на спам. Данная проверка сейчас используется только для
			// получения id проверки. В будущем можно избавить добросовестных
			// пользователей от ввода Captcha.
			CheckSpamResult checkSpamResult = contactFormService.checkSpam(
					contactFormNotification, contactForm.getYandexKey(),
					request);
			model.put("checkSpamResult", checkSpamResult);

			// получить Captcha всегда в независимости от проверки выше
			GetCaptchaResult captchaResult = contactFormService.getCaptcha(
					checkSpamResult, contactForm.getYandexKey());
			model.put("getCaptchaResult", captchaResult);

			if ("POST".equals(request.getMethod())) {
				// проверяем ввод пользователем данных
				new ContactFormValidator(contactFormService,
						contactForm.getYandexKey()).validate(
						contactFormNotification, result);
				if (result.hasErrors()) {
					return "components/contactForm.jsp";
				}

				contactFormService.sendMail(contactFormNotification,
						contactForm);

				return "components/contactFormSubmitted.jsp";
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			model.put("errorMessage", "<b>Ошибка</b> " + e.getMessage());
			return "components/contactForm.jsp";
		}

		return "components/contactForm.jsp";
	}

	@TabFactory("Content")
	public void contentTab(TabBuilder tab) {
		contactFormService.addContactFormDialogControls(tab, TabNumber.FIRST);
	}

	@TabFactory("Success")
	public void successTab(TabBuilder tab) {
		contactFormService.addContactFormDialogControls(tab, TabNumber.SECOND);
	}

}

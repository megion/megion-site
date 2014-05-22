package com.megion.site.core.web.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.megion.site.core.exception.MegionsiteException;
import com.megion.site.core.model.ContactFormNotification;
import com.megion.site.core.model.yandex.cleanweb.CheckCaptcha;
import com.megion.site.core.service.YandexCaptchaService;

/**
 * Validating contact forms.
 */
public class ContactFormValidator implements Validator {

	private final YandexCaptchaService yandexCaptchaService;
	private final String yandexKey;

	public ContactFormValidator(YandexCaptchaService yandexCaptchaService,
			String yandexKey) {
		this.yandexCaptchaService = yandexCaptchaService;
		this.yandexKey = yandexKey;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(ContactFormNotification.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required",
				"Имя должно быть заполнено.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required",
				"E-mail должен быть заполнен.");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "message",
				"required", "Текст запроса должен быть заполнен.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "captchaText",
				"required", "Необходимо ввести символы.");

		ContactFormNotification contactFormNotification = (ContactFormNotification) target;
		if (StringUtils.isNotBlank(contactFormNotification.getEmail())) {
			if (!isValidEmailAddress(contactFormNotification.getEmail())) {
				errors.rejectValue("email", "required", null,
						"Неверный формат E-mail.");
			}
		}
		try {
			if (StringUtils
					.isNotBlank(contactFormNotification.getCaptchaText())) {
				CheckCaptcha checkCaptcha = new CheckCaptcha(
						contactFormNotification.getCheckSpamId(),
						contactFormNotification.getCaptchaKey(),
						contactFormNotification.getCaptchaText());
				boolean isValidCaptcha = yandexCaptchaService.checkCaptcha(
						checkCaptcha, yandexKey);
				if (!isValidCaptcha) {
					errors.rejectValue("captchaText", "required", null,
							"Вы неверно ввели символы. Попробуйте еще раз.");
				}
			}
		} catch (Exception e) {
			throw new MegionsiteException(e.getMessage(), e);
		}
	}

	public boolean isValidEmailAddress(String emailAddress) {
		String expression = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
		CharSequence inputStr = emailAddress;
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		return matcher.matches();
	}
}

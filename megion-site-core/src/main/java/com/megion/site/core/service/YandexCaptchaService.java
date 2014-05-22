package com.megion.site.core.service;

import javax.servlet.http.HttpServletRequest;

import com.megion.site.core.model.yandex.cleanweb.CheckSpam;
import com.megion.site.core.model.yandex.cleanweb.CheckCaptcha;
import com.megion.site.core.model.yandex.cleanweb.CheckSpamResult;
import com.megion.site.core.model.yandex.cleanweb.GetCaptchaResult;

public interface YandexCaptchaService {

	/**
	 * Послать GET для проверки на спам и получить ответ ответ в виде XML.
	 * 
	 * <a href="http://api.yandex.ru/cleanweb/doc/dg/concepts/check-spam.xml">
	 * Документация</a>
	 */
	CheckSpamResult checkSpam(CheckSpam checkSpam, String yandexKey,
			HttpServletRequest request) throws Exception;

	GetCaptchaResult getCaptcha(CheckSpamResult checkSpamResult, String yandexKey)
			throws Exception;
	
	boolean checkCaptcha(CheckCaptcha checkCaptcha, String yandexKey) throws Exception;

}

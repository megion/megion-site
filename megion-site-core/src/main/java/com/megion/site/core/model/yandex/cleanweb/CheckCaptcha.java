package com.megion.site.core.model.yandex.cleanweb;

/**
 * Данные для проверки Captcha формы 
 */
public class CheckCaptcha {

	/**
	 * Скрытое поле
	 */
	private String checkSpamId;
	
	/**
	 * Скрытое поле
	 */
	private String captchaKey;
	
	private String captchaText;

	public String getCaptchaText() {
		return captchaText;
	}

	public void setCaptchaText(String captchaText) {
		this.captchaText = captchaText;
	}

	public String getCaptchaKey() {
		return captchaKey;
	}

	public void setCaptchaKey(String captchaKey) {
		this.captchaKey = captchaKey;
	}

	public String getCheckSpamId() {
		return checkSpamId;
	}

	public void setCheckSpamId(String checkSpamId) {
		this.checkSpamId = checkSpamId;
	}

}

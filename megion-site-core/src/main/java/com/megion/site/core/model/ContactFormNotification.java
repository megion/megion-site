package com.megion.site.core.model;

/**
 * Форма задать вопрос
 */
public class ContactFormNotification {

	private String name;
	private String email;
	private String messageTitle;
	private String message;

	/**
	 * Скрытое поле
	 */
	private String checkSpamId;
	
	/**
	 * Скрытое поле
	 */
	private String captchaKey;
	
	private String captchaText;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

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

package com.megion.site.core.model;

/**
 * Данные формы диалога "Задать вопрос"
 */
public class ContactForm {

	/**
	 * Заголовок формы
	 */
	private String title;

	/**
	 * E-mail адрес отправителя
	 */
	private String fromEmail;

	/**
	 * тема письма
	 */
	private String emailSubject;

	/**
	 * Заголовок об успешно отправленном сообщении
	 */
	private String successTitle;

	/**
	 * Текст успешного завершения отправки сообщения
	 */
	private String successText;

	/**
	 * E-mail адреса разделенные через ';'
	 */
	private String toEmailList;

	/**
	 * Ключ API yandex для проверки Captcha. Подробнее
	 * http://api.yandex.ru/cleanweb/
	 */
	private String yandexKey;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isHasTitle() {
		return !org.apache.commons.lang.StringUtils.isBlank(title);
	}

	public String getToEmailList() {
		return toEmailList;
	}

	public void setToEmailList(String toEmailList) {
		this.toEmailList = toEmailList;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public boolean isHasFromEmail() {
		return !org.apache.commons.lang.StringUtils.isBlank(fromEmail);
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public boolean isHasEmailSubject() {
		return !org.apache.commons.lang.StringUtils.isBlank(emailSubject);
	}

	public String getSuccessTitle() {
		return successTitle;
	}

	public void setSuccessTitle(String successTitle) {
		this.successTitle = successTitle;
	}

	public boolean isHasSuccessTitle() {
		return !org.apache.commons.lang.StringUtils.isBlank(successTitle);
	}

	public String getSuccessText() {
		return successText;
	}

	public void setSuccessText(String successText) {
		this.successText = successText;
	}

	public boolean isHasSuccessText() {
		return !org.apache.commons.lang.StringUtils.isBlank(successText);
	}

	public String getYandexKey() {
		return yandexKey;
	}

	public void setYandexKey(String yandexKey) {
		this.yandexKey = yandexKey;
	}

}

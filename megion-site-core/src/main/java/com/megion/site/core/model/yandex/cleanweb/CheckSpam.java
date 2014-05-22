package com.megion.site.core.model.yandex.cleanweb;

/**
 * Данные для проверки введенных пользователем данных на спам с формы Captcha
 */
public class CheckSpam {

	private String name;
	private String subjectPlain;
	private String bodyPlain;
	
	public CheckSpam(String name, String subjectPlain, String bodyPlain) {
		this.name = name;
		this.subjectPlain = subjectPlain;
		this.bodyPlain = bodyPlain;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubjectPlain() {
		return subjectPlain;
	}

	public void setSubjectPlain(String subjectPlain) {
		this.subjectPlain = subjectPlain;
	}

	public String getBodyPlain() {
		return bodyPlain;
	}

	public void setBodyPlain(String bodyPlain) {
		this.bodyPlain = bodyPlain;
	}

}

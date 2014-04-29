package com.megion.site.core.model;

/**
 * 
 * Информация о чьем либо мнении
 * 
 */
public class Opinion {

	/**
	 * Кто составил мнение
	 */
	private final String opinionPerson;

	/**
	 * Текст мнения
	 */
	private final String opinionText;

	public Opinion(String opinionPerson, String opinionText) {
		this.opinionPerson = opinionPerson;
		this.opinionText = opinionText;
	}

	public String getOpinionPerson() {
		return opinionPerson;
	}

	public String getOpinionText() {
		return opinionText;
	}

}

package com.megion.site.core.model.pagination;

/**
 * Параметр запроса
 *
 */
public class UrlParam {

	private final String name;
	private final String value;

	public UrlParam(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "UrlParam [name=" + name + ", value=" + value + "]";
	}

}

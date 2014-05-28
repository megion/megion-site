package com.megion.site.core.model.yandex;

/**
 * Данные учетной записи yandex
 */
public class YandexAccount {

	/**
	 * Ключ API yandex для проверки Captcha. Подробнее
	 * http://api.yandex.ru/cleanweb/
	 */
	private String yandexKey;
	
	public String getYandexKey() {
		return yandexKey;
	}

	public void setYandexKey(String yandexKey) {
		this.yandexKey = yandexKey;
	}

}

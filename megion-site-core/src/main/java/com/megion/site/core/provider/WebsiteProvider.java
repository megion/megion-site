package com.megion.site.core.provider;

/**
 * Провайдер должен быть реализован в WAR проекте в единичном экземпляре.
 */
public interface WebsiteProvider {
	/**
	 * @return путь к головной странице сайта
	 */
	String getSiteRootPath();
	
}

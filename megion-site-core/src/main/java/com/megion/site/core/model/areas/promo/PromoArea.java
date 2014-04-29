package com.megion.site.core.model.areas.promo;

public class PromoArea {

	/**
	 * Запрет автоматической генерации блоков
	 */
	private Boolean disabelAutoGeneration;

	/**
	 * Разрешить ввод собственных блоков
	 */
	private Boolean enableCustomization;

	/**
	 * Скрыть статическую рекламу
	 */
	private Boolean hideStaticPromos;

	/**
	 * Относительный (относительно к расположению шаблона area) путь
	 * расположения статического содержимого. Пример:
	 * '../includes/staticPromos.jsp'
	 */
	private String relativeStaticPromosPath;

	public Boolean getDisabelAutoGeneration() {
		return disabelAutoGeneration;
	}

	public void setDisabelAutoGeneration(Boolean disabelAutoGeneration) {
		this.disabelAutoGeneration = disabelAutoGeneration;
	}

	public Boolean getEnableCustomization() {
		return enableCustomization;
	}

	public void setEnableCustomization(Boolean enableCustomization) {
		this.enableCustomization = enableCustomization;
	}

	public Boolean getHideStaticPromos() {
		return hideStaticPromos;
	}

	public void setHideStaticPromos(Boolean hideStaticPromos) {
		this.hideStaticPromos = hideStaticPromos;
	}

	public String getRelativeStaticPromosPath() {
		return relativeStaticPromosPath;
	}

	public void setRelativeStaticPromosPath(String relativeStaticPromosPath) {
		this.relativeStaticPromosPath = relativeStaticPromosPath;
	}

}

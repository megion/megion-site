package com.megion.site.core.model;

/**
 * Модель шаблона главной страницы
 */
public class MainPageTemplate {

	private final String title;
	private final String URL;

	private final Integer centerСontainerWidth;
	private final Integer centerСontainerDefaultWidth;

	private final Integer rightСontainerWidth;
	private final Integer rightСontainerDefaultWidth;

	public MainPageTemplate(String title, String URL,
			Integer centerСontainerWidth, Integer centerСontainerDefaultWidth,
			Integer rightСontainerWidth, Integer rightСontainerDefaultWidth) {
		this.title = title;
		this.URL = URL;
		this.centerСontainerWidth = centerСontainerWidth;
		this.centerСontainerDefaultWidth = centerСontainerDefaultWidth;
		this.rightСontainerWidth = rightСontainerWidth;
		this.rightСontainerDefaultWidth = rightСontainerDefaultWidth;
	}

	public String getTitle() {
		return title;
	}

	public Integer getCenterСontainerWidth() {
		return centerСontainerWidth;
	}

	public Integer getCenterСontainerDefaultWidth() {
		return centerСontainerDefaultWidth;
	}

	public Integer getRightСontainerWidth() {
		return rightСontainerWidth;
	}

	public Integer getRightСontainerDefaultWidth() {
		return rightСontainerDefaultWidth;
	}

	public String getCenterСontainerHtmlStyle() {
		return getWidthHtmlStyle(centerСontainerWidth,
				centerСontainerDefaultWidth);
	}

	public String getRightСontainerHtmlStyle() {
		return getWidthHtmlStyle(rightСontainerWidth,
				rightСontainerDefaultWidth);
	}

	public String getURL() {
		return URL;
	}

	private String getWidthHtmlStyle(Integer width, Integer defaultWidth) {
		if (width == null) {
			if (defaultWidth == null) {
				return "";
			}

			return "width: " + defaultWidth + "px;";

		}
		return "width: " + width + "px;";
	}

}

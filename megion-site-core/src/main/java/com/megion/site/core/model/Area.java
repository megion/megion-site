package com.megion.site.core.model;

/**
 * Общий класс для area
 */
public class Area {

	private final Integer width;
	private final Integer defaultWidth;

	public Area(Integer width, Integer defaultWidth) {
		this.width = width;
		this.defaultWidth = defaultWidth;
	}

	public Integer getWidth() {
		return width;
	}

	public String getHtmlStyle() {
		if (width == null) {
			if (defaultWidth==null) {
				return "";
			}
			
			return "width: " + defaultWidth + "px;";
			
		}
		return "width: " + width + "px;";
	}

}

package com.megion.site.core.model.yandex.map;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.megion.site.core.enums.TextPositionType;

/**
 * Модельный класс географической карты
 * 
 */
@XmlRootElement(name = "geomap")
@XmlAccessorType(XmlAccessType.FIELD)
public class GeoMap {

	/**
	 * Идентификатор карты в Yandex API
	 */
	@XmlElement(name = "id")
	private String id;

	/**
	 * Широта
	 */
	@XmlElement(name = "centerLat")
	private double centerLat;

	/**
	 * Долгота
	 */
	@XmlElement(name = "centerLon")
	private double centerLon;

	/**
	 * Увеличение
	 */
	@XmlElement(name = "zoom")
	private int zoom;

	/**
	 * Ширина карты
	 */
	@XmlElement(name = "width")
	private Integer width;

	/**
	 * Высота карты
	 */
	@XmlElement(name = "height")
	private Integer height;

	/**
	 * Метки на карте
	 */
	@XmlElement(name = "placemarks")
	private List<Placemark> placemarks;

	private String title;

	/**
	 * Текст карты
	 */
	private String mapText;

	private TextPositionType textPosition;

	private Integer textWidth;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getCenterLat() {

		return centerLat;
	}

	public void setCenterLat(double centerLat) {
		this.centerLat = centerLat;
	}

	public double getCenterLon() {
		return centerLon;
	}

	public void setCenterLon(double centerLon) {
		this.centerLon = centerLon;
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getMapHtmlStyle() {
		String htmlStyle = "";
		if (width != null) {
			htmlStyle = " width: " + width + "px;";
		}
		if (height != null) {
			htmlStyle = htmlStyle + " height: " + height + "px;";
		}

		return htmlStyle;
	}

	public String getTextHtmlStyle() {
		String htmlStyle = "";
		if (textWidth != null) {
			htmlStyle = " width: " + textWidth + "px;";
		}

		return htmlStyle;
	}

	public List<Placemark> getPlacemarks() {
		return placemarks;
	}

	public void setPlacemarks(List<Placemark> placemarks) {
		this.placemarks = placemarks;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isHasTitle() {
		return !org.apache.commons.lang.StringUtils.isBlank(title);
	}

	public String getMapText() {
		return mapText;
	}

	public void setMapText(String mapText) {
		this.mapText = mapText;
	}

	public boolean isHasMapText() {
		return !org.apache.commons.lang.StringUtils.isBlank(mapText);
	}

	public TextPositionType getTextPosition() {
		return textPosition;
	}

	public void setTextPosition(TextPositionType textPosition) {
		this.textPosition = textPosition;
	}

	public boolean isCanShowTextOnLeft() {
		return isHasMapText() && textPosition != null
				&& (TextPositionType.LEFT.equals(textPosition));
	}

	public boolean isCanShowTextOnRight() {
		return isHasMapText()
				&& (textPosition == null || (TextPositionType.RIGHT
						.equals(textPosition)));
	}

	public Integer getTextWidth() {
		return textWidth;
	}

	public void setTextWidth(Integer textWidth) {
		this.textWidth = textWidth;
	}

	@Override
	public String toString() {
		return "GeoMap [id=" + id + ", centerLat=" + centerLat + ", centerLon="
				+ centerLon + ", zoom=" + zoom + ", width=" + width
				+ ", height=" + height + ", placemarks=" + placemarks + "]";
	}

}

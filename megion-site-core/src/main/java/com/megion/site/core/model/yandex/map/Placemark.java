package com.megion.site.core.model.yandex.map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "placemark")
@XmlAccessorType(XmlAccessType.FIELD)
public class Placemark {

	/**
	 * Широта
	 */
	@XmlElement(name = "lat")
	private double lat;

	/**
	 * Долгота
	 */
	@XmlElement(name = "lon")
	private double lon;

	/**
	 * текст иконки
	 */
	@XmlElement(name = "hintContent")
	private String hintContent;

	/**
	 * текст балуна
	 */
	@XmlElement(name = "balloonContent")
	private String balloonContent;

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getHintContent() {
		return hintContent;
	}

	public void setHintContent(String hintContent) {
		this.hintContent = hintContent;
	}

	public String getBalloonContent() {
		return balloonContent;
	}

	public void setBalloonContent(String balloonContent) {
		this.balloonContent = balloonContent;
	}

	@Override
	public String toString() {
		return "Placemark [lat=" + lat + ", lon=" + lon + ", hintContent="
				+ hintContent + ", balloonContent=" + balloonContent + "]";
	}

}

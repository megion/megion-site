package com.megion.site.core.model;

import info.magnolia.module.templatingkit.dam.Asset;

/**
 * Реклама, акции и пр.
 */
public class Promo {

	private final String URL;
	private final Asset image;
	
	public Promo(String uRL, Asset image) {
		URL = uRL;
		this.image = image;
	}

	public String getURL() {
		return URL;
	}

	public Asset getImage() {
		return image;
	}

	@Override
	public String toString() {
		return "Promo [URL=" + URL + ", image=" + image + "]";
	}

}

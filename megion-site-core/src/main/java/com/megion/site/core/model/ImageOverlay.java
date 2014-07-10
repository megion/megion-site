package com.megion.site.core.model;

import info.magnolia.module.templatingkit.dam.Asset;

/**
 * Всплывающая картинка
 */
public class ImageOverlay {

	private Asset minImage;
	private Asset image;

	public Asset getMinImage() {
		return minImage;
	}

	public void setMinImage(Asset minImage) {
		this.minImage = minImage;
	}

	public Asset getImage() {
		return image;
	}

	public void setImage(Asset image) {
		this.image = image;
	}

}

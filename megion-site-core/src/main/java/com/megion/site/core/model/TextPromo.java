package com.megion.site.core.model;

/**
 * Текстовый баннер
 */
public class TextPromo {

	private final String URL;
	private final String externalURL;
	private final boolean isUseExternalURL;
	private final String text;

	public TextPromo(String uRL, String externalURL, boolean isUseExternalURL,
			String text) {
		URL = uRL;
		this.externalURL = externalURL;
		this.isUseExternalURL = isUseExternalURL;
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public String getURL() {
		return URL;
	}

	public String getExternalURL() {
		return externalURL;
	}

	public boolean isUseExternalURL() {
		return isUseExternalURL;
	}

}

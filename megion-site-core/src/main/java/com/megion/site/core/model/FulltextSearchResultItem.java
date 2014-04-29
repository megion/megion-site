package com.megion.site.core.model;

public class FulltextSearchResultItem {

	private final String title;
	private final String URL;

	private String text;

	public FulltextSearchResultItem(String title, String uRL) {
		this.title = title;
		URL = uRL;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return title;
	}

	public String getURL() {
		return URL;
	}

}

package com.megion.site.core.model.page;

import java.util.List;

/**
 * Общая модель страницы вебсайта
 */
public class WebsitePage {

	private final String title;
	private final String URL;
	
	private List<String> textTags;

	public WebsitePage(String title, String uRL) {
		this.title = title;
		URL = uRL;
	}

	public String getTitle() {
		return title;
	}

	public String getURL() {
		return URL;
	}
	
	/**
	 * Краткое содержание страницы
	 */
	public String getWebsitePageSummary() {
		return null;
	}
	
	public boolean isHasWebsitePageSummary() {
		return !org.apache.commons.lang.StringUtils.isBlank(getWebsitePageSummary());
	}
	
	public List<String> getTextTags() {
		return textTags;
	}

	public void setTextTags(List<String> textTags) {
		this.textTags = textTags;
	}

	public boolean isHasTextTags() {
		return textTags != null && !textTags.isEmpty();
	}

	@Override
	public String toString() {
		return "WebsitePage [title=" + title + ", URL=" + URL + "]";
	}

}

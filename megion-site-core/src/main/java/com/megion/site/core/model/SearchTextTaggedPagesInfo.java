package com.megion.site.core.model;

public class SearchTextTaggedPagesInfo {

	private final String title;
	private final String searchedTag;

	public SearchTextTaggedPagesInfo(String title, String searchedTag) {
		this.title = title;
		this.searchedTag = searchedTag;
	}

	public String getTitle() {
		return title;
	}

	public String getSearchedTag() {
		return searchedTag;
	}

	public boolean isHasTitle() {
		return !org.apache.commons.lang.StringUtils.isBlank(title);
	}

}

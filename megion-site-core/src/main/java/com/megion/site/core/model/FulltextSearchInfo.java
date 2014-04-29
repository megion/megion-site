package com.megion.site.core.model;

import org.springframework.web.util.HtmlUtils;

public class FulltextSearchInfo {

	private final String title;
	private final String searchPath;
	private String fulltext;
	
	private int resultCount;

	public FulltextSearchInfo(String title, String searchPath) {
		this.title = title;
		this.searchPath = searchPath;
	}

	public String getTitle() {
		return title;
	}

	public String getSearchPath() {
		return searchPath;
	}

	public boolean isHasTitle() {
		return !org.apache.commons.lang.StringUtils.isBlank(title);
	}

	public String getFulltext() {
		return fulltext;
	}
	
	public String getHtmlEscapedFulltext() {
		return HtmlUtils.htmlEscape(fulltext);
	}

	public void setFulltext(String fulltext) {
		this.fulltext = fulltext;
	}

	public int getResultCount() {
		return resultCount;
	}

	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}

}

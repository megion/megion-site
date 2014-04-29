package com.megion.site.core.model;

import javax.jcr.Node;

/**
 * 
 * Вкладка
 * 
 */
public class TabItem {

	private final String URL;
	private boolean isSelected;
	private final String title;

	/**
	 * Узел представляющий собой страницу на которую ссылается вкладка
	 */
	private final Node tabPage;

	public TabItem(String url, String title,
			boolean isSelected, Node tabPage) {
		URL = url;
		this.title = title;
		this.isSelected = isSelected;
		this.tabPage = tabPage;
	}

	public String getURL() {
		return URL;
	}

	public String getTitle() {
		return title;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public Node getTabPage() {
		return tabPage;
	}

	@Override
	public String toString() {
		return "TabItem [URL=" + URL + ", isSelected=" + isSelected + ", title="
				+ title + ", tabPage=" + tabPage + "]";
	}

}

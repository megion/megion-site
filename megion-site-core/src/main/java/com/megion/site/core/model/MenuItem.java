package com.megion.site.core.model;

import java.util.List;

import javax.jcr.Node;

/**
 * 
 * Пункт меню
 *
 */
public class MenuItem {

	private final List<MenuItem> children;

	private final String URL;
	private boolean isSelected;
	private final String name;
    private boolean isNotLink;
    private String replacementURL;

	/**
	 * Узел представляющий собой страницу на которую ссылается меню
	 */
	private final Node menuPage;
	
	public MenuItem(String url, String name, List<MenuItem> children,
			boolean isSelected, Node menuPage, boolean isNotLink) {
		URL = url;
		this.name = name;
		this.children = children;
		this.isSelected = isSelected;
		this.menuPage = menuPage;
        this.isNotLink = isNotLink;
		// если не выбран текущий элемент тогда проверить есть ли среди детей
		// выбранный
		if (!this.isSelected) {
			if (this.isHasChildren()) {
				this.isSelected = hasSelectedChild(children);
			}
		}

	}

	private boolean hasSelectedChild(List<MenuItem> children) {
		for (MenuItem child : children) {
			if (child.isSelected()) {
				return true;
			}

			if (child.isHasChildren()) {
				if(hasSelectedChild(child.getChildren())) {
					return true;
				}
			}
		}
		return false;
	}

	public List<MenuItem> getChildren() {
		return children;
	}

	public String getURL() {
		return URL;
	}

	public String getName() {
		return name;
	}

	public boolean isHasChildren() {
		return children != null && children.size() > 0;
	}

	public boolean isSelected() {
		return isSelected;
	}

    public boolean isNotLink() {
        return isNotLink;
    }

    public Node getMenuPage() {
		return menuPage;
	}
    
    public String getReplacementURL() {
		return replacementURL;
	}

	public void setReplacementURL(String replacementURL) {
		this.replacementURL = replacementURL;
	}

	@Override
	public String toString() {
		return "MainItem [children=" + children + ", URL=" + URL
				+ ", isSelected=" + isSelected + ", name=" + name + ", notLink=" + isNotLink + "]";
	}

}

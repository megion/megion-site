package com.megion.site.core.model;

import javax.jcr.Node;

public class HtmlBlock {

	private final String htmlStyle;
	private final Node component;

	public HtmlBlock(String htmlStyle, Node component) {
		this.htmlStyle = htmlStyle;
		this.component = component;
	}

	public String getHtmlStyle() {
		return htmlStyle;
	}

	public Node getComponent() {
		return component;
	}

	@Override
	public String toString() {
		return "HtmlBlock [htmlStyle=" + htmlStyle + ", component=" + component
				+ "]";
	}

}

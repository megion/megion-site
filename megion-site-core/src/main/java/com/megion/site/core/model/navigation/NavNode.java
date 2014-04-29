package com.megion.site.core.model.navigation;

import javax.jcr.Node;

public class NavNode {

	private final String URL;
	private final String title;
	private final Node node;
    private boolean isNotLink;

	public NavNode(String uRL, String title, Node node) {
		URL = uRL;
		this.title = title;
		this.node = node;
	}

    public NavNode(String uRL, String title, Node node, boolean isNotLink) {
        URL = uRL;
        this.title = title;
        this.node = node;
        this.isNotLink = isNotLink;
    }

	public String getURL() {
		return URL;
	}

	public String getTitle() {
		return title;
	}

	public Node getNode() {
		return node;
	}

    public boolean isNotLink() {
        return isNotLink;
    }

    public void setNotLink(boolean notLink) {
        isNotLink = notLink;
    }
}

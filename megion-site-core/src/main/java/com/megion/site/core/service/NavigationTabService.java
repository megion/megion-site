package com.megion.site.core.service;

import info.magnolia.module.blossom.dialog.TabBuilder;

import java.util.List;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import com.megion.site.core.model.TabItem;

public interface NavigationTabService {

	List<TabItem> getNavigationTabs(Node parrentNode, Node currentNode)
			throws RepositoryException;
	
	List<TabItem> getSubPagesAsTabs(Node subPagesAsTabsComponent)
			throws RepositoryException;
	
	void addSubPagesAsTabsDialogControls(TabBuilder tabBuilder);

}

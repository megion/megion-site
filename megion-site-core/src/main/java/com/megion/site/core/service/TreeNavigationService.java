package com.megion.site.core.service;

import info.magnolia.module.blossom.dialog.TabBuilder;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import com.megion.site.core.model.navigation.TreeNavigation;

public interface TreeNavigationService {

	TreeNavigation getTreeNavigation(Node treeNavigationComponent)
			throws RepositoryException;
	
	void addTreeNavigationDialogControls(TabBuilder tabBuilder);

}

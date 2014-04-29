package com.megion.site.core.service;

import java.util.List;

import info.magnolia.module.blossom.dialog.TabBuilder;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;

import com.megion.site.core.model.MenuItem;
import com.megion.site.core.model.navigation.BreadcrumbsNavigation;
import com.megion.site.core.model.page.PageModel;
import com.megion.site.core.model.page.WebsitePage;

public interface PageService {

	void addMainPageDialogControls(TabBuilder tabBuilder) throws RepositoryException;
	
	void addPageMetadataDialogControls(TabBuilder tabBuilder) throws RepositoryException;
	
	WebsitePage createGenericWebsitePage(Node page) throws RepositoryException;
	
	BreadcrumbsNavigation getBreadcrumbsNavigation(Node currentPage) throws RepositoryException;
	
	PageModel getPageModel(Node page);
	
	/**
	 * Получить пункты верхнего меню
	 */
	List<MenuItem> getRootMenus(Node currentNode)
			throws PathNotFoundException, RepositoryException;
	
	/**
	 * Получить пункты меню, представленные под страницами.
	 */
	List<MenuItem> getSecondLevelMenus(Node currentNode)
			throws PathNotFoundException, RepositoryException;

}

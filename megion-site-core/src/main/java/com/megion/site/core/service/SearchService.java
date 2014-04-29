package com.megion.site.core.service;

import info.magnolia.module.blossom.dialog.TabBuilder;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.ServletRequestBindingException;

import com.megion.site.core.model.FulltextSearchInfo;
import com.megion.site.core.model.FulltextSearchResultItem;
import com.megion.site.core.model.page.WebsitePage;
import com.megion.site.core.model.pagination.PagedList;
import com.megion.site.core.model.pagination.PaginationResult;
import com.megion.site.core.model.pagination.Step;

public interface SearchService {

	/**
	 * Поиск всех страниц, с указанным multival свойством
	 */
	PagedList<WebsitePage> searchWebsitePages(String propName, String propVal,
			String searchPath, Step step) throws RepositoryException;

	/**
	 * Полноценный поиск по узлам страниц
	 */
	PaginationResult<FulltextSearchResultItem> getSearchResults(
			HttpServletRequest request, Node componentNode,
			FulltextSearchInfo searchInfo) throws RepositoryException,
			ServletRequestBindingException;

	void addFulltextSearchDialogControls(TabBuilder tabBuilder);

	FulltextSearchInfo getFulltextSearchInfo(HttpServletRequest request,
			Node componentNode) throws RepositoryException,
			ServletRequestBindingException;

}

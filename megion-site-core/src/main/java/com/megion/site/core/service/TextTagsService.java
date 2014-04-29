package com.megion.site.core.service;

import info.magnolia.module.blossom.dialog.TabBuilder;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.ServletRequestBindingException;

import com.megion.site.core.model.SearchTextTaggedPagesInfo;
import com.megion.site.core.model.page.WebsitePage;
import com.megion.site.core.model.pagination.PaginationResult;

public interface TextTagsService {

	void addTextTagsCloudDialogControls(TabBuilder tabBuilder);

	void addSearchTextTaggedPagesDialogControls(TabBuilder tabBuilder);

	PaginationResult<WebsitePage> getWebsitePagesByTag(
			HttpServletRequest request, Node componentNode)
			throws RepositoryException, ServletRequestBindingException;

	SearchTextTaggedPagesInfo getSearchTextTaggedPagesInfo(
			HttpServletRequest request, Node componentNode)
			throws RepositoryException, ServletRequestBindingException;

}

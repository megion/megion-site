package com.megion.site.core.service;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import com.megion.site.core.model.page.WebsitePage;
import com.megion.site.core.service.identify.Identifiable;

public interface WebsitePageService extends Identifiable {
	
	public static final String WEBSITE_PAGE_TYPE_PROPERTY = "websitePageType";

	WebsitePage getWebsitePage(Node websitePage) throws RepositoryException;

}

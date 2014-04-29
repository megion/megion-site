package com.megion.site.core.service;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import com.megion.site.core.model.page.WebsitePage;
import com.megion.site.core.service.identify.Identifiable;

public interface WebsiteService extends Identifiable {

	WebsitePage getWebsitePage(Node websitePage) throws RepositoryException;

}

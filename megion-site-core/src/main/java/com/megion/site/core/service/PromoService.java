package com.megion.site.core.service;

import info.magnolia.module.blossom.dialog.TabBuilder;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;

import com.megion.site.core.model.Promo;
import com.megion.site.core.model.TextPromo;


public interface PromoService {

	Promo getPromo(Node promoComponent) throws PathNotFoundException, RepositoryException;
	
	TextPromo getTextPromo(Node promoComponent) throws RepositoryException;
	
	void addPromoDialogControls(TabBuilder tabBuilder);
	
	void addTextPromoDialogControls(TabBuilder tabBuilder);
	
	void generatePromoComponents(Node areaNode) throws RepositoryException;
	
	void generateTextPromoComponents(Node areaNode) throws RepositoryException;
	
}

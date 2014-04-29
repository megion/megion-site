package com.megion.site.core.service.area;

import info.magnolia.module.blossom.dialog.TabBuilder;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import com.megion.site.core.model.areas.promo.PromoArea;

public interface PromoAreaService {

	void addPromoAreaDialogControls(TabBuilder tabBuilder);

	PromoArea getPromoArea(Node area) throws RepositoryException;

	void generatePromoAreaComponents(Node areaNode) throws RepositoryException;

	void generateTextPromoAreaComponents(Node areaNode)
			throws RepositoryException;

}

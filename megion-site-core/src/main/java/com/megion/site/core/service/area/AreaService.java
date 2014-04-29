package com.megion.site.core.service.area;

import info.magnolia.module.blossom.dialog.TabBuilder;

import java.util.List;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;

import com.megion.site.core.model.HtmlBlock;

public interface AreaService {

	List<HtmlBlock> getHorizontalBlocks(Node areaNode)
			throws PathNotFoundException, RepositoryException;

	void addHorizontalAreaDialogControls(TabBuilder tabBuilder) throws RepositoryException;

}

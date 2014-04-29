package com.megion.site.core.service.area;

import info.magnolia.module.blossom.dialog.TabBuilder;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.xml.bind.JAXBException;

import com.megion.site.core.model.areas.column.ColumnArea;

public interface ColumnAreaService {

	ColumnArea getColumnArea(Node areaNode) throws RepositoryException, JAXBException;

	void addColumnAreaDialogControls(TabBuilder tabBuilder) throws RepositoryException;

}

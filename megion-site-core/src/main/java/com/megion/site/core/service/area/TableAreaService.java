package com.megion.site.core.service.area;

import info.magnolia.module.blossom.dialog.TabBuilder;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.xml.bind.JAXBException;

import com.megion.site.core.model.areas.table.TableArea;

public interface TableAreaService {

	TableArea getTableArea(Node areaNode) throws RepositoryException, JAXBException;

	void addTableAreaDialogControls(TabBuilder tabBuilder) throws RepositoryException;

}

package com.megion.site.core.service;

import info.magnolia.module.blossom.dialog.TabBuilder;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.xml.bind.JAXBException;

import com.megion.site.core.enums.TabNumber;
import com.megion.site.core.model.yandex.map.GeoMap;

public interface GeoMapService {

	void addGeoMapDialogControls(TabBuilder tabBuilder, TabNumber tabNumber) throws RepositoryException;

	GeoMap getGeoMap(Node geoMapComponent) throws RepositoryException, JAXBException;
	
	String createGeoMapJson(GeoMap geoMap) throws JAXBException;

}

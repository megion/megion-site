package com.megion.site.core.web.component;

import info.magnolia.module.blossom.annotation.TabFactory;
import info.magnolia.module.blossom.annotation.Template;
import info.magnolia.module.blossom.annotation.TemplateDescription;
import info.magnolia.module.blossom.dialog.TabBuilder;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;

import com.megion.site.core.enums.TabNumber;
import com.megion.site.core.model.yandex.map.GeoMap;
import com.megion.site.core.service.GeoMapService;

/**
 * Компонент отображения географической карты
 */
@Controller
@Template(title = "Geo map", id = "megion-site:components/geoMap")
@TemplateDescription("Географическая карта")
public class GeoMapComponent {

	@Autowired
	private GeoMapService geoMapService;

	@RequestMapping("/geoMap")
	public String handleRequest(ModelMap model, HttpSession session,
			HttpServletRequest request, Node node)
			throws PathNotFoundException, RepositoryException,
			ServletRequestBindingException, JAXBException {

		GeoMap geoMap = geoMapService.getGeoMap(node);
		model.put("geoMap", geoMap);
		
		String geoMapJson = geoMapService.createGeoMapJson(geoMap);
		model.put("geoMapJson", geoMapJson);

		return "components/geoMap.jsp";
	}

	@TabFactory("Content")
	public void contentTab(TabBuilder tab) throws RepositoryException {
		geoMapService.addGeoMapDialogControls(tab, TabNumber.FIRST);
	}
	
	@TabFactory("Placemarks")
	public void placemarksTab(TabBuilder tab) throws RepositoryException {
		geoMapService.addGeoMapDialogControls(tab, TabNumber.SECOND);
	}
	
	@TabFactory("Text")
	public void textTab(TabBuilder tab) throws RepositoryException {
		geoMapService.addGeoMapDialogControls(tab, TabNumber.THIRD);
	}

}

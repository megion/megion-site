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

import com.megion.site.core.model.ImageOverlay;
import com.megion.site.core.service.ImageOverlayService;

/**
 * Компонент ImageOverlay
 */
@Controller
@Template(title = "Image overlay", id = "megion-site:components/imageOverlay")
@TemplateDescription("Image overlay")
public class ImageOverlayComponent {
	
	@Autowired
	private ImageOverlayService imageOverlayService;

	@RequestMapping("/imageOverlay")
	public String handleRequest(ModelMap model, HttpSession session,
			HttpServletRequest request, Node node)
			throws PathNotFoundException, RepositoryException,
			ServletRequestBindingException, JAXBException {
		ImageOverlay imgOverlay = imageOverlayService.getImageOverlay(node);
		model.put("imgOverlay", imgOverlay);
		
		return "components/imageOverlay.jsp";
	}

	@TabFactory("Content")
	public void contentTab(TabBuilder tab) throws RepositoryException {
		imageOverlayService.addImageOverlayDialogControls(tab);
	}

}

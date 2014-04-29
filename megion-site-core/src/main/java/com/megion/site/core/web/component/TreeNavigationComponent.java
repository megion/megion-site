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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;

import com.megion.site.core.model.navigation.TreeNavigation;
import com.megion.site.core.service.TreeNavigationService;

/**
 * Компонент отображающий навигацию по дочерним страницам 
 */
@Controller
@Template(title = "Tree page navigation", id = "megion-site:components/treeNavigation")
@TemplateDescription("Навигация по страницам")
public class TreeNavigationComponent {

	@Autowired
	private TreeNavigationService treeNavigationService;

	@RequestMapping("/treeNavigation")
	public String handleRequest(ModelMap model, HttpSession session,
			HttpServletRequest request, Node node)
			throws PathNotFoundException, RepositoryException,
			ServletRequestBindingException {
		
		TreeNavigation treeNavigation = treeNavigationService.getTreeNavigation(node);
		model.put("treeNavigation", treeNavigation);
		
		return "components/treeNavigation.jsp";
	}

	@TabFactory("Content")
	public void contentTab(TabBuilder tab) {
		treeNavigationService.addTreeNavigationDialogControls(tab);
	}
}

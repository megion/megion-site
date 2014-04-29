package com.megion.site.core.web.component;

import info.magnolia.module.blossom.annotation.TabFactory;
import info.magnolia.module.blossom.annotation.Template;
import info.magnolia.module.blossom.annotation.TemplateDescription;
import info.magnolia.module.blossom.dialog.TabBuilder;

import java.util.List;

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

import com.megion.site.core.model.TabItem;
import com.megion.site.core.service.NavigationTabService;

/**
 * Компонент отображающий дочерние страницы как закладки 
 */
@Controller
@Template(title = "Sub pages as tabs", id = "megion-site:components/subPagesAsTabs")
@TemplateDescription("Закладки")
public class SubPagesAsTabsComponent {

	@Autowired
	private NavigationTabService navigationTabService;

	@RequestMapping("/subPagesAsTabs")
	public String handleRequest(ModelMap model, HttpSession session,
			HttpServletRequest request, Node node)
			throws PathNotFoundException, RepositoryException,
			ServletRequestBindingException {
		
		List<TabItem> items = navigationTabService.getSubPagesAsTabs(node);
		model.put("items", items);
		
		return "components/tabs.jsp";
	}

	@TabFactory("Content")
	public void contentTab(TabBuilder tab) {
		navigationTabService.addSubPagesAsTabsDialogControls(tab);
	}
}

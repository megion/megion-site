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

import com.megion.site.core.model.SearchTextTaggedPagesInfo;
import com.megion.site.core.model.page.WebsitePage;
import com.megion.site.core.model.pagination.PaginationResult;
import com.megion.site.core.service.PaginationService;
import com.megion.site.core.service.TextTagsService;

/**
 * Компонент отображающий список страниц отфильтрованных по тегу
 */
@Controller
@Template(title = "Tagged website pages", id = "megion-site:components/searchTextTaggedPages")
@TemplateDescription("Список страниц соответствующих текстовому тегу")
public class SearchTextTaggedPagesComponent {

	@Autowired
	private TextTagsService textTagsService;
	
	@Autowired
	private PaginationService paginationService;

	@RequestMapping("/searchTextTaggedPages")
	public String handleRequest(ModelMap model, HttpSession session,
			HttpServletRequest request, Node node)
			throws PathNotFoundException, RepositoryException,
			ServletRequestBindingException {
		
		SearchTextTaggedPagesInfo info = textTagsService.getSearchTextTaggedPagesInfo(request, node);
		model.put("info", info);
		
		PaginationResult<WebsitePage> result = textTagsService.getWebsitePagesByTag(request, node);
		if (result.getPagination()!=null) {
			model.put("pagination", result.getPagination());
		}
		model.put("items", result.getResult());
		
		return "components/searchTextTaggedPages.jsp";
	}

	@TabFactory("Content")
	public void contentTab(TabBuilder tab) throws RepositoryException {
		textTagsService.addSearchTextTaggedPagesDialogControls(tab);
	}
	
	@TabFactory("Paging")
	public void pagingTab(TabBuilder tab) {
		paginationService.addPagingDialogControls(tab);
	}
}

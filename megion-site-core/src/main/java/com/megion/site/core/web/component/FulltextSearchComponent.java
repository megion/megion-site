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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;

import com.megion.site.core.model.FulltextSearchInfo;
import com.megion.site.core.model.FulltextSearchResultItem;
import com.megion.site.core.model.pagination.PaginationResult;
import com.megion.site.core.service.PaginationService;
import com.megion.site.core.service.SearchService;

/**
 * Компонент отображающий список страниц отфильтрованных по тегу
 */
@Controller
@Template(title = "Full-text search", id = "megion-site:components/fulltextSearch")
@TemplateDescription("Полноценный поиск по сайту")
public class FulltextSearchComponent {

	@Autowired
	private SearchService searchService;

	@Autowired
	private PaginationService paginationService;

	@RequestMapping("/fulltextSearch")
	public String handleRequest(ModelMap model, HttpSession session,
			HttpServletRequest request, Node node)
			throws PathNotFoundException, RepositoryException,
			ServletRequestBindingException {

		FulltextSearchInfo info = searchService.getFulltextSearchInfo(request,
				node);

		if (StringUtils.isNotBlank(info.getFulltext())) {
			PaginationResult<FulltextSearchResultItem> result = searchService
					.getSearchResults(request, node, info);
			if (result.getPagination() != null) {
				model.put("pagination", result.getPagination());
			}
			model.put("items", result.getResult());
			info.setResultCount(result.getTotal());
		}
		model.put("info", info);

		return "components/fulltextSearch.jsp";
	}

	@TabFactory("Content")
	public void contentTab(TabBuilder tab) throws RepositoryException {
		searchService.addFulltextSearchDialogControls(tab);
	}

	@TabFactory("Paging")
	public void pagingTab(TabBuilder tab) {
		paginationService.addPagingDialogControls(tab);
	}
}

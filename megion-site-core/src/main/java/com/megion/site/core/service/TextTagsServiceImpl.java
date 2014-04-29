package com.megion.site.core.service;

import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.module.blossom.dialog.TabBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

import com.megion.site.core.model.SearchTextTaggedPagesInfo;
import com.megion.site.core.model.page.WebsitePage;
import com.megion.site.core.model.pagination.PagedList;
import com.megion.site.core.model.pagination.PaginationResult;
import com.megion.site.core.model.pagination.Step;
import com.megion.site.core.model.pagination.UrlParam;
import com.megion.site.core.provider.WebsiteProvider;

@Service
public class TextTagsServiceImpl implements TextTagsService {

	@Autowired
	private SearchService searchService;

	@Autowired
	private PaginationService paginationService;

	@Autowired
	private TemplatingService templatingService;
	
	@Autowired
	private WebsiteProvider websiteProvider;

	@Override
	public void addTextTagsCloudDialogControls(TabBuilder tabBuilder) {
		tabBuilder.addEdit("title", "Заголовок", "");
		tabBuilder.addUuidLink("searchLink", "Страница поиска по тегу",
				"Ссылка на страницу с результатами поиска по тегу")
				.setRequired(true);

		tabBuilder
				.addUuidLink("rootTaggedPageLink",
						"Верхне-уровневая страница поиска тегов",
						"Ссылка на страницу верхнего уровня под которой необходимо искать теги")
				.setRequired(true);
	}

	@Override
	public PaginationResult<WebsitePage> getWebsitePagesByTag(
			HttpServletRequest request, Node componentNode)
			throws RepositoryException, ServletRequestBindingException {

		final String tagForSearch = ServletRequestUtils.getStringParameter(
				request, "searchtag");
		final List<UrlParam> additionalParams = new ArrayList<UrlParam>();
		additionalParams.add(new UrlParam("searchtag", tagForSearch));

		PaginationResult<WebsitePage> result = paginationService
				.createPaginationResult(request, componentNode,
						additionalParams, new PagedListCreator<WebsitePage>() {
							@Override
							public PagedList<WebsitePage> create(
									Step currentStep)
									throws RepositoryException {
								return searchService.searchWebsitePages("tags",
										tagForSearch,
										websiteProvider.getSiteRootPath(),
										currentStep);
							}
						});

		return result;
	}

	@Override
	public SearchTextTaggedPagesInfo getSearchTextTaggedPagesInfo(
			HttpServletRequest request, Node componentNode)
			throws RepositoryException, ServletRequestBindingException {
		String title = PropertyUtil.getString(componentNode, "title");
		String searchedTag = ServletRequestUtils.getStringParameter(request,
				"searchtag");

		return new SearchTextTaggedPagesInfo(title, searchedTag);
	}

	@Override
	public void addSearchTextTaggedPagesDialogControls(TabBuilder tabBuilder) {
		tabBuilder.addEdit("title", "Заголовок", "");
	}

}
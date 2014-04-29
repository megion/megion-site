package com.megion.site.core.service;

import info.magnolia.cms.core.MgnlNodeType;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.jcr.wrapper.I18nNodeWrapper;
import info.magnolia.module.blossom.dialog.TabBuilder;
import info.magnolia.module.templatingkit.search.SearchResultItem;
import info.magnolia.objectfactory.Components;
import info.magnolia.repository.RepositoryConstants;
import info.magnolia.templating.functions.TemplatingFunctions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletRequest;

import net.sourceforge.openutils.mgnlcriteria.jcr.query.AdvancedResult;
import net.sourceforge.openutils.mgnlcriteria.jcr.query.AdvancedResultItem;
import net.sourceforge.openutils.mgnlcriteria.jcr.query.Criteria;
import net.sourceforge.openutils.mgnlcriteria.jcr.query.JCRCriteriaFactory;
import net.sourceforge.openutils.mgnlcriteria.jcr.query.ResultIterator;
import net.sourceforge.openutils.mgnlcriteria.jcr.query.criterion.Criterion;
import net.sourceforge.openutils.mgnlcriteria.jcr.query.criterion.Disjunction;
import net.sourceforge.openutils.mgnlcriteria.jcr.query.criterion.Order;
import net.sourceforge.openutils.mgnlcriteria.jcr.query.criterion.Restrictions;
import net.sourceforge.openutils.mgnlcriteria.jcr.query.xpath.utils.XPathTextUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.util.HtmlUtils;

import com.megion.site.core.model.FulltextSearchInfo;
import com.megion.site.core.model.FulltextSearchResultItem;
import com.megion.site.core.model.page.WebsitePage;
import com.megion.site.core.model.pagination.PagedList;
import com.megion.site.core.model.pagination.PaginationResult;
import com.megion.site.core.model.pagination.Step;
import com.megion.site.core.model.pagination.UrlParam;
import com.megion.site.core.service.identify.BeansCollector;
import com.megion.site.core.util.JcrNodeUtils;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private PageService pageService;

	@Autowired
	private TemplatingService templatingService;

	@Autowired
	private PaginationService paginationService;
	
	//private 

	@Override
	public PagedList<WebsitePage> searchWebsitePages(String propName,
			String propVal, String searchPath, Step step)
			throws RepositoryException {
		String basePath = searchPath == null ? "/" : searchPath;
		Criteria criteria = JCRCriteriaFactory
				.createCriteria()
				.setWorkspace(RepositoryConstants.WEBSITE)
				.setBasePath(basePath)
				.add(Restrictions.eq(Criterion.JCR_PRIMARYTYPE,
						MgnlNodeType.NT_PAGE)).addOrder(Order.asc("@title"));

		if (propVal != null) {
			criteria.add(Restrictions.like("@" + propName, propVal));
		}

		if (step != null) {
			criteria.setFirstResult(step.getOffset());
			criteria.setMaxResults(step.getMax());
		}

		AdvancedResult result = criteria.execute();
		ResultIterator<AdvancedResultItem> items = result.getItems();
		Iterator<AdvancedResultItem> iterator = items.iterator();
		List<WebsitePage> pages = new ArrayList<WebsitePage>();
		
		BeansCollector<WebsitePageService> beansCollector = new BeansCollector<WebsitePageService>(applicationContext, WebsitePageService.class); 
		
		while (iterator.hasNext()) {
			AdvancedResultItem item = iterator.next();
			Node itemNode = item.getJCRNode();

			WebsitePage page = null;
			if (itemNode.hasProperty(WebsitePageService.WEBSITE_PAGE_TYPE_PROPERTY)) {
				String pageTypeId = PropertyUtil.getString(itemNode,
						WebsitePageService.WEBSITE_PAGE_TYPE_PROPERTY);
				
				WebsitePageService websitePageService = beansCollector.findIdentifiable(pageTypeId);

				if (websitePageService != null) {
					websitePageService.getWebsitePage(itemNode);
				} else {
					page = pageService.createGenericWebsitePage(itemNode);
				}
			} else {
				page = pageService.createGenericWebsitePage(itemNode);
			}

			pages.add(page);
		}
		return new PagedList<WebsitePage>(result.getTotalSize(), pages);
	}

	private PagedList<FulltextSearchResultItem> fulltextSearch(
			String searchPath, String fulltext, Step step)
			throws RepositoryException {
		Criteria criteria = JCRCriteriaFactory
				.createCriteria()
				.setWorkspace(RepositoryConstants.WEBSITE)
				.setBasePath(searchPath)
				.add(Restrictions.eq(Criterion.JCR_PRIMARYTYPE,
						MgnlNodeType.NT_PAGE))
				.add(Restrictions.ne(Criterion.JCR_PRIMARYTYPE,
						MgnlNodeType.MIX_DELETED))
				.addOrder(Order.asc("@title"));

		String escapedFulltext = XPathTextUtils.stringToJCRSearchExp(fulltext);
		String searchtextToHtml = HtmlUtils.htmlEscape(fulltext);

		Disjunction disjunction = Restrictions.disjunction();
		//disjunction.add(Restrictions.contains("*", escapedFulltext));
		//disjunction.add(Restrictions.contains(".", escapedFulltext));
		//disjunction.add(Restrictions.contains("*/.", escapedFulltext)); // area
		//disjunction.add(Restrictions.contains("*/*/.", escapedFulltext)); // area/component[n]
		
		//disjunction.add(Restrictions.contains("*", escapedFulltext));
		
		disjunction.add(Restrictions.contains(".", escapedFulltext)); // current page
		disjunction.add(Restrictions.contains("./*", escapedFulltext)); // area
		disjunction.add(Restrictions.contains("./*/*", escapedFulltext)); // area/component[n]
		
		criteria.add(disjunction);

		// не искать в акция и баннерах
		//criteria.add(Restrictions.not(Restrictions.eq("fn:name()",
				//"rightCenterPromos")));
		//criteria.add(Restrictions.not(Restrictions.eq("fn:name()",
				//"rightBottomPromos")));

		criteria.add(Restrictions.or(
				Restrictions.isNull("@excludeInFulltextSearch"),
				Restrictions.eq("@excludeInFulltextSearch", "false")));

		if (step != null) {
			criteria.setFirstResult(step.getOffset());
			criteria.setMaxResults(step.getMax());
		}

		AdvancedResult result = criteria.execute();
		ResultIterator<AdvancedResultItem> items = result.getItems();
		Iterator<AdvancedResultItem> iterator = items.iterator();
		List<FulltextSearchResultItem> objResults = new ArrayList<FulltextSearchResultItem>();

		TemplatingFunctions templatingFunctions = Components
				.getComponent(TemplatingFunctions.class);
		
		while (iterator.hasNext()) {
			AdvancedResultItem item = iterator.next();
			Node itemNode = new I18nNodeWrapper(item.getJCRNode());

			// ищем в дочерних компонентах
			AdvancedResult subAreasOrComponents = fullsearchTextInSubAreasOrComponents(
					itemNode, escapedFulltext);
			ResultIterator<AdvancedResultItem> subAreasOrComponentItems = subAreasOrComponents
					.getItems();
			Iterator<AdvancedResultItem> subAreasOrComponentsIterator = subAreasOrComponentItems
					.iterator();

			StringBuffer snippets = new StringBuffer();
			SearchResultItem searchResult = new SearchResultItem(itemNode,
					searchtextToHtml, templatingFunctions);
			String pageSnippet = searchResult.getText();
			if (StringUtils.isNotBlank(pageSnippet)) {
				snippets.append(pageSnippet);
			}
			
			
			while (subAreasOrComponentsIterator.hasNext()) {
				AdvancedResultItem subItem = subAreasOrComponentsIterator
						.next();
				Node subItemNode = new I18nNodeWrapper(subItem.getJCRNode());
				SearchResultItem subSearchResult = new SearchResultItem(
						subItemNode, searchtextToHtml, templatingFunctions);
				String subText = subSearchResult.getText();
				if (StringUtils.isNotBlank(subText) && !(snippets.toString().indexOf(subText) > -1)) { //
					snippets.append(subText);
				}
			}
			
			FulltextSearchResultItem resultItem = new FulltextSearchResultItem(
					PropertyUtil.getString(itemNode, "title"),
					JcrNodeUtils.getURL(itemNode));
			resultItem.setText(snippets.toString());
			objResults.add(resultItem);
		}

		return new PagedList<FulltextSearchResultItem>(result.getTotalSize(),
				objResults);
	}

	private AdvancedResult fullsearchTextInSubAreasOrComponents(
			Node parentPage, String fulltext) throws RepositoryException {
		Criteria criteria = JCRCriteriaFactory
				.createCriteria()
				.setWorkspace(RepositoryConstants.WEBSITE)
				.setBasePath(parentPage.getPath())
				.add(Restrictions.ne(Criterion.JCR_PRIMARYTYPE,
						MgnlNodeType.MIX_DELETED));

		criteria.add(Restrictions.or(Restrictions.eq(Criterion.JCR_PRIMARYTYPE,
				MgnlNodeType.NT_AREA), Restrictions.eq(
				Criterion.JCR_PRIMARYTYPE, MgnlNodeType.NT_COMPONENT)));

		// поиск по тексту
		criteria.add(Restrictions.contains(".", fulltext));
		AdvancedResult result = criteria.execute();
		return result;
	}

	@Override
	public void addFulltextSearchDialogControls(TabBuilder tabBuilder) {
		tabBuilder.addEdit("title", "Заголовок", "");
		tabBuilder.addUuidLink("rootSearchPage",
				"Страница под которой производится поиск", "")
				.setRequired(true);
	}

	@Override
	public FulltextSearchInfo getFulltextSearchInfo(HttpServletRequest request,
			Node componentNode) throws RepositoryException,
			ServletRequestBindingException {
		String title = PropertyUtil.getString(componentNode, "title");
		String fulltext = ServletRequestUtils.getStringParameter(request,
				"query");
		Node rootSearchNode = templatingService
				.getNodePropertyAsWebsiteUuidLink(componentNode,
						"rootSearchPage");

		FulltextSearchInfo info = new FulltextSearchInfo(title,
				rootSearchNode.getPath());
		info.setFulltext(fulltext);
		return info;
	}

	@Override
	public PaginationResult<FulltextSearchResultItem> getSearchResults(
			HttpServletRequest request, Node componentNode,
			final FulltextSearchInfo searchInfo) throws RepositoryException,
			ServletRequestBindingException {
		final String fulltext = searchInfo.getFulltext();
		final List<UrlParam> additionalParams = new ArrayList<UrlParam>();
		additionalParams.add(new UrlParam("query", fulltext));

		PaginationResult<FulltextSearchResultItem> result = paginationService
				.createPaginationResult(request, componentNode,
						additionalParams,
						new PagedListCreator<FulltextSearchResultItem>() {
							@Override
							public PagedList<FulltextSearchResultItem> create(
									Step currentStep)
									throws RepositoryException {
								return fulltextSearch(
										searchInfo.getSearchPath(), fulltext,
										currentStep);
							}
						});

		return result;
	}

}
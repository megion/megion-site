package com.megion.site.core.service;

import info.magnolia.cms.core.MgnlNodeType;
import info.magnolia.cms.util.ContentUtil;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.module.blossom.dialog.TabBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megion.site.core.model.MenuItem;
import com.megion.site.core.model.navigation.BreadcrumbsNavigation;
import com.megion.site.core.model.navigation.NavNode;
import com.megion.site.core.model.navigation.NodeFinder;
import com.megion.site.core.model.navigation.NodeProcessor;
import com.megion.site.core.model.page.PageModel;
import com.megion.site.core.model.page.WebsitePage;
import com.megion.site.core.provider.WebsiteProvider;
import com.megion.site.core.util.JcrNodeUtils;

@SuppressWarnings("deprecation")
@Service
public class PageServiceImpl implements PageService {

	@Autowired
	private DataTypeService dataTypeService;

	@Autowired
	private TemplatingService templatingService;

	@Autowired
	private WebsiteProvider websiteProvider;

	@Override
	public void addMainPageDialogControls(TabBuilder tabBuilder)
			throws RepositoryException {
		tabBuilder.addEdit("title", "Заголовок", "Название страницы")
				.setRequired(true);
		tabBuilder
				.addCheckbox("hideInNavigation", "Не показывать в меню",
						"Установите чек-бокс чтобы не показывать страницу в меню навигации.");
		tabBuilder
				.addCheckbox("hideBreadcrumbs", "Не показывать Breadcrumbs",
						"Установите чек-бокс чтобы не показывать навигацию по страницам");
		tabBuilder.addCheckbox("hideInBreadcrumbs",
				"Не показывать в навигации Breadcrumbs",
				"Установите чек-бокс чтобы не показывать страницу в навигации");
		tabBuilder.addUuidLink("replaceInBreadcrumbsPage",
				"Страница для замены в Breadcrumbs",
				"Ссылка на страницу которая будет отображена в breadcrumbs вместо данной."
						+ " Если значение пустое отображается сама страница.");
		tabBuilder
				.addCheckbox(
						"notLink",
						"Убрать ссылку",
						"Установите чек-бокс чтобы убрать якорь на страницу в меню и в навигации Breadcrumbs");
		tabBuilder.addCheckbox("excludeInFulltextSearch",
				"Исключить страницу из полноценного поиска", "");
		tabBuilder.addCheckbox("hideInNav",
				"Не показывать страницу на карте сайта", "");
		tabBuilder
				.addEdit("replacementURL",
						"Относиельный URL страницы заменяющий стандартный",
						"Данный URL может быть использовани при формировании URL пункта меню")
				.setRequired(true);
	}

	@Override
	public WebsitePage createGenericWebsitePage(Node page)
			throws RepositoryException {
		WebsitePage websitePage = new WebsitePage(PropertyUtil.getString(page,
				"title"), JcrNodeUtils.getURL(page));
		websitePage.setTextTags(templatingService
				.getNodePropertyAsMultipleStrings(page, "tags"));
		return websitePage;
	}

	@Override
	public BreadcrumbsNavigation getBreadcrumbsNavigation(Node currentPage)
			throws RepositoryException {

		if (PropertyUtil.getBoolean(currentPage, "hideBreadcrumbs", false)) {
			return null;
		}

		final List<NavNode> navs = new ArrayList<NavNode>();
		templatingService.walkByParentNodes(currentPage, new NodeFinder() {
			@Override
			public boolean isFind(Node node) throws RepositoryException {
				return websiteProvider.getSiteRootPath().equals(node.getPath());
			}
		}, new NodeProcessor() {

			@Override
			public void processNode(Node node) throws RepositoryException {
				if (PropertyUtil.getBoolean(node, "hideInBreadcrumbs", false)) {
					return;
				}

				Node replacedNode = templatingService
						.getNodePropertyAsWebsiteUuidLink(node,
								"replaceInBreadcrumbsPage");
				if (replacedNode != null) {
					navs.add(new NavNode(JcrNodeUtils.getURL(replacedNode),
							PropertyUtil.getString(replacedNode, "title"),
							replacedNode, PropertyUtil.getBoolean(replacedNode,
									"notLink", false)));
				} else {
					navs.add(new NavNode(JcrNodeUtils.getURL(node),
							PropertyUtil.getString(node, "title"), node,
							PropertyUtil.getBoolean(node, "notLink", false)));
				}
			}
		});

		if (navs.isEmpty()) {
			return null;
		}

		Collections.reverse(navs);
		NavNode curNavNode = new NavNode(JcrNodeUtils.getURL(currentPage),
				PropertyUtil.getString(currentPage, "title"), currentPage);
		navs.add(curNavNode);

		BreadcrumbsNavigation breadcrumbs = new BreadcrumbsNavigation();
		breadcrumbs.setNavigations(navs);
		return breadcrumbs;
	}

	@Override
	public void addPageMetadataDialogControls(TabBuilder tabBuilder)
			throws RepositoryException {
		tabBuilder
				.addEdit(
						"metaDescription",
						"Метатег Description",
						"Данный тег используется при создании краткого описания страницы,"
								+ " используется поисковыми системами для индексации,"
								+ " а также при создании аннотации в выдаче по запросу.");

		tabBuilder
				.addEdit(
						"metaKeywords",
						"Метатег Keywords",
						"При формировании данного тега необходимо использовать только те слова,"
								+ " которые содержатся в самом документе."
								+ " Использование тех слов, которых нет на странице, не рекомендуется."
								+ " Рекомендованное количество слов в данном теге — не более десяти."
								+ " Пример содержимого: АйТи, IT");

		tabBuilder
				.addEdit(
						"metaRobots",
						"Мeтaтeг Robots",
						"Тег формирует информацию о гипертекстовых документах,"
								+ " которая поступает к роботам поисковых систем. Значение по умолчанию: all");

		tabBuilder.addEdit("metaCopyright", "Мeтaтeг Copyright",
				"Наименование организации автора Интернет-страницы");
	}

	@Override
	public PageModel getPageModel(Node page) {
		PageModel pageModel = new PageModel();
		pageModel.setTheme(templatingService.getCurrentTheme());
		return pageModel;
	}

	private List<MenuItem> populateMenuItems(Iterable<Node> nodes,
			Node currentNode, int level, int maxLevel) throws RepositoryException {
		if (nodes == null) {
			return null;
		}
		// ограничение по вложенности
		if (level > maxLevel) {
			return null;
		}
		List<MenuItem> menus = new ArrayList<MenuItem>();

		for (Node node : nodes) {
			if (!PropertyUtil.getBoolean(node, "hideInNavigation", false)) {
				// не показывать скрытые от навигации
				if (!ContentUtil.asContent(node).hasMixin(
						MgnlNodeType.MIX_DELETED)) {
					// не показывать удаленные узлы в magnolia
					boolean isSelected = node.getPath().equals(
							currentNode.getPath());
					List<MenuItem> children = populateMenuItems(
							NodeUtil.getNodes(node, MgnlNodeType.NT_PAGE),
							currentNode, level + 1, maxLevel);
					MenuItem menu = new MenuItem(JcrNodeUtils.getURL(node),
							PropertyUtil.getString(node, "title", ""),
							children, isSelected, node,
							PropertyUtil.getBoolean(node, "notLink", false));
					menu.setReplacementURL(PropertyUtil.getString(node, "replacementURL", null));
					menus.add(menu);
				}
			}
		}

		return menus;
	}

	@Override
	public List<MenuItem> getRootMenus(Node currentNode)
			throws PathNotFoundException, RepositoryException {
		Node homeNode = currentNode.getSession().getNode(
				websiteProvider.getSiteRootPath());
		Iterable<Node> topNodes = NodeUtil.getNodes(homeNode,
				MgnlNodeType.NT_PAGE);
		return populateMenuItems(topNodes, currentNode, 1, 4);
	}

	@Override
	public List<MenuItem> getSecondLevelMenus(Node currentNode)
			throws PathNotFoundException, RepositoryException {
		List<MenuItem> rootMenus = getRootMenus(currentNode);
		for (MenuItem item: rootMenus) {
			if(item.isSelected()) {
				return item.getChildren();
			}
		}
		
		return null;
	}

}
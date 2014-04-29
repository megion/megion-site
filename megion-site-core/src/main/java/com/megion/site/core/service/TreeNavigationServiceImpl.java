package com.megion.site.core.service;

import info.magnolia.cms.core.MgnlNodeType;
import info.magnolia.cms.util.ContentUtil;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.module.blossom.dialog.TabBuilder;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megion.site.core.model.navigation.NavNode;
import com.megion.site.core.model.navigation.TreeNavigation;
import com.megion.site.core.util.JcrNodeUtils;

@SuppressWarnings("deprecation")
@Service
public class TreeNavigationServiceImpl implements TreeNavigationService {

	@Autowired
	private TemplatingService templatingService;

	private void populateTreeNavigation(Iterable<Node> nodes, Node parentNode,
			Node currentNode, TreeNavigation nav) throws RepositoryException {

		Node prevNode = null;
		for (Node node : nodes) {
			if (!PropertyUtil.getBoolean(node, "hideInNavigation", false)) {
				// не показывать скрытые от навигации
				if (!ContentUtil.asContent(node).hasMixin(
						MgnlNodeType.MIX_DELETED)) {
					// не показывать удаленные узлы в magnolia

					if (nav.getNextNav() != null) {
						return;
					}

					if (currentNode.getIdentifier()
							.equals(node.getIdentifier())) {
						// находимся в текущем узле
						nav.setFound(true);
						if (prevNode != null) {
							nav.setPrevNav(createNavNode(prevNode));
						} else {
							if (!nav.getHomeNav().getNode().getIdentifier()
									.equals(node.getIdentifier())) {
								// текущий не равен главному узлу: тогда
								// установить предыдущий
								nav.setPrevNav(createNavNode(parentNode));
							}
						}
					} else {
						if (nav.getHomeNav().getNode().getIdentifier()
								.equals(currentNode.getIdentifier())) {
							nav.setNextNav(createNavNode(node));
						} else if (nav.isFound() && nav.getNextNav() == null) {
							nav.setNextNav(createNavNode(node));
						}
					}
					prevNode = node;

					populateTreeNavigation(
							NodeUtil.getNodes(node, MgnlNodeType.NT_PAGE),
							node, currentNode, nav);
				}
			}
		}
	}

	private NavNode createNavNode(Node node) throws RepositoryException {
		return new NavNode(JcrNodeUtils.getURL(node), PropertyUtil.getString(
				node, "title", ""), node);

	}

	@Override
	public TreeNavigation getTreeNavigation(Node treeNavigationComponent)
			throws RepositoryException {
		Node parentPage = templatingService.getNodePropertyAsWebsiteUuidLink(
				treeNavigationComponent, "parentPageLink");

		Node currentPage = templatingService
				.getContainerPage(treeNavigationComponent);

		Iterable<Node> childNodes = NodeUtil.getNodes(parentPage,
				MgnlNodeType.NT_PAGE);
		TreeNavigation nav = new TreeNavigation(createNavNode(parentPage));
		nav.setHidePageTitle(PropertyUtil.getBoolean(treeNavigationComponent,
				"hidePageTitle", false));
		populateTreeNavigation(childNodes, parentPage, currentPage, nav);
		return nav;
	}

	@Override
	public void addTreeNavigationDialogControls(TabBuilder tabBuilder) {
		tabBuilder
				.addCheckbox("hidePageTitle", "Не показывать названия страниц",
						"Установите чек-бокс чтобы не показывать названия страниц в навигации.");
		tabBuilder
				.addUuidLink("parentPageLink",
						"Страница с дочерними под-страницами",
						"Ссылка на страницу с дочерними под-страницами для отображания пейджинга")
				.setRequired(true);
	}

}
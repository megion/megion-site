package com.megion.site.core.service;

import info.magnolia.cms.core.MgnlNodeType;
import info.magnolia.cms.util.ContentUtil;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.module.blossom.dialog.TabBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megion.site.core.model.TabItem;
import com.megion.site.core.util.JcrNodeUtils;

@SuppressWarnings("deprecation")
@Service
public class NavigationTabServiceImpl implements NavigationTabService {

	@Autowired
	private TemplatingService templatingService;

	private List<TabItem> populateTabItems(Iterable<Node> nodes,
			Node currentNode) throws RepositoryException {
		List<TabItem> items = new ArrayList<TabItem>();

		for (Node node : nodes) {
			if (!PropertyUtil.getBoolean(node, "hideInNavigation", false)) {
				// не показывать скрытые от навигации
				if (!ContentUtil.asContent(node).hasMixin(
						MgnlNodeType.MIX_DELETED)) {
					// не показывать удаленные узлы в magnolia
					boolean isSelected = node.getPath().equals(
							currentNode.getPath());
					TabItem menu = new TabItem(JcrNodeUtils.getURL(node),
							PropertyUtil.getString(node, "title", ""),
							isSelected, node);
					items.add(menu);
				}
			}
		}

		return items;
	}

	@Override
	public List<TabItem> getNavigationTabs(Node parrentNode, Node currentNode)
			throws RepositoryException {
		Iterable<Node> childNodes = NodeUtil.getNodes(parrentNode,
				MgnlNodeType.NT_PAGE);
		return populateTabItems(childNodes, currentNode);
	}

	@Override
	public void addSubPagesAsTabsDialogControls(TabBuilder tabBuilder) {
		tabBuilder.addEdit("title", "Заголовок", "");
		tabBuilder
				.addUuidLink("parentPageLink",
						"Страница с дочерними под-страницами",
						"Ссылка на страницу с дочерними под-страницами для отображания как закладки")
				.setRequired(true);
	}

	@Override
	public List<TabItem> getSubPagesAsTabs(Node subPagesAsTabsComponent)
			throws RepositoryException {
		Node parentPage =  templatingService.getNodePropertyAsWebsiteUuidLink(
				subPagesAsTabsComponent, "parentPageLink");
		
		Node pageContainer = templatingService.getContainerPage(subPagesAsTabsComponent);
		return getNavigationTabs(parentPage, pageContainer);
	}

}
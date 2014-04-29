package com.megion.site.core.service;

import info.magnolia.cms.core.MgnlNodeType;
import info.magnolia.cms.gui.dialog.DialogEdit;
import info.magnolia.jcr.util.ContentMap;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.jcr.wrapper.HTMLEscapingNodeWrapper;
import info.magnolia.module.blossom.dialog.DialogCreationContext;
import info.magnolia.module.blossom.dialog.TabBuilder;
import info.magnolia.module.templatingkit.dam.Asset;
import info.magnolia.module.templatingkit.dam.DAMSupport;
import info.magnolia.module.templatingkit.functions.STKTemplatingFunctions;
import info.magnolia.module.templatingkit.style.Theme;
import info.magnolia.objectfactory.Classes;
import info.magnolia.objectfactory.Components;
import info.magnolia.repository.RepositoryConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jcr.AccessDeniedException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Value;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megion.site.core.model.Keyable;
import com.megion.site.core.model.navigation.NodeFinder;
import com.megion.site.core.model.navigation.NodeProcessor;
import com.megion.site.core.provider.WebsiteProvider;

@Service
public class TemplatingServiceImpl implements TemplatingService, InitializingBean {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory
			.getLogger(TemplatingServiceImpl.class);

	@Autowired
	private WebsiteProvider websiteProvider;
	
	private STKTemplatingFunctions stkTemplatingFunction;

	@Override
	public String getNodePropertyAsHtml(Node node, String propName) {
		ContentMap contentMap = new ContentMap(NodeUtil.deepUnwrap(node,
				HTMLEscapingNodeWrapper.class));
		String text = (String) contentMap.get(propName);
		return text;
	}

	@Override
	public Asset getNodePropertyAsImage(Node node, String propName) {
		Asset image = stkTemplatingFunction.getAsset(node, propName,
				DAMSupport.VARIATION_ORIGINAL);
		return image;
	}

	@Override
	public List<Node> getAreaComponents(Node areaNode)
			throws RepositoryException {
		Iterable<Node> nodeComponents = NodeUtil.getNodes(areaNode,
				MgnlNodeType.NT_COMPONENT);
		List<Node> components = new ArrayList<Node>();
		for (Node component : nodeComponents) {
			components.add(component);
		}
		return components;
	}

	@Override
	public Node getNodePropertyAsWebsiteUuidLink(Node node, String propName)
			throws RepositoryException {
		if (node.hasProperty(propName)) {
			Property property = PropertyUtil.getPropertyOrNull(node, propName);
			Value value = property.getValue();
			String id = value.getString();
			if (org.apache.commons.lang.StringUtils.isBlank(id)) {
				return null;
			}

			return NodeUtil
					.getNodeByIdentifier(RepositoryConstants.WEBSITE, id);

		}
		return null;
	}

	@Override
	public Node getContainerPage(Node componentNode) throws RepositoryException {
		return findParentNode(componentNode, new NodeFinder() {
			@Override
			public boolean isFind(Node node) throws RepositoryException {
				return node.isNodeType(MgnlNodeType.NT_PAGE);
			}
		}, null);
	}

	@Override
	public Node getTopParentNode(Node node) throws RepositoryException {
		return findParentNode(node, new NodeFinder() {
			@Override
			public boolean isFind(Node node) throws RepositoryException {
				return websiteProvider.getSiteRootPath().equals(node.getPath());
			}
		}, null);
	}

	/**
	 * Получит узел верхнего уровня для текущего узла
	 */
	private Node findParentNode(Node node, NodeFinder finder,
			NodeProcessor parentProcessor) throws AccessDeniedException,
			RepositoryException {
		Node nextNode = node;
		try {
			Node parentNode = nextNode.getParent();
			while (parentNode != null) {
				parentNode = nextNode.getParent();
				if (parentNode != null) {
					if (parentProcessor != null) {
						parentProcessor.processNode(parentNode);
					}
					if (finder != null && finder.isFind(parentNode)) {
						return parentNode;
					}
					nextNode = parentNode;
				}
			}

			return null;
		} catch (ItemNotFoundException ie) {
			return null;
		}
	}

	@Override
	public List<String> getNodePropertyAsMultipleStrings(Node node,
			String propName) throws RepositoryException {
		Property prop = PropertyUtil.getPropertyOrNull(node, propName);
		if (prop == null) {
			return null;
		}

		if (!prop.isMultiple()) {
			return null;
		}

		ArrayList<String> list = new ArrayList<String>();
		for (Value value : prop.getValues()) {
			String valStr = PropertyUtil.getValueString(value);
			if (!StringUtils.isBlank(valStr)) {
				list.add(valStr);
			}
		}
		return list;
	}

	@Override
	public void walkByParentNodes(Node node, NodeFinder parentFinder,
			NodeProcessor parentProcessor) throws RepositoryException {
		findParentNode(node, parentFinder, parentProcessor);
	}

	@Override
	public List<String> getNodePropertyAsMultipleSubNodesStrings(Node node,
			String propName) throws RepositoryException {
		if (!node.hasNode(propName)) {
			return null;
		}
		ArrayList<String> list = new ArrayList<String>();
		Node propertyNode = node.getNode(propName);
		PropertyIterator propIter = propertyNode.getProperties();
		while (propIter.hasNext()) {
			Property p = propIter.nextProperty();
			if (!p.isMultiple()
					&& !p.getName().startsWith(MgnlNodeType.JCR_PREFIX)) {
				list.add(p.getString());
			}
		}

		return list;
	}

	@Override
	public <T, E extends Keyable<T>> Set<T> transformToIds(Collection<E> keys) {
		Set<T> ids = new HashSet<T>();
		for (Keyable<T> key : keys) {
			ids.add(key.getId());
		}
		return ids;
	}
	
	@Override
	public DialogEdit addNumberLongControl(TabBuilder tab, String name,
			String label, String description) throws RepositoryException {
		DialogEdit control = Classes.getClassFactory().newInstance(
				DialogEdit.class);

		DialogCreationContext context = tab.getContext();
		control.init(context.getRequest(), context.getResponse(),
				context.getWebsiteNode(), null);

		control.setName(name);
		control.setLabel(label);
		control.setDescription(description);

		control.setConfig("type", PropertyType.TYPENAME_LONG);
		control.setConfig("width", "160px");
		tab.getTab().addSub(control);
		return control;
	}

	@Override
	public DialogEdit addNumberDoubleControl(TabBuilder tab, String name,
			String label, String description) throws RepositoryException {
		DialogEdit control = Classes.getClassFactory().newInstance(
				DialogEdit.class);

		DialogCreationContext context = tab.getContext();
		control.init(context.getRequest(), context.getResponse(),
				context.getWebsiteNode(), null);

		control.setName(name);
		control.setLabel(label);
		control.setDescription(description);

		control.setConfig("type", PropertyType.TYPENAME_DOUBLE);
		control.setConfig("width", "160px");
		tab.getTab().addSub(control);
		return control;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		stkTemplatingFunction = Components.getComponent(STKTemplatingFunctions.class);
	}

	@Override
	public Theme getCurrentTheme() {
		return stkTemplatingFunction.theme(stkTemplatingFunction.site());
	}

}
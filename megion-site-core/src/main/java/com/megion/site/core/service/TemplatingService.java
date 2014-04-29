package com.megion.site.core.service;

import info.magnolia.cms.gui.dialog.DialogEdit;
import info.magnolia.module.blossom.dialog.TabBuilder;
import info.magnolia.module.templatingkit.dam.Asset;
import info.magnolia.module.templatingkit.style.Theme;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import com.megion.site.core.model.Keyable;
import com.megion.site.core.model.navigation.NodeFinder;
import com.megion.site.core.model.navigation.NodeProcessor;

public interface TemplatingService {

	/**
	 * Получить HTML текст
	 */
	String getNodePropertyAsHtml(Node node, String propName);

	/**
	 * Получить картинку
	 */
	Asset getNodePropertyAsImage(Node node, String propName);

	/**
	 * Получить узел на который Uuid link с учетом того что используется
	 * репозиторий "website"
	 */
	Node getNodePropertyAsWebsiteUuidLink(Node node, String propName)
			throws RepositoryException;

	/**
	 * Получить свойства узла как список значений
	 */
	List<String> getNodePropertyAsMultipleStrings(Node node, String propName)
			throws RepositoryException;
	
	/**
	 * Получить свойства узла как список значений
	 */
	List<String> getNodePropertyAsMultipleSubNodesStrings(Node node, String propName)
			throws RepositoryException;

	/**
	 * Получить компоненты Area
	 */
	List<Node> getAreaComponents(Node areaNode) throws RepositoryException;

	Node getContainerPage(Node componentNode) throws RepositoryException;

	/**
	 * Получит узел самого верхнего уровня для указанного узла
	 */
	Node getTopParentNode(Node node) throws RepositoryException;

	void walkByParentNodes(Node node, NodeFinder parentFinder,
			NodeProcessor parentProcessor) throws RepositoryException;
	
	<T, E extends Keyable<T>> Set<T> transformToIds(Collection<E> keys);
	
	DialogEdit addNumberLongControl(TabBuilder tab, String name, String label,
			String description) throws RepositoryException;

	DialogEdit addNumberDoubleControl(TabBuilder tab, String name,
			String label, String description) throws RepositoryException;
	
	/**
	 * Получить текущую тему сайта 
	 */
	Theme getCurrentTheme();

}

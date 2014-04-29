package com.megion.site.core.service;

import info.magnolia.module.blossom.dialog.TabBuilder;

import java.util.List;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.ServletRequestBindingException;

import com.megion.site.core.model.pagination.PagedList;
import com.megion.site.core.model.pagination.Pagination;
import com.megion.site.core.model.pagination.PaginationResult;
import com.megion.site.core.model.pagination.Step;
import com.megion.site.core.model.pagination.UrlParam;

public interface PaginationService {

	/**
	 * Получить текущую страницу
	 */
	Step getCurrentStep(HttpServletRequest request, Node node, List<UrlParam> additionalUrlParams)
			throws ServletRequestBindingException, RepositoryException;

	/**
	 * Создать список с результатами для отображения на текущей странице
	 */
	<T> PagedList<T> createPageResults(List<T> allResult, Step currentStep);

	/**
	 * Создать объект пейджинга
	 */
	Pagination createPagination(Node node, Step currentStep, int total, List<UrlParam> additionalUrlParams)
			throws RepositoryException;

	/**
	 * Создать итоговый объект пейджинга
	 */
	<T> PaginationResult<T> createPaginationResult(HttpServletRequest request,
			Node node, List<UrlParam> additionalUrlParams, PagedListCreator<T> pagedListCreator)
			throws RepositoryException, ServletRequestBindingException;

	void addPagingDialogControls(TabBuilder tabBuilder);

	boolean isEnable(Node node);

}

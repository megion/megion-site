package com.megion.site.core.service;

import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.module.blossom.dialog.TabBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

import com.megion.site.core.enums.PagingPositionType;
import com.megion.site.core.model.pagination.PagedList;
import com.megion.site.core.model.pagination.Pagination;
import com.megion.site.core.model.pagination.PaginationResult;
import com.megion.site.core.model.pagination.Step;
import com.megion.site.core.model.pagination.UrlParam;
import com.megion.site.core.util.JcrNodeUtils;

@Service
public class PaginationServiceImpl implements PaginationService {

	protected static Integer MAX_RESULTS_PER_PAGE = 100;
	protected static Integer DEFAULT_RESULTS_PER_PAGE = 4;

	@Autowired
	private TemplatingService templatingService;

	@Override
	public Step getCurrentStep(HttpServletRequest request, Node componentNode,
			List<UrlParam> additionalUrlParams)
			throws ServletRequestBindingException, RepositoryException {
		Integer max = ServletRequestUtils.getIntParameter(request,
				getParamsPrefixNode(componentNode) + "max");
		Integer offset = ServletRequestUtils.getIntParameter(request,
				getParamsPrefixNode(componentNode) + "offset");
		if (max == null || offset == null) {
			return new Step(DEFAULT_RESULTS_PER_PAGE, 0,
					getParamsPrefixNode(componentNode), additionalUrlParams);
		}
		if (max.intValue() <= 0) {
			max = Integer.valueOf(1);
		}
		if (max.intValue() > MAX_RESULTS_PER_PAGE) {
			max = Integer.valueOf(MAX_RESULTS_PER_PAGE);
		}

		Step step = new Step(max, offset, getParamsPrefixNode(componentNode),
				additionalUrlParams);
		return step;
	}

	/**
	 * Префикс для получения параметров указанного узла\компонента
	 */
	private String getParamsPrefixNode(Node node) throws RepositoryException {
		return node.getIdentifier() + "_";
	}

	@Override
	public <T> PagedList<T> createPageResults(List<T> allResult,
			Step currentStep) {
		int total = allResult.size();
		int newMax = currentStep.getMax();
		int offset = currentStep.getOffset();
		List<T> result;
		if (total > offset) {
			if (total < offset + currentStep.getMax()) {
				newMax = total - offset;
			}

			result = allResult.subList(offset, offset + newMax);
		} else {
			result = new ArrayList<T>();
		}

		return new PagedList<T>(total, result);
	}

	@Override
	public Pagination createPagination(Node componentNode, Step currentStep,
			int total, List<UrlParam> additionalUrlParams)
			throws RepositoryException {
		int nbPages = (int) Math.ceil((double) total / currentStep.getMax());
		if (nbPages < 2) {
			return null;
		}
		int indexPage = (int) Math.ceil((double) currentStep.getOffset()
				/ currentStep.getMax());
		currentStep.setIndex(indexPage + 1);

		Pagination pagination = new Pagination();
		pagination.setCurrentLink(currentStep);
		if (indexPage > 0 && nbPages > 1) {
			Step prevStep = new Step(currentStep.getMax(),
					currentStep.getOffset() - currentStep.getMax(),
					getParamsPrefixNode(componentNode), additionalUrlParams);
			prevStep.setIndex(1);
			pagination.setPrevLink(prevStep);
		}
		if (indexPage < (nbPages - 1) && nbPages > 1) {
			Step nextStep = new Step(currentStep.getMax(),
					currentStep.getOffset() + currentStep.getMax(),
					getParamsPrefixNode(componentNode), additionalUrlParams);
			nextStep.setIndex(nbPages);
			pagination.setNextLink(nextStep);
		}
		List<Step> stepLinks = new ArrayList<Step>();
		for (int i = 0; i < nbPages; i++) {
			Step step = new Step(currentStep.getMax(),
					i * currentStep.getMax(),
					getParamsPrefixNode(componentNode), additionalUrlParams);
			step.setIndex(i + 1);
			stepLinks.add(step);
		}
		pagination.setStepLinks(stepLinks);

		Node pageNode = templatingService.getContainerPage(componentNode);
		String listUrl = JcrNodeUtils.getURL(pageNode);
		pagination.setPath(listUrl);

		PagingPositionType pagingPosition = PagingPositionType
				.getPagingPositionType(PropertyUtil.getString(componentNode,
						"pagingPositionType"));
		pagination.setPagingPosition(pagingPosition);

		return pagination;
	}

	@Override
	public void addPagingDialogControls(TabBuilder tabBuilder) {
		// options where key is label and value is value
		Map<String, String> typeOptions = new LinkedHashMap<String, String>();
		for (PagingPositionType type : PagingPositionType.values()) {
			typeOptions.put(type.getValue(), type.getId());
		}
		tabBuilder.addRadio("pagingPositionType", "Страницы", typeOptions,
				PagingPositionType.BOTTOM.getId());
	}

	@Override
	public boolean isEnable(Node componentNode) {
		PagingPositionType pagingPosition = PagingPositionType
				.getPagingPositionType(PropertyUtil.getString(componentNode,
						"pagingPositionType"));
		return pagingPosition == null
				|| (PagingPositionType.TOP.equals(pagingPosition)
						|| PagingPositionType.BOTTOM.equals(pagingPosition) || PagingPositionType.TOP_AND_BOTTOM
							.equals(pagingPosition));
	}

	@Override
	public <T> PaginationResult<T> createPaginationResult(
			HttpServletRequest request, Node node,
			List<UrlParam> additionalUrlParams,
			PagedListCreator<T> pagedListCreator) throws RepositoryException,
			ServletRequestBindingException {
		if (isEnable(node)) {
			// 1 текущий шаг пейджинга
			Step currentStep = getCurrentStep(request, node,
					additionalUrlParams);
			// 2
			PagedList<T> pagedResults = pagedListCreator.create(currentStep);

			// 3 объект пейджинга для отрисовки
			Pagination pagination = createPagination(node, currentStep,
					pagedResults.getTotal(), additionalUrlParams);

			return new PaginationResult<T>(pagination, pagedResults.getResult(), pagedResults.getTotal());
		} else {
			PagedList<T> pagedResults = pagedListCreator.create(null);
			return new PaginationResult<T>(null, pagedResults.getResult(), pagedResults.getTotal());
		}
	}

}
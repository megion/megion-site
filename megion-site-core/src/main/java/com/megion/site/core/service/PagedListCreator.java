package com.megion.site.core.service;

import javax.jcr.RepositoryException;

import com.megion.site.core.model.pagination.PagedList;
import com.megion.site.core.model.pagination.Step;

public interface PagedListCreator<T>  {

	PagedList<T> create(Step currentStep) throws RepositoryException;

}

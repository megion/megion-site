package com.megion.site.core.model.pagination;

import javax.jcr.query.QueryResult;

public class PagedQueryResult {

	private final int total;
	private final QueryResult result;

	public PagedQueryResult(int total, QueryResult result) {
		this.total = total;
		this.result = result;
	}
	
	public int getTotal() {
		return total;
	}

	public QueryResult getResult() {
		return result;
	}

}

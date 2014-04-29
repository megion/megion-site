package com.megion.site.core.model.pagination;

import java.util.List;

public class PagedList<T> {

	private final int total;
	private final List<T> result;

	public PagedList(int total, List<T> result) {
		this.total = total;
		this.result = result;
	}
	
	public int getTotal() {
		return total;
	}

	public List<T> getResult() {
		return result;
	}

}

package com.megion.site.core.model.pagination;

import java.util.List;

/**
 * Итоговый объект содержащий всю необходимую информацию для отрисовки
 * результатов страницы. Используется даже в случае отсутствия пейджинга тогда
 * объект pagination = NULL
 */
public class PaginationResult<T> {

	private final Pagination pagination;
	private final List<T> result;
	private final int total;

	public PaginationResult(Pagination pagination, List<T> result, int total) {
		this.pagination = pagination;
		this.result = result;
		this.total = total;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public List<T> getResult() {
		return result;
	}

	public int getTotal() {
		return total;
	}

}

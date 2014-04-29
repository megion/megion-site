package com.megion.site.core.model.areas.column;

import java.util.List;

public class ColumnArea {

	private Integer columnCount;

	private List<Column> columns;

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public Integer getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(Integer columnCount) {
		this.columnCount = columnCount;
	}

	public boolean isHasColumns() {
		return (columns != null && !columns.isEmpty());
	}

	@Override
	public String toString() {
		return "ColumnArea [columnCount=" + columnCount + ", columns=" + columns + "]";
	}

}

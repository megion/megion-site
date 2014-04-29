package com.megion.site.core.model.areas.table;

import java.util.List;

public class TableArea {

	private Integer columnCount;

	private List<TableRow> rows;

	public List<TableRow> getRows() {
		return rows;
	}

	public void setRows(List<TableRow> rows) {
		this.rows = rows;
	}

	public Integer getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(Integer columnCount) {
		this.columnCount = columnCount;
	}

	public boolean isHasRows() {
		return (rows != null && !rows.isEmpty());
	}

	@Override
	public String toString() {
		return "TableArea [columnCount=" + columnCount + ", rows=" + rows + "]";
	}

}

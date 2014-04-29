package com.megion.site.core.model.areas.table;

import java.util.ArrayList;
import java.util.List;

public class TableRow {

	private List<TableCell> cells = new ArrayList<TableCell>();

	public List<TableCell> getCells() {
		return cells;
	}

	public void setCells(List<TableCell> cells) {
		this.cells = cells;
	}

	@Override
	public String toString() {
		return "TableRow [cells=" + cells + "]";
	}

}

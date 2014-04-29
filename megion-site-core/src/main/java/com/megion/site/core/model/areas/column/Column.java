package com.megion.site.core.model.areas.column;

import java.util.ArrayList;
import java.util.List;

public class Column {

	private List<ColumnCell> cells = new ArrayList<ColumnCell>();
	private String htmlStyle;

	public List<ColumnCell> getCells() {
		return cells;
	}

	public void setCells(List<ColumnCell> cells) {
		this.cells = cells;
	}

	public String getHtmlStyle() {
		return htmlStyle;
	}

	public void setHtmlStyle(String htmlStyle) {
		this.htmlStyle = htmlStyle;
	}

	@Override
	public String toString() {
		return "Column [cells=" + cells + ", htmlStyle=" + htmlStyle + "]";
	}

}

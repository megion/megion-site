package com.megion.site.core.model.areas.table;

import javax.jcr.Node;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cell")
@XmlAccessorType(XmlAccessType.FIELD)
public class TableCell {

	/**
	 * порядковый номер ячейки
	 */
	@XmlElement(name = "num")
	private Integer num;

	@XmlElement(name = "rowspan")
	private Integer rowspan;

	@XmlElement(name = "colspan")
	private Integer colspan;

	@XmlElement(name = "style")
	private String style;

	private Node component;

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getRowspan() {
		return rowspan;
	}

	public void setRowspan(Integer rowspan) {
		this.rowspan = rowspan;
	}

	public Integer getColspan() {
		return colspan;
	}

	public void setColspan(Integer colspan) {
		this.colspan = colspan;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Node getComponent() {
		return component;
	}

	public void setComponent(Node component) {
		this.component = component;
	}

	public boolean isHasStyle() {
		return !org.apache.commons.lang.StringUtils.isBlank(style);
	}

	public boolean isHasColspan() {
		return colspan != null && colspan > 1;
	}

	public boolean isHasRowspan() {
		return rowspan != null && rowspan > 1;
	}

	public String getAttributesAsString() {
		String attrs = "";

		if (isHasColspan()) {
			attrs = attrs + "colsan='" + colspan + "'";
		}
		if (isHasRowspan()) {
			attrs = attrs + " rowspan='" + rowspan + "'";
		}
		if (isHasStyle()) {
			attrs = attrs +" style='" + style + "'";
		}
		return attrs;
	}

	@Override
	public String toString() {
		return "TableCell [num=" + num + ", rowspan=" + rowspan + ", colspan="
				+ colspan + ", style=" + style + ", component=" + component
				+ "]";
	}

}

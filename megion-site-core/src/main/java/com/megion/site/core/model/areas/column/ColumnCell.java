package com.megion.site.core.model.areas.column;

import javax.jcr.Node;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cell")
@XmlAccessorType(XmlAccessType.FIELD)
public class ColumnCell {

	/**
	 * порядковый номер компонента
	 */
	@XmlElement(name = "componentNum")
	private Integer componentNum;

	/**
	 * порядковый номер колонки
	 */
	@XmlElement(name = "columnNum")
	private Integer columnNum;

	@XmlElement(name = "style")
	private String style;

	private Node component;

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

	public String getAttributesAsString() {
		String attrs = "";

		if (isHasStyle()) {
			attrs = attrs + " style='" + style + "'";
		}
		return attrs;
	}

	public Integer getComponentNum() {
		return componentNum;
	}

	public void setComponentNum(Integer componentNum) {
		this.componentNum = componentNum;
	}

	public Integer getColumnNum() {
		return columnNum;
	}

	public void setColumnNum(Integer columnNum) {
		this.columnNum = columnNum;
	}

	@Override
	public String toString() {
		return "ColumnCell [componentNum=" + componentNum + ", columnNum="
				+ columnNum + ", style=" + style + ", component=" + component
				+ "]";
	}

}

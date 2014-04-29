package com.megion.site.core.model.areas.slider;

import java.util.List;

import javax.jcr.Node;

public class SliderBoxArea {

	private Integer itemMinWidth;

	private Boolean disabelPopupInfo;

	private List<Node> components;

	public Integer getItemMinWidth() {
		return itemMinWidth;
	}

	public void setItemMinWidth(Integer itemMinWidth) {
		this.itemMinWidth = itemMinWidth;
	}

	public List<Node> getComponents() {
		return components;
	}

	public void setComponents(List<Node> components) {
		this.components = components;
	}

	public Boolean getDisabelPopupInfo() {
		return disabelPopupInfo;
	}

	public void setDisabelPopupInfo(Boolean disabelPopupInfo) {
		this.disabelPopupInfo = disabelPopupInfo;
	}

}

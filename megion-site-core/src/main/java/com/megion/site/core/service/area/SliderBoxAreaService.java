package com.megion.site.core.service.area;

import info.magnolia.module.blossom.dialog.TabBuilder;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import com.megion.site.core.model.areas.slider.SliderBoxArea;

public interface SliderBoxAreaService {

	SliderBoxArea getSliderBoxArea(Node areaNode) throws RepositoryException;

	void addSliderBoxAreaDialogControls(TabBuilder tabBuilder) throws RepositoryException;

}

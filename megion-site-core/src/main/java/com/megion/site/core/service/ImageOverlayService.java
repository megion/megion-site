package com.megion.site.core.service;

import info.magnolia.module.blossom.dialog.TabBuilder;

import javax.jcr.Node;

import com.megion.site.core.model.ImageOverlay;


public interface ImageOverlayService {

	ImageOverlay getImageOverlay(Node imageOverlayComponent);
	
	void addImageOverlayDialogControls(TabBuilder tabBuilder);
	
}

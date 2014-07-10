package com.megion.site.core.service;

import info.magnolia.module.blossom.dialog.TabBuilder;
import info.magnolia.module.templatingkit.dam.Asset;

import javax.jcr.Node;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megion.site.core.model.ImageOverlay;

@Service
public class ImageOverlayServiceImpl implements ImageOverlayService {

	@Autowired
	private TemplatingService templatingService;
	@Autowired
	private DialogService dialogService;

	@Override
	public ImageOverlay getImageOverlay(Node imageOverlayComponent) {
		ImageOverlay overlay = new ImageOverlay();
		Asset minImage = templatingService.getNodePropertyAsImage(
				imageOverlayComponent, "minImage");
		Asset mainImage = templatingService.getNodePropertyAsImage(
				imageOverlayComponent, "mainImage");
		overlay.setMinImage(minImage);
		overlay.setImage(mainImage);
		return overlay;
	}

	@Override
	public void addImageOverlayDialogControls(TabBuilder tabBuilder) {
		dialogService.addDAM(tabBuilder, "minImage",
				"Небольшая картинка ссылка", "");
		dialogService.addDAM(tabBuilder, "mainImage",
				"Основная картинка", "");
	}

}
package com.megion.site.core.service;

import info.magnolia.module.blossom.dialog.TabBuilder;

import javax.jcr.Node;

import com.megion.site.core.model.Opinion;


public interface OpinionService {

	Opinion getOpinion(Node node);
	
	void addOpinionDialogControls(TabBuilder tabBuilder);

}

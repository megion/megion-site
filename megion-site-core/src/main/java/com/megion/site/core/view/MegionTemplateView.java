package com.megion.site.core.view;

import java.util.Locale;

import info.magnolia.module.blossom.view.TemplateView;

public class MegionTemplateView extends TemplateView {
	
	public boolean checkResource(Locale locale) throws Exception {
		return getApplicationContext().getResource(getUrl()).exists();
	}
}

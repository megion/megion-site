package com.megion.site.core.view;

import info.magnolia.module.blossom.view.TemplateViewResolver;

public class MegionTemplateViewResolver extends TemplateViewResolver {

	@Override
    protected Class<MegionTemplateView> requiredViewClass() {
        return MegionTemplateView.class;
    }
}

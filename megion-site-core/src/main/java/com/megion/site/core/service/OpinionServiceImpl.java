package com.megion.site.core.service;

import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.module.blossom.dialog.TabBuilder;

import javax.jcr.Node;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megion.site.core.constants.WebConstants;
import com.megion.site.core.model.Opinion;

@Service
public class OpinionServiceImpl implements OpinionService {

	@Autowired
	private TemplatingService templatingService;

	@Override
	public Opinion getOpinion(Node node) {
		String opinionText = templatingService.getNodePropertyAsHtml(node,
				"opinionText");
		String opinionPerson = PropertyUtil.getString(node, "opinionPerson");

		Opinion opinion = new Opinion(opinionPerson, opinionText);
		return opinion;
	}

	@Override
	public void addOpinionDialogControls(TabBuilder tabBuilder) {
		tabBuilder.addTextArea("opinionPerson", "Составитель мнения", "", 3)
				.setRequired(true);
		tabBuilder.addFckEditor("opinionText", "Текст мнения", "").setRequired(
				true);
		// скрытый параметр необходим для поиска проектов у которых есть хотябы
		// один блок с мнением
		tabBuilder.addHidden(WebConstants.OPINION_COMPONENT_TYPE_PROPERTY,
				WebConstants.OPINION_COMPONENT_TYPE_PROPERTY);
	}

}
package com.megion.site.core.service;

import info.magnolia.module.blossom.dialog.DialogCreationContext;
import info.magnolia.module.blossom.dialog.RuntimeRepositoryException;
import info.magnolia.module.blossom.dialog.TabBuilder;
import info.magnolia.module.fckeditor.dialogs.FckEditorDialog;
import info.magnolia.module.templatingkit.dam.dialog.DialogDAM;
import info.magnolia.objectfactory.Classes;

import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;

import org.springframework.stereotype.Service;

@Service
public class DialogServiceImpl implements DialogService {

	@Override
	public DialogDAM addDAM(TabBuilder tab, String name, String label,
			String description) {
		try {
			DialogDAM dialog = Classes.getClassFactory().newInstance(
					DialogDAM.class);
			dialog.setName(name);
			dialog.setLabel(label);
			dialog.setDescription(description);
			DialogCreationContext context = tab.getContext();
			dialog.init(context.getRequest(), context.getResponse(),
					context.getWebsiteNode(), null);
			dialog.setConfig("i18nBasename",
					"info.magnolia.module.templatingkit.messages");
			dialog.setConfig("type", PropertyType.STRING);
			tab.getTab().addSub(dialog);
			return dialog;
		} catch (RepositoryException e) {
			throw new RuntimeRepositoryException(e);
		}
	}

	@Override
	public FckEditorDialog addFckEditor(TabBuilder tabBuilder, String name,
			String label, String description) {
		FckEditorDialog dialog = tabBuilder.addFckEditor(name, label, description);
		dialog.setConfig("source", true);
        dialog.setConfig("images", true);
        dialog.setConfig("tables", true);
        dialog.setConfig("alignment", true);
        return dialog;
	}
}
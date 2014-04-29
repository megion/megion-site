package com.megion.site.core.util;

import info.magnolia.module.blossom.dialog.DialogCreationContext;
import info.magnolia.module.blossom.dialog.RuntimeRepositoryException;
import info.magnolia.module.blossom.dialog.TabBuilder;
import info.magnolia.module.fckeditor.dialogs.FckEditorDialog;
import info.magnolia.module.templatingkit.dam.dialog.DialogDAM;
import info.magnolia.objectfactory.Classes;

import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;

public class DialogUtils {

	public static void addDamControl(TabBuilder tab, String name, String label,
			String description) {
		try {
			DialogDAM control = Classes.getClassFactory().newInstance(
					DialogDAM.class);
			control.setName(name);
			control.setLabel(label);
			control.setDescription(description);
			DialogCreationContext context = tab.getContext();
			control.init(context.getRequest(), context.getResponse(),
					context.getWebsiteNode(), null);
			control.setConfig("i18nBasename",
					"info.magnolia.module.templatingkit.messages");
			tab.getTab().addSub(control);
			control.setConfig("type", PropertyType.STRING);
		} catch (RepositoryException e) {
			throw new RuntimeRepositoryException(e);
		}
	}

    public static void addFckEditor(TabBuilder tabBuilder, String name, String label, String description) {
        try {
            FckEditorDialog dialog = new FckEditorDialog();
            DialogCreationContext context = tabBuilder.getContext();
            dialog.init(context.getRequest(), context.getResponse(), context.getWebsiteNode(), null);
            dialog.setName(name);
            dialog.setLabel(label);
            dialog.setDescription(description);
            dialog.setConfig("source", true);
            dialog.setConfig("images", true);
            dialog.setConfig("tables", true);
            dialog.setConfig("alignment", true);
            tabBuilder.getTab().addSub(dialog);
        } catch (RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
}

package com.megion.site.core.service;

import info.magnolia.module.blossom.dialog.TabBuilder;
import info.magnolia.module.fckeditor.dialogs.FckEditorDialog;
import info.magnolia.module.templatingkit.dam.dialog.DialogDAM;

public interface DialogService {

	DialogDAM addDAM(TabBuilder tab, String name, String label,
			String description);

	FckEditorDialog addFckEditor(TabBuilder tabBuilder, String name, String label, String description);

}

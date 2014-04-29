package com.megion.site.core.web.component;

import info.magnolia.module.blossom.annotation.TabFactory;
import info.magnolia.module.blossom.annotation.Template;
import info.magnolia.module.blossom.annotation.TemplateDescription;
import info.magnolia.module.blossom.dialog.TabBuilder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Simple component for adding text to a page.
 */
@Controller
@Template(title = "Text", id = "megion-site:components/text")
@TemplateDescription("Simple text block")
public class TextComponent {

    @RequestMapping("/text")
    public String render() {
        return "components/text.jsp";
    }

    @TabFactory("Content")
    public void addDialog(TabBuilder tab) {
        tab.addFckEditor("body", "Text", "");
    }
}

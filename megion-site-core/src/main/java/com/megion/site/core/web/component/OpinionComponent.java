package com.megion.site.core.web.component;

import info.magnolia.module.blossom.annotation.TabFactory;
import info.magnolia.module.blossom.annotation.Template;
import info.magnolia.module.blossom.annotation.TemplateDescription;
import info.magnolia.module.blossom.dialog.TabBuilder;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.megion.site.core.model.Opinion;
import com.megion.site.core.service.OpinionService;

/**
 * Компонент отображающий мнение
 */
@Controller
@Template(title = "Opinion", id = "megion-site:components/opinion")
@TemplateDescription("Информация о мнении")
public class OpinionComponent {

	@Autowired
    private OpinionService opinionService;

    @RequestMapping("/opinion")
    public String handleRequest(ModelMap model, HttpSession session, Node node) throws PathNotFoundException, RepositoryException {
    	Opinion item = opinionService.getOpinion(node);
        model.put("opinion", item);
		
        return "components/opinion.jsp";
    }

    @TabFactory("Content")
    public void contentTab(TabBuilder tab) {
    	opinionService.addOpinionDialogControls(tab);
    }
}

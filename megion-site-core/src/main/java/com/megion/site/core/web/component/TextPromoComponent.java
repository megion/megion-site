package com.megion.site.core.web.component;

import info.magnolia.module.blossom.annotation.TabFactory;
import info.magnolia.module.blossom.annotation.Template;
import info.magnolia.module.blossom.annotation.TemplateDescription;
import info.magnolia.module.blossom.dialog.TabBuilder;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;

import com.megion.site.core.model.TextPromo;
import com.megion.site.core.service.PromoService;

/**
 * Компонент отображающий текстовый баннер
 */
@Controller
@Template(title = "Text promos and banners", id = "megion-site:components/textPromo")
@TemplateDescription("Текстовые баннеры")
public class TextPromoComponent {

	@Autowired
	private PromoService promoService;

	@RequestMapping("/textPromo")
	public String handleRequest(ModelMap model, HttpSession session,
			HttpServletRequest request, Node node)
			throws PathNotFoundException, RepositoryException,
			ServletRequestBindingException {

		TextPromo promo = promoService.getTextPromo(node);
		model.put("promo", promo);

		return "components/textPromo.jsp";
	}

	@TabFactory("Content")
	public void contentTab(TabBuilder tab) {
		promoService.addTextPromoDialogControls(tab);
	}
}

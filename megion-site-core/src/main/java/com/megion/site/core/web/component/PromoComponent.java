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

import com.megion.site.core.model.Promo;
import com.megion.site.core.service.PromoService;

/**
 * Компонент отображающий промо-акции и рекламу
 */
@Controller
@Template(title = "Promos and banners", id = "megion-site:components/promo")
@TemplateDescription("Промо-акции и реклама")
public class PromoComponent {

	@Autowired
	private PromoService promoService;

	@RequestMapping("/promo")
	public String handleRequest(ModelMap model, HttpSession session,
			HttpServletRequest request, Node node)
			throws PathNotFoundException, RepositoryException,
			ServletRequestBindingException {

		Promo promo = promoService.getPromo(node);
		model.put("promo", promo);

		return "components/promo.jsp";
	}

	@TabFactory("Content")
	public void contentTab(TabBuilder tab) {
		promoService.addPromoDialogControls(tab);
	}
}

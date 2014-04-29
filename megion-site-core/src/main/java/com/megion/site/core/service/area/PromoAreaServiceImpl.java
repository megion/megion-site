package com.megion.site.core.service.area;

import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.module.blossom.dialog.TabBuilder;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megion.site.core.model.areas.promo.PromoArea;
import com.megion.site.core.service.PromoService;
import com.megion.site.core.service.TemplatingService;

@Service
public class PromoAreaServiceImpl implements PromoAreaService {

	@Autowired
	private TemplatingService templatingService;

	@Autowired
	private PromoService promoService;

	@Override
	public void addPromoAreaDialogControls(TabBuilder tabBuilder) {
		tabBuilder
				.addCheckbox("enableCustomization",
						"Разрешить ввод собственных компонентов",
						"Установите чек-бокс для того чтобы можно было вводить собственные компоненты");
		tabBuilder.addCheckbox("hideStaticPromos",
				"Скрыть статический блок рекламы",
				"Установите чек-бокс для скрытия статического блока рекламы");
		tabBuilder
				.addCheckbox("disabelAutoGeneration",
						"Запрет автоматической генерации компонентов",
						"Установите чек-бокс для запрета автоматической генерации компонентов");
	}

	@Override
	public PromoArea getPromoArea(Node promoAreaNode)
			throws RepositoryException {
		PromoArea area = new PromoArea();
		area.setEnableCustomization(PropertyUtil.getBoolean(promoAreaNode,
				"enableCustomization", false));
		area.setDisabelAutoGeneration(PropertyUtil.getBoolean(promoAreaNode,
				"disabelAutoGeneration", false));
		area.setHideStaticPromos(PropertyUtil.getBoolean(promoAreaNode,
				"hideStaticPromos", false));
		return area;
	}

	@Override
	public void generatePromoAreaComponents(Node areaNode)
			throws RepositoryException {
		PromoArea area = getPromoArea(areaNode);
		if (area.getDisabelAutoGeneration()) {
			return;
		}

		promoService.generatePromoComponents(areaNode);
	}

	@Override
	public void generateTextPromoAreaComponents(Node areaNode)
			throws RepositoryException {
		PromoArea area = getPromoArea(areaNode);
		if (area.getDisabelAutoGeneration()) {
			return;
		}

		promoService.generateTextPromoComponents(areaNode);
	}

}
package com.megion.site.core.service.area;

import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.module.blossom.dialog.TabBuilder;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megion.site.core.model.areas.slider.SliderBoxArea;
import com.megion.site.core.service.DataTypeService;
import com.megion.site.core.service.TemplatingService;
import com.megion.site.core.util.JcrNodeUtils;

@Service
public class SliderBoxAreaServiceImpl implements SliderBoxAreaService {

	@Autowired
	private DataTypeService dataTypeService;
	
	@Autowired
	private TemplatingService templatingService;

	@Override
	public SliderBoxArea getSliderBoxArea(Node areaNode)
			throws RepositoryException {
		SliderBoxArea area = new SliderBoxArea();
		area.setItemMinWidth(JcrNodeUtils.getInteger(areaNode,
				"itemMinWidth", null));
		area.setComponents(templatingService.getAreaComponents(areaNode));
		
		area.setDisabelPopupInfo(PropertyUtil.getBoolean(areaNode,
				"disabelPopupInfo", false));
		
		return area;
	}

	@Override
	public void addSliderBoxAreaDialogControls(TabBuilder tabBuilder)
			throws RepositoryException {
		templatingService.addNumberLongControl(tabBuilder, "itemMinWidth",
				"Минимальная ширина элемента слайдера",
				"Минимальную ширина элемента слайдера"
						+ " следует задавать таким образом чтобы"
						+ " она была больше любого из неделимых элементов"
						+ " (картинки и тд.) содержимого каждого из"
						+ " слайдов. В противном случае подсчет страниц"
						+ " слайдера будет неверен.");
		tabBuilder
		.addCheckbox("disabelPopupInfo",
				"Запрет всплывающего окна с информацией",
				"Установите чек-бокс для запрета всплывающего окна с информацией");
	}

}
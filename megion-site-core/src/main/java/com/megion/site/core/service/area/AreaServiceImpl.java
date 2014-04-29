package com.megion.site.core.service.area;

import info.magnolia.module.blossom.dialog.TabBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megion.site.core.constants.WebConstants;
import com.megion.site.core.model.HtmlBlock;
import com.megion.site.core.model.HtmlHorizontalBlockSize;
import com.megion.site.core.service.DataTypeService;
import com.megion.site.core.service.TemplatingService;
import com.megion.site.core.util.JcrNodeUtils;

@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	private TemplatingService templatingService;
	
	@Autowired
	private DataTypeService dataTypeService;

	@Override
	public void addHorizontalAreaDialogControls(TabBuilder tabBuilder) throws RepositoryException {
		templatingService.addNumberLongControl(tabBuilder, "columnNumber", "Число колонок",
				"Число колонок по горизонтали");
		templatingService.addNumberLongControl(tabBuilder, "columnSpacer", "Пространство между колонками",
				"Пространство между колонками в px");
		templatingService.addNumberLongControl(tabBuilder, "containerWidth", "Ширина контейнера",
				"Ширина контейнера в px");
	}

	@Override
	public List<HtmlBlock> getHorizontalBlocks(Node areaNode)
			throws PathNotFoundException, RepositoryException {
		List<Node> components = templatingService.getAreaComponents(areaNode);
		int columnSpacer = JcrNodeUtils.getInteger(areaNode, "columnSpacer", 0);
		int containerWidth = JcrNodeUtils.getInteger(areaNode, "containerWidth", WebConstants.DEFAULT_SITE_WIDTH);
		int columnNumber = JcrNodeUtils.getInteger(areaNode, "columnNumber", components.size() > 6?6:components.size());

		List<HtmlBlock> horItems = new ArrayList<HtmlBlock>();
		HtmlHorizontalBlockSize blockSize = new HtmlHorizontalBlockSize(columnNumber,
				columnSpacer, containerWidth);
		int i = 0;
		for (Node item : components) {
			blockSize.calculate(i);
			String htmlStyle = "width: " + blockSize.getWidth()
					+ "px; margin-right: " + blockSize.getMarginRight() + "px;";
			HtmlBlock bl = new HtmlBlock(htmlStyle, item);
			horItems.add(bl);
			i++;
		}
		return horItems;
	}

}
package com.megion.site.core.service.area;

import info.magnolia.cms.gui.dialog.DialogMultiSelect;
import info.magnolia.module.blossom.dialog.TabBuilder;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.megion.site.core.constants.WebConstants;
import com.megion.site.core.model.HtmlHorizontalBlockSize;
import com.megion.site.core.model.areas.column.Column;
import com.megion.site.core.model.areas.column.ColumnArea;
import com.megion.site.core.model.areas.column.ColumnCell;
import com.megion.site.core.service.DataTypeService;
import com.megion.site.core.service.TemplatingService;
import com.megion.site.core.util.JcrNodeUtils;

@Service
public class ColumnAreaServiceImpl implements ColumnAreaService {
	
	private static final Logger log = LoggerFactory
			.getLogger(ColumnAreaServiceImpl.class);

	@Autowired
	private TemplatingService templatingService;
	
	@Autowired
	private DataTypeService dataTypeService;

	@Override
	public ColumnArea getColumnArea(Node areaNode) throws RepositoryException,
			JAXBException {
		List<Node> components = templatingService.getAreaComponents(areaNode);
		ColumnArea columnArea = new ColumnArea();
		int columnCount = JcrNodeUtils.getInteger(areaNode, "columnCount", components.size());
		if (columnCount==0) {
			columnCount = components.size();
		}
		
		columnArea.setColumnCount(columnCount);
		int columnSpacer = JcrNodeUtils.getInteger(areaNode, "columnSpacer", 0);
		int containerWidth = JcrNodeUtils.getInteger(areaNode, "containerWidth", WebConstants.DEFAULT_SITE_WIDTH);
		
		List<String> cellsJson = templatingService
				.getNodePropertyAsMultipleSubNodesStrings(areaNode,
						"cellsJson");
		Map<Integer, ColumnCell> cellByComponentNum = new HashMap<Integer, ColumnCell>(); 
		if (cellsJson!=null) {
			JAXBContext jc = JAXBContext.newInstance(ColumnCell.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			// Use JAXB for json:
			unmarshaller.setProperty("eclipselink.media-type", "application/json");
			for (String json: cellsJson) {
				String jsonNew = json.replace("'", "\"");
				Reader reader = new StringReader(jsonNew);
				ColumnCell cell = (ColumnCell)unmarshaller.unmarshal(new InputSource(reader));
				if (cell.getComponentNum()!=null) {
					cellByComponentNum.put(cell.getComponentNum(), cell);
				} else {
					log.warn("Skip column cell because component num is null: " + cell);
				}
			}
		}
		
		Map<Integer, Column> columnByNum = new LinkedHashMap<Integer, Column>();
		HtmlHorizontalBlockSize blockSize = new HtmlHorizontalBlockSize(columnCount,
				columnSpacer, containerWidth);
		for (int j=0;j<columnCount;j++) {
			Column col = new Column();
			blockSize.calculate(j);
			String htmlStyle = "width: " + blockSize.getWidth()
					+ "px; margin-right: " + blockSize.getMarginRight() + "px;";
			col.setHtmlStyle(htmlStyle);
			columnByNum.put(Integer.valueOf(j+1), col);
		}
		
		int i = 0;
		int curColNum = 0;
		
		for (Node item : components) {
			ColumnCell cell;
			Integer componentNum = Integer.valueOf(i+1);
			if (cellByComponentNum.containsKey(componentNum)) {
				cell = cellByComponentNum.get(componentNum);
			} else {
				cell = new ColumnCell();
			}
			cell.setComponent(item);
			
			
			if (i%columnCount==0) {
				curColNum = 0;
			}
			Column col;
			if (cell.getColumnNum()!=null && 
					columnByNum.containsKey(cell.getColumnNum())) {
				col = columnByNum.get(cell.getColumnNum());
			} else {
				col = columnByNum.get(Integer.valueOf(curColNum+1));
			}
			col.getCells().add(cell);
			
			i++;
			curColNum++;
		}
		
		columnArea.setColumns(new ArrayList<Column>(columnByNum.values()));
		return columnArea;
	}

	@Override
	public void addColumnAreaDialogControls(TabBuilder tabBuilder)
			throws RepositoryException {
		templatingService.addNumberLongControl(tabBuilder, "columnCount",
				"Число колонок", "").setRequired(true);
		templatingService.addNumberLongControl(tabBuilder, "columnSpacer",
				"Пространство между колонками", "Пространство между колонками в px");
		templatingService.addNumberLongControl(tabBuilder, "containerWidth",
				"Ширина контейнера", "Ширина контейнера в px");
		
		DialogMultiSelect ms = tabBuilder
				.addMultiSelect(
						"cellsJson",
						"Ячейки колонок",
						"Ячейки колонок в формате JSON, пример:"
								+ " {'cell':{'componentNum': 2, 'columnNum': 2, 'style': 'color: red; width:100px'}}");
		ms.setConfig("width", "520px");
		
	}

}
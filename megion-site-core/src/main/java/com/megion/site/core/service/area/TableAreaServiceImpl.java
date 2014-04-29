package com.megion.site.core.service.area;

import info.magnolia.cms.gui.dialog.DialogMultiSelect;
import info.magnolia.module.blossom.dialog.TabBuilder;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.megion.site.core.model.areas.table.TableArea;
import com.megion.site.core.model.areas.table.TableCell;
import com.megion.site.core.model.areas.table.TableRow;
import com.megion.site.core.service.DataTypeService;
import com.megion.site.core.service.TemplatingService;
import com.megion.site.core.util.JcrNodeUtils;

@Service
public class TableAreaServiceImpl implements TableAreaService {
	
	private static final Logger log = LoggerFactory
			.getLogger(TableAreaServiceImpl.class);

	@Autowired
	private TemplatingService templatingService;
	
	@Autowired
	private DataTypeService dataTypeService;

	@Override
	public TableArea getTableArea(Node areaNode) throws RepositoryException, JAXBException {
		TableArea tableArea = new TableArea();
		int columnCount = JcrNodeUtils.getInteger(areaNode, "columnCount", 0);
		tableArea.setColumnCount(columnCount);
		
		List<String> cellsJson = templatingService
				.getNodePropertyAsMultipleSubNodesStrings(areaNode,
						"cellsJson");
		Map<Integer, TableCell> tableCells = new HashMap<Integer, TableCell>(); 
		if (cellsJson!=null) {
			JAXBContext jc = JAXBContext.newInstance(TableCell.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			// Use JAXB for json:
			unmarshaller.setProperty("eclipselink.media-type", "application/json");
			for (String json: cellsJson) {
				String jsonNew = json.replace("'", "\"");
				Reader reader = new StringReader(jsonNew);
				TableCell cell = (TableCell)unmarshaller.unmarshal(new InputSource(reader));
				if (cell.getNum()!=null) {
					tableCells.put(cell.getNum(), cell);
				} else {
					log.warn("Skip table cell because num is null: " + cell);
				}
			}
		}
		
		List<TableRow> rows = new ArrayList<TableRow>();
		int i = 0;
		
		List<Node> components = templatingService.getAreaComponents(areaNode);
		TableRow row = new TableRow();
		if (columnCount<=0) {
			rows.add(row);
		}
		for (Node item : components) {
			if (columnCount>0 && i%columnCount==0) {
				row = new TableRow();
				rows.add(row);
			}
			
			TableCell cell;
			Integer num = Integer.valueOf(i+1);
			if (tableCells.containsKey(num)) {
				cell = tableCells.get(num);
			} else {
				cell = new TableCell();
			}
			
			cell.setComponent(item);
			row.getCells().add(cell);
			
			i++;
		}
		tableArea.setRows(rows);
		return tableArea;
	}

	@Override
	public void addTableAreaDialogControls(TabBuilder tabBuilder) throws RepositoryException {
		templatingService.addNumberLongControl(tabBuilder, "columnCount",
				"Число колонок", "");
		
		DialogMultiSelect ms = tabBuilder
				.addMultiSelect(
						"cellsJson",
						"Ячейки таблицы",
						"Ячейки таблицы в формате JSON, пример:"
								+ " {'cell':{'num': 2, 'rowspan': 2, 'colspan': 1, 'style': 'color: red; width:100px'}}");
		ms.setConfig("width", "520px");
		
	}

}
package com.megion.site.core.service;

import info.magnolia.cms.gui.dialog.DialogMultiSelect;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.module.blossom.dialog.TabBuilder;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.megion.site.core.enums.TabNumber;
import com.megion.site.core.enums.TextPositionType;
import com.megion.site.core.model.yandex.map.GeoMap;
import com.megion.site.core.model.yandex.map.Placemark;
import com.megion.site.core.util.JcrNodeUtils;

@Service
public class GeoMapServiceImpl implements GeoMapService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory
			.getLogger(GeoMapServiceImpl.class);

	@Autowired
	private TemplatingService templatingService;
	@Autowired
	private DataTypeService dataTypeService;
	@Autowired
	private DialogService dialogService;

	@Override
	public void addGeoMapDialogControls(TabBuilder tabBuilder,
			TabNumber tabNumber) throws RepositoryException {
		if (TabNumber.FIRST.equals(tabNumber)) {
			tabBuilder.addEdit("mapId", "Идентификатор",
					"Идентификатор карты используемый для доступа в Yandex map API."
							+ " На странице должен быть уникален.");
			templatingService.addNumberDoubleControl(tabBuilder, "centerLat",
					"Широта центра карты", "").setRequired(true);
			templatingService.addNumberDoubleControl(tabBuilder, "centerLon",
					"Долгота центра карты", "").setRequired(true);
			templatingService.addNumberLongControl(tabBuilder, "zoom",
					"Увеличение", "").setRequired(true);
			templatingService.addNumberLongControl(tabBuilder, "mapWidth",
					"Ширина карты", "");
			templatingService.addNumberLongControl(tabBuilder, "mapHeight",
					"Высота карты", "");
		} else if (TabNumber.SECOND.equals(tabNumber)) {
			DialogMultiSelect ms = tabBuilder
					.addMultiSelect(
							"placemarksJson",
							"Метки на карте",
							"Метки на карте в формате JSON, пример:"
									+ " {'placemark':{'lat': 55.76, 'lon': 37.64, 'hintContent': 'Москва!', 'balloonContent': 'Столица России'}}");
			ms.setConfig("width", "520px");
		} else if (TabNumber.THIRD.equals(tabNumber)) {
			tabBuilder.addEdit("title", "Заголовок", "");
			
			Map<String, String> typeOptions = new LinkedHashMap<String, String>();
			for (TextPositionType type : TextPositionType.values()) {
				typeOptions.put(type.getValue(), type.getId());
			}
			tabBuilder.addRadio("textPositionType", "Расположение текста", typeOptions,
					TextPositionType.RIGHT.getId());
			
			dialogService.addFckEditor(tabBuilder, "mapText", "Текст описания", "");
			
			templatingService.addNumberLongControl(tabBuilder, "textWidth",
					"Ширина текста", "");
		}
	}

	@Override
	public GeoMap getGeoMap(Node geoMapComponent) throws RepositoryException, JAXBException {
		GeoMap geoMap = new GeoMap();
		geoMap.setId(PropertyUtil.getString(geoMapComponent, "mapId"));
		geoMap.setCenterLat(JcrNodeUtils.getDouble(geoMapComponent,
				"centerLat", 0d));
		geoMap.setCenterLon(JcrNodeUtils.getDouble(geoMapComponent,
				"centerLon", 0d));
		geoMap.setZoom(JcrNodeUtils.getInteger(geoMapComponent, "zoom", 0));

		geoMap.setWidth(JcrNodeUtils.getInteger(geoMapComponent, "mapWidth",
				null));
		geoMap.setHeight(JcrNodeUtils.getInteger(geoMapComponent, "mapHeight",
				null));
		
		geoMap.setTitle(PropertyUtil.getString(geoMapComponent, "title"));
		geoMap.setMapText(templatingService.getNodePropertyAsHtml(geoMapComponent,
				"mapText"));
		TextPositionType textPosition = TextPositionType
				.getTextPositionType(PropertyUtil.getString(geoMapComponent,
						"textPositionType"));
		geoMap.setTextPosition(textPosition);
		geoMap.setTextWidth(JcrNodeUtils.getInteger(geoMapComponent, "textWidth",
				null));

		List<String> placemarksJson = templatingService
				.getNodePropertyAsMultipleSubNodesStrings(geoMapComponent,
						"placemarksJson");
		if (placemarksJson!=null) {
			List<Placemark> placemarks = new ArrayList<Placemark>(); 
			JAXBContext jc = JAXBContext.newInstance(Placemark.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			// Use JAXB for json:
			unmarshaller.setProperty("eclipselink.media-type", "application/json");
			for (String json: placemarksJson) {
				String jsonNew = json.replace("'", "\"");
				Reader reader = new StringReader(jsonNew);
				Placemark placemark = (Placemark)unmarshaller.unmarshal(new InputSource(reader));
				placemarks.add(placemark);
			}
			
			geoMap.setPlacemarks(placemarks);
		}
		

		return geoMap;
	}

	@Override
	public String createGeoMapJson(GeoMap geoMap) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(GeoMap.class);
		Marshaller marshaller = jc.createMarshaller();
		// Use JAXB for json:
		// http://www.eclipse.org/eclipselink/documentation/2.5/moxy/runtime001.htm
		marshaller.setProperty("eclipselink.media-type", "application/json");
		Writer writer = new StringWriter();
		marshaller.marshal(geoMap, writer);
		String json = writer.toString();
		return json;
	}

}
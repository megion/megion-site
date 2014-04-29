package com.megion.site.core.model.page;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.megion.site.core.model.datatype.DataType;
import com.megion.site.core.model.datatype.DataTypeValue;

/**
 * Модель страницы с типами данных
 */
public class DataTypesPage extends WebsitePage {

	private final Map<DataType, Set<DataTypeValue>> valuesByType;

	public DataTypesPage(String title, String uRL) {
		super(title, uRL);
		valuesByType = new LinkedHashMap<DataType, Set<DataTypeValue>>();
	}

	public Map<DataType, Set<DataTypeValue>> getValuesByType() {
		return valuesByType;
	}

	public void putDataTypeValues(DataType dataType, Set<DataTypeValue> values) {
		this.valuesByType.put(dataType, values);
	}

	public Set<DataTypeValue> getDataTypeValues(DataType dataType) {
		return this.valuesByType.get(dataType);
	}

}

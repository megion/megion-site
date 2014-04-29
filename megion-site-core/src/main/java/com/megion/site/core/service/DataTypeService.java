package com.megion.site.core.service;

import info.magnolia.module.blossom.dialog.TabBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;

import com.megion.site.core.model.datatype.DataType;
import com.megion.site.core.model.datatype.DataTypeValue;

public interface DataTypeService {

	/**
	 * Найти DataType по указанному treeName
	 */
	DataType findDataTypeByTreeName(String treeName);

	/**
	 * Добавить контрол выбора значений указанного типа данных
	 */
	void addDataTypeMultiSelectControl(TabBuilder tab, DataType dataType,
			String name, String label, String description)
			throws RepositoryException;

	/**
	 * Получить множественное значение справочника хранящиеся в узле
	 */
	List<Node> getNodePropertyAsDataTypes(Node node, String propName)
			throws ValueFormatException, IllegalStateException,
			RepositoryException;

	/**
	 * Получить множественное значение справочника хранящиеся в узле как список
	 * тегов
	 */
	Set<DataTypeValue> getNodeDataTypeValues(Node node, String propName, DataType dataType)
			throws ValueFormatException, IllegalStateException,
			RepositoryException;

	/**
	 * Получить все значения для указанного типа данных в виде Map, где ключ это ID узла а
	 * значение это DataTypeValue
	 */
	Map<String, DataTypeValue> getAllDataTypeValuesAsMap(DataType dataType)
			throws LoginException, RepositoryException;

}

package com.megion.site.core.provider;

import info.magnolia.module.blossom.dialog.TabBuilder;

import java.util.Collection;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import com.megion.site.core.model.datatype.DataType;
import com.megion.site.core.model.datatype.DataTypeValue;

/**
 * Провайдер для работы с DataType Провайдер должен быть реализован в WAR
 * проекте в единичном экземпляре.
 */
public interface DataTypeProvider {
	/**
	 * @return все зарегистрированные типы данных DataType
	 */
	Collection<DataType> getAllDataType();

	/**
	 * @return все зарегистрированные типы данных DataType как Map где ключом
	 *         является treeName
	 */
	Map<String, DataType> getDataTypeMapByTreeName();
	
	/**
	 * Создать значение значение типа данных для указанного узла
	 */
	<T extends DataTypeValue> T createDataTypeValue(Node node, DataType dataType) throws RepositoryException;
	
	/**
	 * Добавить поля диалога редактирования типа данных
	 */
	void addDataTypeDialogControls(TabBuilder tabBuilder, DataType dataType) throws RepositoryException;

}

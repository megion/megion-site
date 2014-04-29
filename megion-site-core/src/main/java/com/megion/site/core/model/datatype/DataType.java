package com.megion.site.core.model.datatype;

import com.megion.site.core.admininterface.DataTypeSaveHandler;

/**
 * Информация о типе данных
 */
public class DataType {

	/**
	 * Путь в репозитории data к типу данных
	 */
	private final String rootPath;

	/**
	 * Наименование дерева в JCR хранилище /modules/data/trees. Наименование
	 * должно быть уникальным
	 */
	private final String treeName;

	/**
	 * Class для сохранения типа данных
	 */
	private final Class<? extends DataTypeSaveHandler> saveHandler;

	/**
	 * Лейбл
	 */
	private final String label;

	public DataType(String rootPath, String treeName,
			Class<? extends DataTypeSaveHandler> saveHandler, String label) {
		this.rootPath = rootPath;
		this.treeName = treeName;
		this.saveHandler = saveHandler;
		this.label = label;
	}

	public String getRootPath() {
		return rootPath;
	}

	public String getTreeName() {
		return treeName;
	}

	public Class<? extends DataTypeSaveHandler> getSaveHandler() {
		return saveHandler;
	}

	public String getLabel() {
		return label;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((treeName == null) ? 0 : treeName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataType other = (DataType) obj;
		if (treeName == null) {
			if (other.treeName != null)
				return false;
		} else if (!treeName.equals(other.treeName))
			return false;
		return true;
	}

}

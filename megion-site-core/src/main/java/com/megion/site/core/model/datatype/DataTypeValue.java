package com.megion.site.core.model.datatype;

import com.megion.site.core.model.Keyable;

/**
 * Модельный класс хранящий значение типа данных DataType
 */
public class DataTypeValue implements Keyable<String> {

	/**
	 * Node.getIdentifier()
	 */
	private final String id;

	private String name;
	private String displayName;

	/**
	 * Порядковый номер расположения в дереве
	 */
	private int order;
	
	/**
	 * URL страницы данного значения типа данных
	 */
	private String URL;

	public DataTypeValue(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String getId() {
		return id;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		DataTypeValue other = (DataTypeValue) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DataTypeValue [id=" + id + ", name=" + name + ", displayName="
				+ displayName + ", order="
				+ order + "]";
	}

}

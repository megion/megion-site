package com.megion.site.core.model.datatype;


/**
 * Содержит сгруппированную информацию для значения типа данных. Например
 * количество проектов для указанного значения типа данных.
 */
public class DataTypeValueAggregation implements
		Comparable<DataTypeValueAggregation> {

	/**
	 * Значение типа данных
	 */
	private final DataTypeValue typeValue;

	/**
	 * Число сущностей которые относятся к данному значению типа данных
	 */
	private int count;

	/**
	 * Нормализованное значение count, для отрисовки при помощи CSS
	 */
	private int normolizedCount;

	public DataTypeValueAggregation(DataTypeValue typeValue) {
		this.typeValue = typeValue;
	}

	public DataTypeValue getTypeValue() {
		return typeValue;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getNormolizedCount() {
		return normolizedCount;
	}

	public void setNormolizedCount(int normolizedCount) {
		this.normolizedCount = normolizedCount;
	}

	@Override
	public int compareTo(DataTypeValueAggregation o) {
		int thisVal = this.typeValue.getOrder();
		int anotherVal = o.getTypeValue().getOrder();
		return (thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1));
	}

}

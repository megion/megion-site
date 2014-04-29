package com.megion.site.core.enums;

/**
 * Справочник местоположения пейджинга
 */
public enum PagingPositionType {

	TOP("1", "На верху"), //
	BOTTOM("2", "Внизу"), //
	TOP_AND_BOTTOM("3", "Наверху и внизу"), //
	NONE("4", "Без страниц");

	private final String id;
	private final String value;

	private PagingPositionType(String id, String value) {
		this.id = id;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public String getValue() {
		return value;
	}
	
	public static PagingPositionType getPagingPositionType(String id) {
		for(PagingPositionType v: PagingPositionType.values()) {
			if(v.getId().equals(id)) {
				return v;
			}
		}
		
		return null;
	}

}

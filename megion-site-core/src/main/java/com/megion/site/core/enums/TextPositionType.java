package com.megion.site.core.enums;

/**
 * Справочник местоположения текста
 */
public enum TextPositionType {

	LEFT("1", "Слева"), //
	RIGHT("2", "Справа");
	
	private final String id;
	private final String value;

	private TextPositionType(String id, String value) {
		this.id = id;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public String getValue() {
		return value;
	}
	
	public static TextPositionType getTextPositionType(String id) {
		for(TextPositionType v: TextPositionType.values()) {
			if(v.getId().equals(id)) {
				return v;
			}
		}
		
		return null;
	}

}

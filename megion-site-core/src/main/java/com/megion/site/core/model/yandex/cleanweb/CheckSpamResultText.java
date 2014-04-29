package com.megion.site.core.model.yandex.cleanweb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class CheckSpamResultText {
	
	@XmlAttribute(name = "spam-flag")
	protected String spamFlag;

	@XmlValue
	protected String value;

	public String getSpamFlag() {
		return spamFlag;
	}


	public void setSpamFlag(String spamFlag) {
		this.spamFlag = spamFlag;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "CheckSpamResultText [spamFlag=" + spamFlag + ", value=" + value
				+ "]";
	}

}

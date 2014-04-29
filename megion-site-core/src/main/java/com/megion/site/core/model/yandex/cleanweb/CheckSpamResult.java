package com.megion.site.core.model.yandex.cleanweb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "check-spam-result")
@XmlAccessorType(XmlAccessType.FIELD)
public class CheckSpamResult {

	@XmlElement(name = "id")
	protected String id;

	@XmlElement(name = "text")
	protected CheckSpamResultText text;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CheckSpamResultText getText() {
		return text;
	}

	public void setText(CheckSpamResultText text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "CheckSpamResult [id=" + id + ", text=" + text + "]";
	}

}

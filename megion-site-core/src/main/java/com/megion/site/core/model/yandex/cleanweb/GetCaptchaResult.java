package com.megion.site.core.model.yandex.cleanweb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "get-captcha-result")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetCaptchaResult {

	@XmlElement(name = "captcha")
	private String captcha;

	@XmlElement(name = "url")
	protected String url;

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "GetCaptchaResult [captcha=" + captcha + ", url=" + url + "]";
	}

}

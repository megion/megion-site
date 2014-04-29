package com.megion.site.core.model.yandex.cleanweb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "check-captcha-result")
@XmlAccessorType(XmlAccessType.FIELD)
public class CheckCaptchaResult {

	@XmlElement(name = "ok")
	protected String ok;

	@XmlElement(name = "failed")
	protected String failed;

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public String getFailed() {
		return failed;
	}

	public void setFailed(String failed) {
		this.failed = failed;
	}
	
	public boolean isValid() {
		return this.ok != null;
	}

	@Override
	public String toString() {
		return "CheckCaptchaResult [ok=" + ok + ", failed=" + failed + "]";
	}

}

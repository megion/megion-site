package com.megion.site.core.service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.sax.SAXSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.megion.site.core.model.yandex.cleanweb.CheckCaptcha;
import com.megion.site.core.model.yandex.cleanweb.CheckCaptchaResult;
import com.megion.site.core.model.yandex.cleanweb.CheckSpam;
import com.megion.site.core.model.yandex.cleanweb.CheckSpamResult;
import com.megion.site.core.model.yandex.cleanweb.GetCaptchaResult;

@Service
public class YandexCaptchaServiceImpl implements YandexCaptchaService {

	private static final Logger log = LoggerFactory
			.getLogger(YandexCaptchaServiceImpl.class);

	@SuppressWarnings("unchecked")
	private <T> T unmarshalFromUrl(String urlStr, String urlParams, boolean isPost, Class<T> clazz) throws IOException,
			JAXBException, XMLStreamException, SAXException,
			ParserConfigurationException {
		
		HttpURLConnection connection;
		if (isPost) {
			URL url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParams.getBytes().length));
			connection.setRequestProperty("Accept-Charset", "UTF-8");
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
			wr.writeBytes(urlParams);
			wr.flush();
			wr.close();
		} else {
			URL url = new URL(urlStr + "?" + urlParams);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept-Charset", "UTF-8");
			connection.setUseCaches(false);
		}
		
		InputStream input = null;
		try {
			input = connection.getInputStream();

			JAXBContext jc = JAXBContext.newInstance(clazz);

			SAXParserFactory spf = SAXParserFactory.newInstance();
			spf.setValidating(false);
			spf.setNamespaceAware(true);
			spf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			spf.setFeature(
					"http://apache.org/xml/features/nonvalidating/load-external-dtd",
					false);
			XMLReader xmlReader = spf.newSAXParser().getXMLReader();

			InputSource inputSource = new InputSource(input);
			SAXSource source = new SAXSource(xmlReader, inputSource);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			T result = (T) unmarshaller.unmarshal(source);
			return result;
		} catch (IOException e) {
			int respCode = connection.getResponseCode();
			log.error("HTTP error code: " + respCode);
			throw e;
		} finally {
			if (input != null) {
				input.close();
			}
			connection.disconnect();
		}
	}

	@Override
	public CheckSpamResult checkSpam(CheckSpam checkSpam, String yandexKey,
			HttpServletRequest request) throws Exception {
		// http://cleanweb-api.yandex.ru/1.0/check-spam?
		// key=cw.1.1.20110707T172051Z.faf547ce44f3d10b.d7e3028845ea04f56c38f7eef90999f765dd0d1f
		// &ip=10.178.33.2
		// &name=Vasia
		// &subject-plain=%D0%9E+%D1%81%D0%BF%D0%B0%D0%BC%D0%B5
		// &body-plain=%D0%AF+%D0%BD%D0%B5+%D1%81%D0%BF%D0%B0%D0%BC%D0%B5%D1%80

		String url = "http://cleanweb-api.yandex.ru/1.0/check-spam";
		String key = yandexKey;
		String ip = request.getRemoteAddr();
		String name = checkSpam.getName() == null ? "" : checkSpam
				.getName();
		String subjectPlain = checkSpam.getSubjectPlain() == null ? ""
				: checkSpam.getSubjectPlain();
		String bodyPlain = checkSpam.getBodyPlain() == null ? ""
				: checkSpam.getBodyPlain();

		String charset = "UTF-8";
		String query = String.format(
				"key=%s&ip=%s&name=%s&subject-plain=%s&body-plain=%s", key, ip,
				URLEncoder.encode(name, charset),
				URLEncoder.encode(subjectPlain, charset),
				URLEncoder.encode(bodyPlain, charset));

		CheckSpamResult result = unmarshalFromUrl(url, query, true,
				CheckSpamResult.class);
		log.info("Result spam-check: " + result);

		return result;
	}

	@Override
	public GetCaptchaResult getCaptcha(CheckSpamResult checkSpamResult,
			String yandexKey) throws Exception {
		// http://cleanweb-api.yandex.ru/1.0/get-captcha?
		// key=cw.1.1.20110707T172051Z.faf547ce44f3d10b.d7e3028845ea04f56c38f7eef90999f765dd0d1f
		// &id=%23%23133396837300000%5B%5D

		String url = "http://cleanweb-api.yandex.ru/1.0/get-captcha";
		String key = yandexKey;
		String id = "";
		if (checkSpamResult!=null) {
			id = checkSpamResult.getId();
		}

		String query = String.format("key=%s&id=%s", key, id);

		GetCaptchaResult result = unmarshalFromUrl(url, query, false,
				GetCaptchaResult.class);
		log.info("Result spam-check: " + result);

		return result;
	}

	@Override
	public boolean checkCaptcha(CheckCaptcha checkCaptcha, String yandexKey)
			throws Exception {
		// http://cleanweb-api.yandex.ru/1.0/check-captcha?
		// key=cw.1.1.20110707T172051Z.faf547ce44f3d10b.d7e3028845ea04f56c38f7eef90999f765dd0d1f
		// &id=%23%23133396837300000%5B%5D
		// &captcha=308JR213_g_JSaE76RvWQ3R63cK4mc8N
		// &value=3234

		String url = "http://cleanweb-api.yandex.ru/1.0/check-captcha";
		String key = yandexKey;
		String id = checkCaptcha.getCheckSpamId();
		String captcha = checkCaptcha.getCaptchaKey();
		String value = checkCaptcha.getCaptchaText();

		String query = String.format("key=%s&id=%s&captcha=%s&value=%s", key,
				id, captcha, value);
		
		CheckCaptchaResult result = unmarshalFromUrl(url, query, false,
				CheckCaptchaResult.class);
		log.info("Result check-captcha: " + result);

		return result.isValid();
	}

}
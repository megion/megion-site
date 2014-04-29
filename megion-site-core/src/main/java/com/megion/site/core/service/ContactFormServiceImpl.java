package com.megion.site.core.service;

import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.module.blossom.dialog.TabBuilder;
import info.magnolia.module.mail.MailModule;
import info.magnolia.module.mail.MgnlMailFactory;
import info.magnolia.module.mail.handlers.MgnlMailHandler;
import info.magnolia.module.mail.templates.MgnlEmail;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.megion.site.core.enums.TabNumber;
import com.megion.site.core.exception.MegionsiteException;
import com.megion.site.core.model.ContactForm;
import com.megion.site.core.model.ContactFormNotification;
import com.megion.site.core.model.yandex.cleanweb.CheckCaptchaResult;
import com.megion.site.core.model.yandex.cleanweb.CheckSpamResult;
import com.megion.site.core.model.yandex.cleanweb.GetCaptchaResult;

@Service
public class ContactFormServiceImpl implements ContactFormService {

	private static final Logger log = LoggerFactory
			.getLogger(ContactFormServiceImpl.class);

	@Autowired
	private TemplatingService templatingService;

	@Override
	public void sendMail(ContactFormNotification contactFormNotification,
			ContactForm contactForm) throws Exception {
		@SuppressWarnings("deprecation")
		MgnlMailFactory mailFactory = MailModule.getInstance().getFactory();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("notification", contactFormNotification);

		MgnlEmail mail = mailFactory.getEmailFromTemplate(
				"contactFormNotification", params);
		if (mail == null) {
			throw new MegionsiteException(
					"Mail template for id [contactFormNotification] not found");
		}

		mail.setToList(contactForm.getToEmailList());
		if (contactForm.isHasFromEmail()) {
			mail.setFrom(contactForm.getFromEmail());
		} else {
			mail.setFrom(contactFormNotification.getEmail());
		}
		if (contactForm.isHasEmailSubject()) {
			mail.setSubject(contactForm.getEmailSubject());
		} else {
			mail.setSubject(contactFormNotification.getMessageTitle());
		}
		mail.setBodyFromResourceFile();

		MgnlMailHandler mmh = mailFactory.getEmailHandler();
		mmh.prepareAndSendMail(mail);
	}

	@Override
	public void addContactFormDialogControls(TabBuilder tabBuilder,
			TabNumber tabNumber) {
		if (TabNumber.FIRST.equals(tabNumber)) {
			tabBuilder.addEdit("title", "Заголовок формы", "");
			tabBuilder
					.addEdit("fromEmail", "От",
							"Email адреса отправителя. Если оставить пустым тогда назначится с формы");
			tabBuilder.addEdit("toEmailList", "Кому",
					"Email адреса для отправки разделенные через ';'");
			tabBuilder
					.addEdit("emailSubject", "Тема",
							"Тема письма. Если оставить пустым тогда назначится с формы");
			tabBuilder
					.addEdit(
							"yandexKey",
							"Ключ yandex",
							"Ключ API yandex для проверки Captcha. Подробнее http://api.yandex.ru/cleanweb/")
					.setRequired(true);
		} else {
			tabBuilder.addEdit("successTitle",
					"Заголовок успешности отправки сообщения", "");
			tabBuilder.addFckEditor("successText",
					"Текст успешности отправки сообщения", "");
		}
	}

	@Override
	public ContactForm getContactForm(Node contactFormComponent) {
		ContactForm dialogData = new ContactForm();
		dialogData.setTitle(PropertyUtil.getString(contactFormComponent,
				"title"));
		dialogData.setFromEmail(PropertyUtil.getString(contactFormComponent,
				"fromEmail"));
		dialogData.setToEmailList(PropertyUtil.getString(contactFormComponent,
				"toEmailList"));
		dialogData.setEmailSubject(PropertyUtil.getString(contactFormComponent,
				"emailSubject"));
		dialogData.setSuccessTitle(PropertyUtil.getString(contactFormComponent,
				"successTitle"));

		dialogData.setSuccessText(templatingService.getNodePropertyAsHtml(
				contactFormComponent, "successText"));

		dialogData.setYandexKey(PropertyUtil.getString(contactFormComponent,
				"yandexKey"));

		return dialogData;
	}

	@SuppressWarnings("unchecked")
	private <T> T unmarshalFromUrl(URL url, Class<T> clazz) throws IOException,
			JAXBException, XMLStreamException, SAXException,
			ParserConfigurationException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept-Charset", "UTF-8");
		connection.setUseCaches(false);
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
	public CheckSpamResult checkSpam(
			ContactFormNotification contactFormNotification, String yandexKey,
			HttpServletRequest request) throws Exception {
		// http://cleanweb-api.yandex.ru/1.0/check-spam?
		// key=cw.1.1.20110707T172051Z.faf547ce44f3d10b.d7e3028845ea04f56c38f7eef90999f765dd0d1f
		// &ip=10.178.33.2
		// &name=Vasia
		// &subject-plain=%D0%9E+%D1%81%D0%BF%D0%B0%D0%BC%D0%B5
		// &body-plain=%D0%AF+%D0%BD%D0%B5+%D1%81%D0%BF%D0%B0%D0%BC%D0%B5%D1%80

		String url = "http://cleanweb-api.yandex.ru/1.0/check-spam";

		String key = yandexKey;// "cw.1.1.20131002T132753Z.f7f7645e8647844e.10272f5f0e69ae92921779e0e15563a8683d139f";
		String ip = request.getRemoteAddr();

		String name = contactFormNotification.getName() == null ? ""
				: contactFormNotification.getName();
		String subjectPlain = contactFormNotification.getMessageTitle() == null ? ""
				: contactFormNotification.getMessageTitle();
		String bodyPlain = contactFormNotification.getMessage() == null ? ""
				: contactFormNotification.getMessage();

		String charset = "UTF-8";
		String query = String.format(
				"key=%s&ip=%s&name=%s&subject-plain=%s&body-plain=%s", key, ip,
				URLEncoder.encode(name, charset),
				URLEncoder.encode(subjectPlain, charset),
				URLEncoder.encode(bodyPlain, charset));

		String fullUrl = url + "?" + query;
		log.info("Send spam check information to yandex: " + fullUrl);
		CheckSpamResult result = unmarshalFromUrl(new URL(fullUrl),
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
		String key = yandexKey;// "cw.1.1.20131002T132753Z.f7f7645e8647844e.10272f5f0e69ae92921779e0e15563a8683d139f";
		String id = checkSpamResult.getId();

		String query = String.format("key=%s&id=%s", key, id);

		String fullUrl = url + "?" + query;
		log.info("Send get-captcha information to yandex: " + fullUrl);
		GetCaptchaResult result = unmarshalFromUrl(new URL(fullUrl),
				GetCaptchaResult.class);
		log.info("Result spam-check: " + result);

		return result;
	}

	@Override
	public boolean checkCaptcha(
			ContactFormNotification contactFormNotification, String yandexKey)
			throws Exception {
		// http://cleanweb-api.yandex.ru/1.0/check-captcha?
		// key=cw.1.1.20110707T172051Z.faf547ce44f3d10b.d7e3028845ea04f56c38f7eef90999f765dd0d1f
		// &id=%23%23133396837300000%5B%5D
		// &captcha=308JR213_g_JSaE76RvWQ3R63cK4mc8N
		// &value=3234

		String url = "http://cleanweb-api.yandex.ru/1.0/check-captcha";
		String key = yandexKey;// "cw.1.1.20131002T132753Z.f7f7645e8647844e.10272f5f0e69ae92921779e0e15563a8683d139f";
		String id = contactFormNotification.getCheckSpamId();
		String captcha = contactFormNotification.getCaptchaKey();
		String value = contactFormNotification.getCaptchaText();

		String query = String.format("key=%s&id=%s&captcha=%s&value=%s", key,
				id, captcha, value);

		String fullUrl = url + "?" + query;
		log.info("Send check-captcha information to yandex: " + fullUrl);
		CheckCaptchaResult result = unmarshalFromUrl(new URL(fullUrl),
				CheckCaptchaResult.class);
		log.info("Result check-captcha: " + result);

		return result.isValid();
	}

}
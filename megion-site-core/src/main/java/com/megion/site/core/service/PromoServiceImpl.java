package com.megion.site.core.service;

import info.magnolia.cms.core.MgnlNodeType;
import info.magnolia.jcr.util.MetaDataUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.module.blossom.dialog.TabBuilder;
import info.magnolia.module.templatingkit.dam.Asset;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megion.site.core.model.Promo;
import com.megion.site.core.model.TextPromo;
import com.megion.site.core.util.JcrNodeUtils;

@Service
public class PromoServiceImpl implements PromoService {

	@Autowired
	private TemplatingService templatingService;
	@Autowired
	private TextTagsService textTagsService;
	@Autowired
	private DialogService dialogService;

	@Override
	public Promo getPromo(Node promoComponent) throws PathNotFoundException,
			RepositoryException {
		String promoLink = PropertyUtil.getString(promoComponent, "promoLink");
		Asset promoImage = templatingService.getNodePropertyAsImage(
				promoComponent, "promoImage");
		Node promoPage = promoComponent.getSession().getNode(promoLink);

		return new Promo(JcrNodeUtils.getURL(promoPage), promoImage);
	}

	@Override
	public void addPromoDialogControls(TabBuilder tabBuilder) {
		tabBuilder.addLink("promoLink", "Страница c описанием",
				"Ссылка на страницу с описанием рекламы");
		dialogService.addDAM(tabBuilder, "promoImage",
				"Картинка с рекламой", "");
	}

	@Override
	public void generatePromoComponents(Node areaNode)
			throws RepositoryException {
		/*
		 * if (!areaNode.hasNode("autoPromo1")) { // banner b-consult Node
		 * promo1 = areaNode.addNode("autoPromo1", MgnlNodeType.NT_COMPONENT);
		 * MetaDataUtil.getMetaData(promo1).setTemplate(
		 * "megion-site:components/promo"); promo1.setProperty("promoLink",
		 * "/home/media-center/materials/centre-consulting");
		 * 
		 * String repository =
		 * info.magnolia.module.dms.DMSModule.getInstance().getRepository();
		 * Node imageLocationNode = SessionUtil.getNode(repository,
		 * "/megion-docs/b-consult"); if (imageLocationNode!=null) {
		 * promo1.setProperty("promoImage", "dms");
		 * promo1.setProperty("promoImageDmsUUID",
		 * imageLocationNode.getIdentifier()); } areaNode.getSession().save(); }
		 * 
		 * if (!areaNode.hasNode("autoPromo2")) { // banner b-test Node promo =
		 * areaNode.addNode("autoPromo2", MgnlNodeType.NT_COMPONENT);
		 * MetaDataUtil.getMetaData(promo).setTemplate(
		 * "megion-site:components/promo"); promo.setProperty("promoLink",
		 * "/home/media-center/materials/testing");
		 * 
		 * String repository =
		 * info.magnolia.module.dms.DMSModule.getInstance().getRepository();
		 * Node imageLocationNode = SessionUtil.getNode(repository,
		 * "/megion-docs/b-test"); if (imageLocationNode!=null) {
		 * promo.setProperty("promoImage", "dms");
		 * promo.setProperty("promoImageDmsUUID",
		 * imageLocationNode.getIdentifier()); } areaNode.getSession().save(); }
		 * 
		 * if (!areaNode.hasNode("autoPromoFeedback")) { // banner feedback Node
		 * promo = areaNode.addNode("autoPromoFeedback",
		 * MgnlNodeType.NT_COMPONENT);
		 * MetaDataUtil.getMetaData(promo).setTemplate(
		 * "megion-site:components/promo"); promo.setProperty("promoLink",
		 * "/home/feedback");
		 * 
		 * String repository =
		 * info.magnolia.module.dms.DMSModule.getInstance().getRepository();
		 * Node imageLocationNode = SessionUtil.getNode(repository,
		 * "/megion-docs/b-ask"); if (imageLocationNode!=null) {
		 * promo.setProperty("promoImage", "dms");
		 * promo.setProperty("promoImageDmsUUID",
		 * imageLocationNode.getIdentifier()); } areaNode.getSession().save(); }
		 */

		// articalService.generateRecentNewsOverviewComponent(areaNode,
		// "autoRecentNewsOverview");
		// textTagsService.generateTextTagsCloudComponent(areaNode,
		// "autoTextTagsCloudTag");

	}

	@Override
	public TextPromo getTextPromo(Node promoComponent)
			throws RepositoryException {
		String promoLink = PropertyUtil.getString(promoComponent, "promoLink");
		String promoExternalUrl = PropertyUtil.getString(promoComponent,
				"promoExternalUrl");
		boolean isUseExternalURL = false;
		String internalUrl = null;
		if (StringUtils.isNotBlank(promoExternalUrl)) {
			isUseExternalURL = true;
		} else {
			Node promoPage = promoComponent.getSession().getNode(promoLink);
			internalUrl = JcrNodeUtils.getURL(promoPage);
		}

		String promoText = templatingService.getNodePropertyAsHtml(
				promoComponent, "promoText");

		return new TextPromo(internalUrl, promoExternalUrl, isUseExternalURL,
				promoText);
	}

	@Override
	public void addTextPromoDialogControls(TabBuilder tabBuilder) {
		tabBuilder.addLink("promoLink", "Внутренняя сслыка",
				"Ссылка на страницу с описанием");
		tabBuilder.addEdit("promoExternalUrl", "Внешняя ссылка",
				"Внешняя ссылка имеет приоритет над внутренней");
		dialogService.addFckEditor(tabBuilder, "promoText", "Текст баннера", "");
	}

	@Override
	public void generateTextPromoComponents(Node areaNode)
			throws RepositoryException {
		/*
		 * if (areaNode.hasNode("autoTextPromoCenterBI")) {
		 * areaNode.getNode("autoTextPromoCenterBI").remove(); } if
		 * (areaNode.hasNode("autoTextPromoCenterIBM")) {
		 * areaNode.getNode("autoTextPromoCenterIBM").remove(); } if
		 * (areaNode.hasNode("autoTextPromoCenterLotus")) {
		 * areaNode.getNode("autoTextPromoCenterLotus").remove(); } if
		 * (areaNode.hasNode("autoTextPromoCenterMicrosoft")) {
		 * areaNode.getNode("autoTextPromoCenterMicrosoft").remove(); } if
		 * (areaNode.hasNode("autoTextPromoCenterTransportSystem")) {
		 * areaNode.getNode("autoTextPromoCenterTransportSystem").remove(); } if
		 * (areaNode.hasNode("autoTextPromoOpenSource")) {
		 * areaNode.getNode("autoTextPromoOpenSource").remove(); } if
		 * (areaNode.hasNode("autoTextPromoContactForm")) {
		 * areaNode.getNode("autoTextPromoContactForm").remove(); }
		 */

		if (!areaNode.hasNode("autoTextPromoCenterBI")) {
			// banner центр компетенции BI
			Node promo = areaNode.addNode("autoTextPromoCenterBI",
					MgnlNodeType.NT_COMPONENT);
			MetaDataUtil.getMetaData(promo).setTemplate(
					"megion-site:components/textPromo");
			promo.setProperty("promoLink",
					"/home/development-areas/business-analyst");
			promo.setProperty("promoText", "Центр компетенции BI");
			areaNode.getSession().save();
		}
		if (!areaNode.hasNode("autoTextPromoCenterIBM")) {
			// Центр компетенции IBM (Ведет на страницу вендора IBM на нашем
			// сайте)
			Node promo = areaNode.addNode("autoTextPromoCenterIBM",
					MgnlNodeType.NT_COMPONENT);
			MetaDataUtil.getMetaData(promo).setTemplate(
					"megion-site:components/textPromo");
			promo.setProperty("promoLink", "/home/vendors/ibm");
			promo.setProperty("promoText", "Центр компетенции IBM");
			areaNode.getSession().save();
		}
		if (!areaNode.hasNode("autoTextPromoCenterLotus")) {
			// Центр компетенции Lotus
			Node promo = areaNode.addNode("autoTextPromoCenterLotus",
					MgnlNodeType.NT_COMPONENT);
			MetaDataUtil.getMetaData(promo).setTemplate(
					"megion-site:components/textPromo");
			promo.setProperty("promoLink", "/home/vendors/lotus");
			promo.setProperty("promoText", "Центр компетенции Lotus");
			areaNode.getSession().save();
		}
		if (!areaNode.hasNode("autoTextPromoCenterMicrosoft")) {
			// Центр компетенции Microsoft
			Node promo = areaNode.addNode("autoTextPromoCenterMicrosoft",
					MgnlNodeType.NT_COMPONENT);
			MetaDataUtil.getMetaData(promo).setTemplate(
					"megion-site:components/textPromo");
			promo.setProperty("promoLink", "/home/vendors/microsoft");
			promo.setProperty("promoText", "Центр компетенции Microsoft");
			areaNode.getSession().save();
		}
		if (!areaNode.hasNode("autoTextPromoCenterTransportSystem")) {
			// Центр транспортных систем
			Node promo = areaNode.addNode("autoTextPromoCenterTransportSystem",
					MgnlNodeType.NT_COMPONENT);
			MetaDataUtil.getMetaData(promo).setTemplate(
					"megion-site:components/textPromo");
			promo.setProperty("promoLink",
					"/home/development-areas/transport-system");
			promo.setProperty("promoText", "Центр транспортных систем");
			areaNode.getSession().save();
		}
		if (!areaNode.hasNode("autoTextPromoOpenSource")) {
			// Свободное программное обеспечение
			Node promo = areaNode.addNode("autoTextPromoOpenSource",
					MgnlNodeType.NT_COMPONENT);
			MetaDataUtil.getMetaData(promo).setTemplate(
					"megion-site:components/textPromo");
			promo.setProperty("promoLink", "/home/open-source");
			promo.setProperty("promoText", "Свободное ПО");
			areaNode.getSession().save();
		}
		if (!areaNode.hasNode("autoTextPromoContactForm")) {
			// Задать вопрос
			Node promo = areaNode.addNode("autoTextPromoContactForm",
					MgnlNodeType.NT_COMPONENT);
			MetaDataUtil.getMetaData(promo).setTemplate(
					"megion-site:components/textPromo");
			promo.setProperty("promoLink", "/home/contact-form");
			promo.setProperty("promoText", "Задать вопрос");
			areaNode.getSession().save();
		}

	}

}
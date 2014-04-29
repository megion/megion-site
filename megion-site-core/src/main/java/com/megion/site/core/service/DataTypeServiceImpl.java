package com.megion.site.core.service;

import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.module.blossom.dialog.DialogCreationContext;
import info.magnolia.module.blossom.dialog.TabBuilder;
import info.magnolia.module.categorization.controls.CategorizationUUIDMultiSelect;
import info.magnolia.module.data.DataModule;
import info.magnolia.objectfactory.Classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megion.site.core.model.datatype.DataType;
import com.megion.site.core.model.datatype.DataTypeValue;
import com.megion.site.core.provider.DataTypeProvider;

@Service
public class DataTypeServiceImpl implements DataTypeService {

	private static final Logger log = LoggerFactory
			.getLogger(DataTypeServiceImpl.class);

	@Autowired
	private TemplatingService templatingService;

	@Autowired
	private DataTypeProvider dataTypeProvider;

	@Override
	public List<Node> getNodePropertyAsDataTypes(Node node, String propName)
			throws ValueFormatException, IllegalStateException,
			RepositoryException {
		List<Node> res = new ArrayList<Node>();

		if (node.hasProperty(propName)) {
			Property property = PropertyUtil.getPropertyOrNull(node, propName);
			Value[] categories = property.getValues();
			for (Value value : categories) {
				Node categoryNode = NodeUtil.getNodeByIdentifier(
						DataModule.getRepository(), value.getString());
				if (categoryNode != null) {
					res.add(categoryNode);
				}
			}
			this.getSortedList(node, res);
		}

		return res;
	}

	@Override
	public Set<DataTypeValue> getNodeDataTypeValues(Node node, String propName, DataType dataType)
			throws ValueFormatException, IllegalStateException,
			RepositoryException {
		List<Node> dataTypeValueNodes = getNodePropertyAsDataTypes(node, propName);
		Set<DataTypeValue> values = new LinkedHashSet<DataTypeValue>();

		for (Node nodeVal : dataTypeValueNodes) {
			DataTypeValue val = dataTypeProvider.createDataTypeValue(nodeVal, dataType);
			values.add(val);
		}

		return values;
	}

	@Override
	public Map<String, DataTypeValue> getAllDataTypeValuesAsMap(DataType dataType)
			throws LoginException, RepositoryException {
		Session session = MgnlContext.getJCRSession(DataModule.getRepository());
		Node parrentNode = session.getNode(dataType.getRootPath());
		Iterable<Node> childNodes = NodeUtil.getNodes(parrentNode);
		int i = 0;
		Map<String, DataTypeValue> tags = new LinkedHashMap<String, DataTypeValue>();
		for (Node child : childNodes) {
			DataTypeValue val = dataTypeProvider.createDataTypeValue(child, dataType);
			val.setOrder(i);
			i++;
			tags.put(val.getId(), val);
		}
		return tags;
	}

	@Override
	public DataType findDataTypeByTreeName(String treeName) {
		Map<String, DataType> typesMap = dataTypeProvider
				.getDataTypeMapByTreeName();
		return typesMap.get(treeName);
	}

	@Override
	public void addDataTypeMultiSelectControl(TabBuilder tab,
			DataType dataType, String name, String label, String description)
			throws RepositoryException {
		addDataMultiSelectControl(tab, name, label, description,
				dataType.getTreeName(), dataType.getSaveHandler()
						.getCanonicalName());
	}
	
	private List<Node> getSortedList(Node node, List<Node> temp)
			throws ValueFormatException, RepositoryException {

		String order = PropertyUtil.getString(node, "order");
		if (!StringUtils.isEmpty(order)) {
			// first criteria always by name then whatever
			Collections.sort(temp, orderBy("name"));
			Collections.reverse(temp);
			if (!order.equals("name")) {
				Collections.sort(temp, orderBy(order));
			}

		}
		return temp;
	}

	private Comparator<Node> orderBy(final String value) {
		return new Comparator<Node>() {
			@Override
			public int compare(Node m0, Node m1) {
				try {
					return PropertyUtil
							.getPropertyOrNull(m1, value)
							.getString()
							.compareToIgnoreCase(
									PropertyUtil.getPropertyOrNull(m0, value)
											.getString());
				} catch (ValueFormatException e) {
					log.debug("No order property define", e);
					return 0;
				} catch (RepositoryException e) {
					log.debug("No order property define", e);
					return 0;
				}
			}
		};

	}
	
	private void addDataMultiSelectControl(TabBuilder tab, String name,
			String label, String description, String tree,
			String saveHandlerClass) throws RepositoryException {
		CategorizationUUIDMultiSelect control = Classes.getClassFactory()
				.newInstance(CategorizationUUIDMultiSelect.class);

		DialogCreationContext context = tab.getContext();
		control.init(context.getRequest(), context.getResponse(),
				context.getWebsiteNode(), null);

		control.setName(name);
		control.setLabel(label);
		control.setDescription(description);

		control.setConfig("tree", tree);
		control.setConfig("saveHandler", saveHandlerClass);
		control.setConfig("type", PropertyType.STRING);
		control.setConfig("repository", DataModule.getRepository());
		control.setConfig("i18nBasename",
				"info.magnolia.module.categorization.messages");
		tab.getTab().addSub(control);
	}

}
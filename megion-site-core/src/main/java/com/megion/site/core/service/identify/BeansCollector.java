package com.megion.site.core.service.identify;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;

public class BeansCollector<T extends Identifiable> implements IdentifiableCollector<T> {
	
	private final Map<String, T> beansByIdentifier;
	private final ApplicationContext context;
	private final Class<T> clazz;
	
	public BeansCollector(ApplicationContext context, Class<T> clazz) {
		this.beansByIdentifier = new HashMap<String, T>();
		this.context = context;
		this.clazz = clazz;
	}

	@Override
	public T findIdentifiable(String identifier) {
		if (beansByIdentifier.containsKey(identifier)) {
			return beansByIdentifier.get(identifier);
		} else {
			Map<String, T> beansOfType = context.getBeansOfType(clazz);
			T bean = beansOfType.get(identifier);
			beansByIdentifier.put(identifier, bean);
			return bean;
		}
	}

}

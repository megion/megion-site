package com.megion.site.core.service.identify;

public interface IdentifiableCollector<T extends Identifiable> {

	T findIdentifiable(String identifier);

}

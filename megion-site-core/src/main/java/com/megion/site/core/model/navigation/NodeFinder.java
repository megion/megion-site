package com.megion.site.core.model.navigation;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

public interface NodeFinder {

	boolean isFind(Node node) throws RepositoryException;
}

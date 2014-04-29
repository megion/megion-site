package com.megion.site.core.model.navigation;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

public interface NodeProcessor {

	void processNode(Node node) throws RepositoryException;
}

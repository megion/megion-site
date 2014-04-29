package info.magnolia.module.blossom.autogeneration;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

public interface AutoGeneratingArea {
	 
    void generate(Node areaNode) throws RepositoryException;
}
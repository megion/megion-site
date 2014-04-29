package com.megion.site.core.util;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JcrNodeUtils {
	
	private static final Logger log = LoggerFactory.getLogger(JcrNodeUtils.class);
	
	public static String getURL(Node node) throws RepositoryException {
		return node.getPath() + ".html";
	}
	
	public static Double getDouble(Node node, String name, Double defaultValue) {
        try {
            if (node.hasProperty(name)) {
                return node.getProperty(name).getDouble();
            }
        } catch (RepositoryException e) {
            log.error("can't read value '" + name + "' of the Node '" + node.toString() + "' will return default value", e);
        }
        return defaultValue;
    }
	
	public static Integer getInteger(Node node, String name, Integer defaultValue) {
        try {
            if (node.hasProperty(name)) {
                return (int)node.getProperty(name).getLong();
            }
        } catch (RepositoryException e) {
            log.error("can't read value '" + name + "' of the Node '" + node.toString() + "' will return default value", e);
        }
        return defaultValue;
    }
	
	public static Long getLong(Node node, String name, Long defaultValue) {
        try {
            if (node.hasProperty(name)) {
                return node.getProperty(name).getLong();
            }
        } catch (RepositoryException e) {
            log.error("can't read value '" + name + "' of the Node '" + node.toString() + "' will return default value", e);
        }
        return defaultValue;
    }
}

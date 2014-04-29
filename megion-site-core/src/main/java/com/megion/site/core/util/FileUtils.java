package com.megion.site.core.util;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.springframework.web.context.support.ServletContextResource;

public class FileUtils {

	public static String readClassPathResource(String resourcePath) {
		InputStream input = FileUtils.class.getClassLoader()
				.getResourceAsStream(resourcePath);
		if (input == null) {
			throw new IllegalArgumentException("В classPath не найден ресурс: "
					+ resourcePath);
		}
		String result;
		try {
			result = IOUtils.toString(input);
		} catch (IOException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				throw new IllegalStateException(e.getMessage(), e);
			}
		}
		return result;
	}

	public static String readWebResource(ServletContext servletContext,
			String resourcePath) {
		ServletContextResource resource = new ServletContextResource(
				servletContext, resourcePath);
		String result;
		InputStream input = null;
		try {
			input = resource.getInputStream();
			if (input == null) {
				throw new IllegalArgumentException(
						"Не найден веб ресурс: " + resourcePath);
			}

			result = IOUtils.toString(input);
		} catch (IOException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				throw new IllegalStateException(e.getMessage(), e);
			}
		}
		return result;
	}
}

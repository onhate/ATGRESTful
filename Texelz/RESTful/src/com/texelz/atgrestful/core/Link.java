package com.texelz.atgrestful.core;

import java.util.LinkedHashMap;

import javax.ws.rs.core.UriInfo;

/**
 * All resources are a potential Link ;)
 * 
 * @author Onhate
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class Link extends LinkedHashMap {

	private static final long serialVersionUID = 6138089806561712431L;

	public static final String PATH_SEPARATOR = "/";

	public Link(UriInfo info, Entity entity) {
		this(getFullyQualifiedContextPath(info), entity);
	}

	public Link(String fqBasePath, Entity entity) {
		String href = createHref(fqBasePath, entity);
		put("href", href);
	}

	public Link(UriInfo info, String subPath) {
		this(getFullyQualifiedContextPath(info), subPath);
	}

	public Link(String fqBasePath, String subPath) {
		String href = fqBasePath + subPath;
		put("href", href);
	}

	protected static String getFullyQualifiedContextPath(UriInfo info) {
		String fq = info.getBaseUri().toString();
		if (fq.endsWith("/")) {
			return fq.substring(0, fq.length() - 1);
		}
		return fq;
	}

	protected String createHref(String fqBasePath, Entity entity) {
		StringBuilder sb = new StringBuilder(fqBasePath);
		String path = ResourcePath.forClass(entity.getClass());
		sb.append(path).append(PATH_SEPARATOR).append(entity.getId());
		return sb.toString();
	}

	public String getHref() {
		return (String) get("href");
	}

}

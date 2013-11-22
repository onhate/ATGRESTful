package com.texelz.atgrestful.core;

import java.util.Collection;
import java.util.Collections;

import javax.ws.rs.core.UriInfo;

/**
 * An utility class when loading a Collection of itens, just to not load all
 * itens, and paginate on them
 * 
 * @author Onhate
 * 
 */
public class CollectionResource extends Link {

	private static final long serialVersionUID = 2301157844766660273L;
	public static final int DEFAULT_LIMIT = 25;

	public CollectionResource(UriInfo info, String subPath, Collection<?> c) {
		this(info, subPath, c, 0, getLimit(c));
	}

	@SuppressWarnings("unchecked")
	public CollectionResource(UriInfo info, String subPath, Collection<?> c, int offset, int limit) {
		super(info, subPath);
		put("offset", offset);
		put("limit", getLimit(limit));
		put("items", c != null ? c : Collections.emptyList());
	}

	private static int getLimit(Collection<?> c) {
		return getLimit(c != null ? c.size() : 0);
	}

	private static int getLimit(int limit) {
		return Math.max(DEFAULT_LIMIT, limit);
	}

}

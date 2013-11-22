package com.texelz.atgrestful.core;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

/**
 * Some basic operations for a good RESTful service, it's optional extending
 * this class for a ATG RESTful service
 * 
 * @author Onhate
 * 
 */
public abstract class BaseRESTService {

	protected Response created(Link resource) {
		String href = (String) resource.get("href");
		URI uri = URI.create(href);
		return Response.created(uri).entity(resource).build();
	}

	protected Map<String, Object> createResult() {
		return new HashMap<String, Object>();
	}

	protected Map<String, Object> createResult(String key, Object value) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put(key, value);
		return result;
	}

}

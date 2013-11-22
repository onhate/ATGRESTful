package com.texelz.atgrestful.core;

import javax.ws.rs.Path;

/**
 * How to load the path of a Resource URL of a (Class)? Using {@link Path}
 * annotation, of course!
 * 
 * @author Onhate
 * 
 */
public final class ResourcePath {

	/**
	 * Given a Class, returns the resource path on {@link Path} value
	 * 
	 * @param clazz
	 * @return
	 */
	public static String forClass(Class<? extends Entity> clazz) {
		String result = null;
		Path path = clazz.getAnnotation(Path.class);
		result = path.value();
		return result;
	}
}

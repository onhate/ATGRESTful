package com.texelz.atgrestful.lang;

import java.io.InputStream;
import java.lang.reflect.Constructor;

public class ClassUtils {

	/**
	 * @since 1.0
	 */
	private static final ClassLoaderAccessor THREAD_CL_ACCESSOR = new ExceptionIgnoringAccessor() {
		@Override
		protected ClassLoader doGetClassLoader() throws Throwable {
			return Thread.currentThread().getContextClassLoader();
		}
	};

	/**
	 * @since 1.0
	 */
	private static final ClassLoaderAccessor CLASS_CL_ACCESSOR = new ExceptionIgnoringAccessor() {
		@Override
		protected ClassLoader doGetClassLoader() throws Throwable {
			return ClassUtils.class.getClassLoader();
		}
	};

	/**
	 * @since 1.0
	 */
	private static final ClassLoaderAccessor SYSTEM_CL_ACCESSOR = new ExceptionIgnoringAccessor() {
		@Override
		protected ClassLoader doGetClassLoader() throws Throwable {
			return ClassLoader.getSystemClassLoader();
		}
	};

	/**
	 * Returns the specified resource by checking the current thread's
	 * {@link Thread#getContextClassLoader() context class loader}, then the
	 * current ClassLoader (<code>ClassUtils.class.getClassLoader()</code>),
	 * then the system/application ClassLoader (
	 * <code>ClassLoader.getSystemClassLoader()</code>, in that order, using
	 * {@link ClassLoader#getResourceAsStream(String) getResourceAsStream(name)}
	 * .
	 * 
	 * @param name
	 *            the name of the resource to acquire from the classloader(s).
	 * @return the InputStream of the resource found, or <code>null</code> if
	 *         the resource cannot be found from any of the three mentioned
	 *         ClassLoaders.
	 * @since 0.9
	 */
	public static InputStream getResourceAsStream(String name) {
		InputStream is = THREAD_CL_ACCESSOR.getResourceStream(name);
		if (is == null) {
			is = CLASS_CL_ACCESSOR.getResourceStream(name);
		}
		if (is == null) {
			is = SYSTEM_CL_ACCESSOR.getResourceStream(name);
		}
		return is;
	}

	/**
	 * Attempts to load the specified class name from the current thread's
	 * {@link Thread#getContextClassLoader() context class loader}, then the
	 * current ClassLoader (<code>ClassUtils.class.getClassLoader()</code>),
	 * then the system/application ClassLoader (
	 * <code>ClassLoader.getSystemClassLoader()</code>, in that order. If any of
	 * them cannot locate the specified class, an
	 * <code>UnknownClassException</code> is thrown (our RuntimeException
	 * equivalent of the JRE's <code>ClassNotFoundException</code>.
	 * 
	 * @param fqcn
	 *            the fully qualified class name to load
	 * @return the located class
	 */
	@SuppressWarnings("rawtypes")
	public static Class forName(String fqcn) throws RuntimeException {
		Class clazz = THREAD_CL_ACCESSOR.loadClass(fqcn);
		if (clazz == null) {
			clazz = CLASS_CL_ACCESSOR.loadClass(fqcn);
		}
		if (clazz == null) {
			clazz = SYSTEM_CL_ACCESSOR.loadClass(fqcn);
		}

		if (clazz == null) {
			String msg = "Unable to load class named ["
					+ fqcn
					+ "] from the thread context, current, or "
					+ "system/application ClassLoaders.  All heuristics have been exhausted.  Class could not be found.";
			throw new RuntimeException(msg);
		}

		return clazz;
	}

	public static boolean isAvailable(String fullyQualifiedClassName) {
		try {
			forName(fullyQualifiedClassName);
			return true;
		} catch (RuntimeException e) {
			return false;
		}
	}

	public static Object newInstance(String fqcn) {
		return newInstance(forName(fqcn));
	}

	public static Object newInstance(String fqcn, Object... args) {
		return newInstance(forName(fqcn), args);
	}

	@SuppressWarnings("rawtypes")
	public static Object newInstance(Class clazz) {
		if (clazz == null) {
			String msg = "Class method parameter cannot be null.";
			throw new IllegalArgumentException(msg);
		}
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Unable to instantiate class [" + clazz.getName() + "]", e);
		}
	}

	@SuppressWarnings("rawtypes")
	public static Object newInstance(Class clazz, Object... args) {
		Class[] argTypes = new Class[args.length];
		for (int i = 0; i < args.length; i++) {
			argTypes[i] = args[i].getClass();
		}
		Constructor ctor = getConstructor(clazz, argTypes);
		return instantiate(ctor, args);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Constructor getConstructor(Class clazz, Class... argTypes) {
		try {
			return clazz.getConstructor(argTypes);
		} catch (NoSuchMethodException e) {
			throw new IllegalStateException(e);
		}

	}

	@SuppressWarnings("rawtypes")
	public static Object instantiate(Constructor ctor, Object... args) {
		try {
			return ctor.newInstance(args);
		} catch (Exception e) {
			String msg = "Unable to instantiate Permission instance with constructor [" + ctor + "]";
			throw new RuntimeException(msg, e);
		}
	}

	/**
	 * @since 1.0
	 */
	@SuppressWarnings("rawtypes")
	private static interface ClassLoaderAccessor {
		Class loadClass(String fqcn);

		InputStream getResourceStream(String name);
	}

	/**
	 * @since 1.0
	 */
	private static abstract class ExceptionIgnoringAccessor implements ClassLoaderAccessor {

		@SuppressWarnings("rawtypes")
		public Class loadClass(String fqcn) {
			Class clazz = null;
			ClassLoader cl = getClassLoader();
			if (cl != null) {
				try {
					clazz = cl.loadClass(fqcn);
				} catch (ClassNotFoundException e) {
				}
			}
			return clazz;
		}

		public InputStream getResourceStream(String name) {
			InputStream is = null;
			ClassLoader cl = getClassLoader();
			if (cl != null) {
				is = cl.getResourceAsStream(name);
			}
			return is;
		}

		protected final ClassLoader getClassLoader() {
			try {
				return doGetClassLoader();
			} catch (Throwable t) {
			}
			return null;
		}

		protected abstract ClassLoader doGetClassLoader() throws Throwable;
	}

}

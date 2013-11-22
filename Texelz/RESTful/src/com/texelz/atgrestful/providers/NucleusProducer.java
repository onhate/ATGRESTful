package com.texelz.atgrestful.providers;

import java.lang.reflect.Type;

import javax.ws.rs.ext.Provider;

import atg.servlet.DynamoHttpServletRequest;
import atg.servlet.ServletUtil;

import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;
import com.texelz.atgrestful.Nucleus;

/**
 * The {@link Nucleus} annotation provider. So, when you annotated your
 * property, this class will works on provide the component object
 * 
 * @author Onhate
 * 
 */
@Provider
public class NucleusProducer implements InjectableProvider<Nucleus, Type> {

	/**
	 * The class that will lookup the Nucleus component
	 * 
	 * @author Onhate
	 * 
	 */
	class NucleusInjectable implements Injectable<Object> {
		private Nucleus nucleus;

		public NucleusInjectable(Nucleus nucleus) {
			this.nucleus = nucleus;
		}

		/**
		 * Returns the component value ;)
		 */
		@Override
		public Object getValue() {
			DynamoHttpServletRequest request = ServletUtil.getCurrentRequest();
			String componentName = nucleus.value();
			Object result = request.resolveName(componentName);
			return result;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Injectable getInjectable(ComponentContext componentContext, final Nucleus nucleus, Type type) {
		return new NucleusInjectable(nucleus);
	}

	@Override
	public ComponentScope getScope() {
		return ComponentScope.PerRequest;
	}
}

package com.texelz.atgrestful;

import atg.nucleus.Nucleus;

import com.sun.jersey.api.core.PackagesResourceConfig;

/**
 * This class will load listed service classes on Oracle ATG component
 * /com/texelz/atgrestful/NucleusRestPackages
 * 
 * @author Onhate
 * @see com.sun.jersey.api.core.PackagesResourceConfig
 * 
 */
public class NucleusResourceConfig extends PackagesResourceConfig {

	private static final String NUCLEUS_PACKAGES_NAME = "/com/texelz/atgrestful/NucleusRestPackages";

	public NucleusResourceConfig() {
		super(getPackagesFromNucleus());
	}

	private static String[] getPackagesFromNucleus() {
		Nucleus nucleus = atg.nucleus.Nucleus.getGlobalNucleus();
		NucleusRestPackages restPackages = (NucleusRestPackages) nucleus.resolveName(NUCLEUS_PACKAGES_NAME);
		return restPackages.getPackages();
	}

}

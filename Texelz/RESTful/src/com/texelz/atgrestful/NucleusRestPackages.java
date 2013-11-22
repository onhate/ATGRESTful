package com.texelz.atgrestful;

/**
 * Only stores the packages names for {@link NucleusResourceConfig} loading on
 * Jersey framework
 * 
 * @author Onhate
 * 
 * @see NucleusResourceConfig
 * 
 */
public class NucleusRestPackages {

	private String[] packages;

	public void setPackages(String[] packages) {
		this.packages = packages;
	}

	public String[] getPackages() {
		return packages;
	}

}

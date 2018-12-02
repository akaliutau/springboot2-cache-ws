package org.microservice.configuration;

public class CacheSettings {

	private final Integer maxNumberOfKeys;

	public CacheSettings(Integer maxNumberOfKeys) {
		this.maxNumberOfKeys = maxNumberOfKeys;
	}

	public Integer getMaxNumberOfKeys() {
		return maxNumberOfKeys;
	}
	
	

}

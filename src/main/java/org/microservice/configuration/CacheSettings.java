package org.microservice.configuration;

/**
 * Used to hold the global settings for server 
 * @author Aliaksei Kaliutau
 * @since Version 1.0
 */
public class CacheSettings {

	// default values
	// number of unique keys in repository before they start to replace
	private Integer maxNumberOfKeys = 100;

	// expiration time for keys in ms
	private Long lifeTime = 300000L;

	public CacheSettings() {
	}

	public Integer getMaxNumberOfKeys() {
		return maxNumberOfKeys;
	}

	public Long getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(Long lifeTime) {
		this.lifeTime = lifeTime;
	}

	public void setMaxNumberOfKeys(Integer maxNumberOfKeys) {
		this.maxNumberOfKeys = maxNumberOfKeys;
	}

}

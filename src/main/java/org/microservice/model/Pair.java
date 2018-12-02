package org.microservice.model;

import java.io.Serializable;

public class Pair implements Serializable {

	private static final long serialVersionUID = 8537751618747741798L;

	private String key;
	private String value;

	public Pair() {
	}

	public Pair(String key) {
		this.key = key;
	}

	public Pair(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

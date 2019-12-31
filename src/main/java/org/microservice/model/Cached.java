package org.microservice.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
//import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "cache", uniqueConstraints = { @UniqueConstraint(columnNames = "id") }, indexes = {
		@Index(name = "IDX_1", columnList = "id,key") })
public class Cached {
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "key")
	private String key;

	@Column(name = "value")
	private String value;

	@Column(name = "timestamp")
	private Long timestamp;

	public Cached() {
		// super();
	}

	public Cached(Long id, String key, Long timestamp, String value) {
		super();
		this.id = id;
		this.key = key;
		this.value = value;
		this.timestamp = timestamp;
	}

	public Cached(Long id, String key, String value) {
		super();
		this.id = id;
		this.key = key;
		this.value = value;
	}

	public Cached(String key, String value) {
		super();
		this.key = key;
		this.value = value;
		this.timestamp = Instant.now().toEpochMilli();
	}

	public void updateValue(String value) {
		this.value = value;
		this.timestamp = Instant.now().toEpochMilli();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

}

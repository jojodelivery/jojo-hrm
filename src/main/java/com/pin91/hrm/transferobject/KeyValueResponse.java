package com.pin91.hrm.transferobject;

public class KeyValueResponse {

	private String key;
	private String value;

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

	@Override
	public String toString() {
		return "ReferenceEntityTO [key=" + key + ", value=" + value + "]";
	}
}

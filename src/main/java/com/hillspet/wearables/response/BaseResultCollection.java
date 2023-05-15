package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseResultCollection {

	private int noOfElements;
	private long totalElements;
	private long searchElments;

	public int getNoOfElements() {
		return noOfElements;
	}

	public void setNoOfElements(int noOfElements) {
		this.noOfElements = noOfElements;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalRecords(long totalElements) {
		this.totalElements = totalElements;
	}
	
	public long getSearchElments() {
		return searchElments;
	}

	public void setSearchElments(long searchElments) {
		this.searchElments = searchElments;
	}

}

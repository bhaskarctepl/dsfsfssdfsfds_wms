package com.hillspet.wearables.dto;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class PointTrackingFilter extends BaseFilter {

	@ApiParam(name = "startDate", value = "Start Date is the first date component value of date range")
	@QueryParam("startDate")
	private String petId;

	public PointTrackingFilter() {

	}

	public PointTrackingFilter(String petId) {
		super();
		this.petId = petId;
	}

	public String getPetId() {
		return petId;
	}

	public void setPetId(String petId) {
		this.petId = petId;
	}

}

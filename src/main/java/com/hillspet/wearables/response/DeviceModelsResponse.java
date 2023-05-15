package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceModelsResponse {

	List<String> deviceTypeList;

	public List<String> getDeviceTypeList() {
		return deviceTypeList;
	}

	public void setDeviceTypeList(List<String> deviceTypeList) {
		this.deviceTypeList = deviceTypeList;
	}

}

package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EatingEnthusiasmScale {
	private Integer enthusiasmScaleId;
	private String enthusiasmScaleValue;

	public Integer getEnthusiasmScaleId() {
		return enthusiasmScaleId;
	}

	public void setEnthusiasmScaleId(Integer enthusiasmScaleId) {
		this.enthusiasmScaleId = enthusiasmScaleId;
	}

	public String getEnthusiasmScaleValue() {
		return enthusiasmScaleValue;
	}

	public void setEnthusiasmScaleValue(String enthusiasmScaleValue) {
		this.enthusiasmScaleValue = enthusiasmScaleValue;
	}

}

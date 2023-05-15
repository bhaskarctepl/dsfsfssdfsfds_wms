package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hillspet.wearables.dto.MaterialType;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MaterialTypesResponse {

	List<MaterialType> materialTypes;

	public List<MaterialType> getMaterialTypes() {
		return materialTypes;
	}

	public void setMaterialTypes(List<MaterialType> materialTypes) {
		this.materialTypes = materialTypes;
	}

}

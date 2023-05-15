package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hillspet.wearables.dto.MaterialCategory;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MaterialCategoriesResponse {

	List<MaterialCategory> materialCategories;

	public List<MaterialCategory> getMaterialCategories() {
		return materialCategories;
	}

	public void setMaterialCategories(List<MaterialCategory> materialCategories) {
		this.materialCategories = materialCategories;
	}

}

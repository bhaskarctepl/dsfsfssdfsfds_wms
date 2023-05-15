package com.hillspet.wearables.service.supportmaterial;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.response.MaterialCategoriesResponse;
import com.hillspet.wearables.response.MaterialTypesResponse;
import com.hillspet.wearables.response.SupportMaterialsResponse;

public interface SupportMaterialService {

	MaterialTypesResponse getMaterialTypeList() throws ServiceExecutionException;

	MaterialCategoriesResponse getMaterialCategoryList(int categoryId) throws ServiceExecutionException;

	SupportMaterialsResponse getSupportMaterials(int categoryId, String deviceType, String deviceModel, int subCategoryId,
			int materialType) throws ServiceExecutionException;

}

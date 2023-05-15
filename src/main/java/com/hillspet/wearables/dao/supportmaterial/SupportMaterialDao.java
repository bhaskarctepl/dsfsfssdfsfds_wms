package com.hillspet.wearables.dao.supportmaterial;

import java.util.List;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.MaterialCategory;
import com.hillspet.wearables.dto.MaterialType;
import com.hillspet.wearables.dto.SupportMaterial;

public interface SupportMaterialDao {

	List<MaterialType> getMaterialTypeList() throws ServiceExecutionException;

	List<MaterialCategory> getMaterialCategoryList(int categoryId) throws ServiceExecutionException;

	SupportMaterial getSupportMaterials(int categoryId, String deviceType, String deviceModel, int subCategoryId,
			int materialType) throws ServiceExecutionException;

}

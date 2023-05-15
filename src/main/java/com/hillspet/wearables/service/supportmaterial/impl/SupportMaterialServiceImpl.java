package com.hillspet.wearables.service.supportmaterial.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.supportmaterial.SupportMaterialDao;
import com.hillspet.wearables.dto.MaterialCategory;
import com.hillspet.wearables.dto.MaterialType;
import com.hillspet.wearables.dto.SupportMaterial;
import com.hillspet.wearables.response.MaterialCategoriesResponse;
import com.hillspet.wearables.response.MaterialTypesResponse;
import com.hillspet.wearables.response.SupportMaterialsResponse;
import com.hillspet.wearables.service.supportmaterial.SupportMaterialService;

@Service
public class SupportMaterialServiceImpl implements SupportMaterialService {

	private static final Logger LOGGER = LogManager.getLogger(SupportMaterialServiceImpl.class);

	@Autowired
	private SupportMaterialDao supportMaterialDao;

	@Override
	public MaterialTypesResponse getMaterialTypeList() throws ServiceExecutionException {
		LOGGER.debug("getMaterialTypeList called");
		List<MaterialType> materialTypeList = supportMaterialDao.getMaterialTypeList();
		MaterialTypesResponse response = new MaterialTypesResponse();
		response.setMaterialTypes(materialTypeList);
		LOGGER.debug("getMaterialTypeList end");
		return response;
	}

	@Override
	public MaterialCategoriesResponse getMaterialCategoryList(int categoryId) throws ServiceExecutionException {
		LOGGER.debug("getMaterialCategoryList called");
		List<MaterialCategory> materialCategoryList = supportMaterialDao.getMaterialCategoryList(categoryId);
		MaterialCategoriesResponse response = new MaterialCategoriesResponse();
		response.setMaterialCategories(materialCategoryList);
		LOGGER.debug("getMaterialCategoryList end");
		return response;
	}

	@Override
	public SupportMaterialsResponse getSupportMaterials(int categoryId, String deviceType, String deviceModel,
			int subCategoryId, int documentType) throws ServiceExecutionException {
		LOGGER.debug("getSupportMaterials called");
		SupportMaterial supportMaterialList = supportMaterialDao.getSupportMaterials(categoryId, deviceType,
				deviceModel, subCategoryId, documentType);
		SupportMaterialsResponse response = new SupportMaterialsResponse();
		response.setSupportMaterials(supportMaterialList);
		LOGGER.debug("getSupportMaterials end");
		return response;
	}

}

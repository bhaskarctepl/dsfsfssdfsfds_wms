package com.hillspet.wearables.dao.supportmaterial.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.common.utils.GCPClientUtil;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.supportmaterial.SupportMaterialDao;
import com.hillspet.wearables.dto.MaterialCategory;
import com.hillspet.wearables.dto.MaterialType;
import com.hillspet.wearables.dto.SupportMaterial;
import com.hillspet.wearables.dto.SupportMaterialDetails;

@Repository
public class SupportMaterialDaoImpl extends BaseDaoImpl implements SupportMaterialDao {

	private static final Logger LOGGER = LogManager.getLogger(SupportMaterialDaoImpl.class);

	@Autowired
	private GCPClientUtil gcpClientUtil;

	private static String GET_SUPPORT_MATERIAL_TYPES = "MOBILE_APP_GET_SUPPORT_MATERIAL_TYPES";
	private static String GET_SUPPORT_MATERIAL_CATEGORIES = "MOBILE_APP_GET_SUPPORT_MATERIAL_CATEGORIES";
	private static String GET_SUPPORT_MATERIALS = "CALL MOBILE_APP_GET_SUPPORT_MATERIALS(?,?,?,?,?)";

	@Override
	public List<MaterialType> getMaterialTypeList() throws ServiceExecutionException {
		List<MaterialType> materialTypeList = new ArrayList<>();
		LOGGER.debug("getMaterialTypeList called");
		try {
			jdbcTemplate.query(GET_SUPPORT_MATERIAL_TYPES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					MaterialType materialType = new MaterialType();
					materialType.setMaterialTypeId(rs.getInt("MATERIAL_TYPE_ID"));
					materialType.setMaterialTypeName(rs.getString("MATERIAL_TYPE_NAME"));
					materialTypeList.add(materialType);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getMaterialTypeList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return materialTypeList;
	}

	@Override
	public List<MaterialCategory> getMaterialCategoryList(int categoryId) throws ServiceExecutionException {
		List<MaterialCategory> materialCategoryList = new ArrayList<>();
		LOGGER.debug("getMaterialCategoryList called");
		try {
			jdbcTemplate.query(GET_SUPPORT_MATERIAL_CATEGORIES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					MaterialCategory materialCategory = new MaterialCategory();
					materialCategory.setCategoryId(rs.getInt("MATERIAL_CATEGORY_ID"));
					materialCategory.setCategoryName(rs.getString("MATERIAL_CATEGORY_NAME"));
					materialCategory.setParentId(rs.getInt("PARENT_ID"));
					materialCategoryList.add(materialCategory);
				}
			}, categoryId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getMaterialCategoryList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return materialCategoryList;
	}

	@Override
	public SupportMaterial getSupportMaterials(int categoryId, String deviceType, String deviceModel, int subCategoryId,
			int materialType) throws ServiceExecutionException {
		SupportMaterial supportMaterials = new SupportMaterial();
		List<SupportMaterialDetails> fags = new ArrayList<>();
		List<SupportMaterialDetails> videos = new ArrayList<>();
		List<SupportMaterialDetails> userGuides = new ArrayList<>();

		LOGGER.debug("getSupportMaterials called");
		try {
			jdbcTemplate.query(GET_SUPPORT_MATERIALS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					SupportMaterialDetails supportMaterial = new SupportMaterialDetails();
					supportMaterial.setMaterialTypeId(rs.getInt("MATERIAL_TYPE_ID"));
					supportMaterial.setMaterialTypeName(rs.getString("MATERIAL_TYPE_NAME"));
					supportMaterial.setCategoryId(rs.getInt("CATEGORY_ID"));
					supportMaterial.setCategoryName(rs.getString("CATEGORY_NAME"));
					supportMaterial.setSubCategoryId(rs.getInt("SUB_CATEGORY_ID"));
					supportMaterial.setSubCategoryName(rs.getString("SUB_CATEGORY_NAME"));
					supportMaterial.setAssetType(rs.getString("ASSET_TYPE"));
					supportMaterial.setAssetModel(rs.getString("ASSET_MODEL"));
					supportMaterial.setTitleOrQuestion(rs.getString("TITLE_OR_QUESTION"));
					supportMaterial.setUrlOrAnswer(rs.getString("URL_OR_ANSWER"));

					String filePath = rs.getString("URL_OR_ANSWER");
					if (StringUtils.isNotBlank(filePath)
							&& supportMaterial.getMaterialTypeId() != Constants.NUMBER_TWO) {
						Map<String, String> fileData = gcpClientUtil.getDownloadFirebaseFileUrlWithSize(filePath);
						supportMaterial.setUrlOrAnswer(fileData.get("URL"));
						supportMaterial.setSize(fileData.get("SIZE")); // SIZE

						supportMaterial.setFileName(rs.getString("UPLOADED_FILE_NAME"));
						supportMaterial.setThumbnailUrl(gcpClientUtil.getDownloadFirebaseFileUrlWithSize(rs.getString("THUMBNAIL_URL")).get("URL"));
					}

					if (supportMaterial.getMaterialTypeId() == Constants.NUMBER_ONE) {
						videos.add(supportMaterial);
					}

					if (supportMaterial.getMaterialTypeId() == Constants.NUMBER_TWO) {
						fags.add(supportMaterial);
					}

					if (supportMaterial.getMaterialTypeId() == Constants.NUMBER_THREE) {
						userGuides.add(supportMaterial);
					}
				}
			}, materialType, categoryId, subCategoryId, deviceType, deviceModel);
			supportMaterials.setVideos(videos);
			supportMaterials.setFags(fags);
			supportMaterials.setUserGuides(userGuides);
		} catch (Exception e) {
			LOGGER.error("error while fetching getSupportMaterials ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return supportMaterials;
	}

}

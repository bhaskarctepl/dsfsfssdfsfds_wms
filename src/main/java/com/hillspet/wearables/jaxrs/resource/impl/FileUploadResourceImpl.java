package com.hillspet.wearables.jaxrs.resource.impl;

import java.io.InputStream;
import java.util.ArrayList;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.common.utils.GCPClientUtil;
import com.hillspet.wearables.jaxrs.resource.FileUploadResource;
import com.hillspet.wearables.service.pet.PetService;

@Service
public class FileUploadResourceImpl implements FileUploadResource {

	@Autowired
	GCPClientUtil gcpClientUtil;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;
	
	@Autowired
	private PetService petService;

	private static final Logger LOGGER = LogManager.getLogger(FileUploadResourceImpl.class);

	@Override
	public Response uploadPetPhoto(InputStream uploadedInputStream, FormDataContentDisposition fileDetail, int petId,
			int petParentId, FormDataBodyPart bodyPart) {
		LOGGER.debug("FileUploadResourceImpl  :::  start");

		String fileName = gcpClientUtil.uploadFile(uploadedInputStream, bodyPart, Constants.GCP_PET_PHOTO_PATH);
		LOGGER.debug("FileUploadResourceImpl  :::  fileName " + fileName);
		ArrayList<String> fileArray = new ArrayList<>();
		fileArray.add(fileName);

		if (StringUtils.isNotBlank(fileName)) {
			String fileUrl = gcpClientUtil.getDownloaFiledUrl(fileName, Constants.GCP_PET_PHOTO_PATH);
			fileArray.add(fileUrl);
			petService.updatePetPhoto(petId, petParentId, fileName);
		}

		SuccessResponse<ArrayList<String>> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(fileArray);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response uploadFile(InputStream uploadedInputStream, FormDataContentDisposition fileDetail,
			String uploadFolderName, FormDataBodyPart bodyPart) {
		LOGGER.debug("FileUploadResourceImpl  :::  start");

		String fileName = gcpClientUtil.uploadFile(uploadedInputStream, bodyPart, uploadFolderName);
		LOGGER.debug("FileUploadResourceImpl  :::  fileName " + fileName);
		ArrayList<String> fileArray = new ArrayList<>();
		fileArray.add(fileName);

		if (StringUtils.isNotBlank(fileName)) {
			String fileUrl = gcpClientUtil.getDownloaFiledUrl(fileName, uploadFolderName);
			fileArray.add(fileUrl);
		}

		SuccessResponse<ArrayList<String>> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(fileArray);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getFileUrlByName(String fileName) {
		LOGGER.info("getFileUrlByName start ");
		String fileUrl = "";
		if (StringUtils.isNotBlank(fileName)) {
			fileUrl = gcpClientUtil.getDownloaFiledUrl(fileName, Constants.GCP_PET_PHOTO_PATH);
		}
		LOGGER.info("getFileUrlByName end ");
		SuccessResponse<String> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(fileUrl);
		return responseBuilder.buildResponse(successResponse);
	}
}

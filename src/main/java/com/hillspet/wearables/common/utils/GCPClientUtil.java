package com.hillspet.wearables.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;

@Service
public class GCPClientUtil {

	private static final Logger LOGGER = LogManager.getLogger(GCPClientUtil.class);

	private static Storage storage;

	@Value("${mediaPath}")
	private String mediaPath;

	@Value("${gcp.uploadFolderPath}")
	// private String uploadFolderPath="UAT/GCloud/WPortal/";
	private String uploadFolderPath;

	@Value("${gcp.bucketName}")
	// private String bucketName="wearables-sensor-data-pr";
	private String bucketName;

	@Value("${firebase.storageBucket}")
	private String firebaseBucketName;

	private Bucket getBucket(String bucketName) throws IOException {
		LOGGER.info("inside getbucketName() :::  " + bucketName);
		Bucket bucket = getStorageInstance().get(bucketName);
		if (bucket == null) {
			throw new IOException("Bucket not found:" + bucketName);
		}
		return bucket;
	}

	/**
	 * @param uploadedInputStream
	 * @param bodyPart
	 * @param gcFolderName
	 * @return
	 */
	public String uploadFile(InputStream uploadedInputStream, FormDataBodyPart bodyPart, String gcFolderName) {

		String fileName = "";
		try {
			LOGGER.info("gcFolderName  :::  " + gcFolderName);

			Bucket bucket = getBucket(bucketName);
			if (bucket != null) {
				String contentType = bodyPart.getMediaType().toString();
				String UploadedFileName = bodyPart.getContentDisposition().getFileName();

				File file = convertMultiPartToFile(uploadedInputStream, UploadedFileName);
				InputStream inputStream = new FileInputStream(file);
				fileName = generateFileName(UploadedFileName);
				String gcFileName = generateGCFileName(fileName, gcFolderName);

				bucket.create(gcFileName, inputStream, contentType);
				// Blob blob = bucket.create(gcFileName, inputStream, contentType);
				// String fileUrl = blob.getMediaLink();
				inputStream.close();
				file.delete();

				// for (Bucket currentBucket : storage.list().iterateAll()) {
				// LOGGER.info(currentBucket); }

			} else {
				LOGGER.info("Bucket not found :::: " + bucketName);
			}

		} catch (IOException e) {
			LOGGER.error("error while uploading the images to google storage", e);
			throw new ServiceExecutionException(e.getMessage());
		} catch (Exception e) {
			LOGGER.error("error while google storage", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return fileName;
	}

	public List<Map<String, String>> bulkUpload(List<FormDataBodyPart> bodyPartList, String gcpFolderName) {
		LOGGER.debug("GCPClientUtil bulkUpload inside");
		List<Map<String, String>> fileNameMap = new ArrayList<Map<String, String>>(bodyPartList.size());
		LOGGER.debug("GCPClientUtil bulkUpload fileNameMap" + fileNameMap);
		String fileName = "";
		try {
			LOGGER.info("gcpFolderName  :::  " + gcpFolderName);
			LOGGER.debug("GCPClientUtil gcpFolderName" + gcpFolderName);
			Bucket bucket = getBucket(bucketName);
			if (bucket != null) {
				for (FormDataBodyPart bodyPart : bodyPartList) {
					InputStream uploadInputStream = bodyPart.getValueAs(InputStream.class);
					String contentType = bodyPart.getMediaType().toString();
					String UploadedFileName = bodyPart.getContentDisposition().getFileName();
					File file = convertMultiPartToFile(uploadInputStream, UploadedFileName);
					InputStream inputStream = new FileInputStream(file);
					HashMap<String, String> nameMap = new HashMap<String, String>();
					fileName = generateFileName(UploadedFileName);
					LOGGER.debug("fileName" + fileName);
					String gcFileName = generateGCFileName(fileName, gcpFolderName);
					bucket.create(gcFileName, inputStream, contentType);
					nameMap.put(UploadedFileName, fileName);
					fileNameMap.add(nameMap);
					LOGGER.debug("nameMap = " + nameMap);

					LOGGER.debug("gcpFolderName = " + gcpFolderName);
					/*
					 * if(gcpFolderName.equals(Constants.GCP_SUPPORT_MATERIAL_FILE_PATH) &&
					 * FilenameUtils.getExtension(UploadedFileName).equals("mp4")) { fileName =
					 * FilenameUtils.removeExtension(fileName);
					 * LOGGER.debug("fileName after extension remove" + fileName); File thumbnail
					 * =ThumbnailGenerator.generate(file.getAbsolutePath(), inputStream);
					 * InputStream inputStream2 = new FileInputStream(thumbnail); String gcFileName2
					 * = generateGCFileName(fileName,
					 * Constants.GCP_SUPPORT_MATERIAL_THUMBNAIL_PATH); bucket.create(gcFileName2,
					 * inputStream2, "image/png"); inputStream2.close(); thumbnail.delete(); }
					 */
					inputStream.close();
					file.delete();

				}
			} else {
				LOGGER.info("Bucket not found :::: " + bucketName);
			}

		} catch (IOException e) {
			LOGGER.error("error while uploading the images to google storage", e);
			throw new ServiceExecutionException(e.getMessage());
		} catch (Exception e) {
			LOGGER.error("error while google storage", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return fileNameMap;
	}

	private File convertMultiPartToFile(InputStream uploadedInputStream, String fileName) throws IOException {
		File convFile = new File(mediaPath + File.separator + fileName);
		FileOutputStream fos = new FileOutputStream(convFile);
		IOUtils.copy(uploadedInputStream, fos);
		fos.close();
		return convFile;
	}

	private String generateFileName(String fileName) {
		LOGGER.info("fileName  :::  " + fileName + " ext  ::: " + fileName.substring(fileName.lastIndexOf(".")));
		// return uploadFolderPath + gcFolderName + "/" + new Date().getTime() +
		// "-" + fileName.replace(" ", "_");
		// return new Date().getTime()+fileName.split(".")[1];
		return new Date().getTime() + fileName.substring(fileName.lastIndexOf("."));
	}

	private String generateGCFileName(String fileName, String gcpFolderName) {
		LOGGER.info("uploadFolderPath  :::  " + uploadFolderPath);
		return uploadFolderPath + gcpFolderName + "/" + fileName;
	}

	@SuppressWarnings("unused")
	public String getDownloaFiledUrl(String fileName, String gcFolderName) {
		String url = "";
		LOGGER.info("bucketName :::  " + bucketName);
		LOGGER.info("filePath :::  " + uploadFolderPath + gcFolderName + "/" + fileName);
		try {
			Blob blob = getStorageInstance().get(bucketName).get(uploadFolderPath + gcFolderName + "/" + fileName);
			if (blob != null && StringUtils.isNotBlank(blob.getName())) {
				String blobName = blob.getName();
				URL signedUrl = getStorageInstance().signUrl(BlobInfo.newBuilder(bucketName, blobName).build(), 30,
						TimeUnit.MINUTES);
				url = signedUrl.toExternalForm();
			}
		} catch (Exception e) {
			LOGGER.error("error while google storage getDownloaFiledUrl", e);
		}
		return url;
	}

	@SuppressWarnings("unused")
	public String getPreludeDownloadFileUrl(String bucketName, String filePath) {
		String url = "";

		if (!StringUtils.isEmpty(bucketName) && !StringUtils.isEmpty(filePath)) {
			try {

				if (filePath.charAt(0) == '/') {
					filePath = filePath.substring(1, filePath.length());
				}
				// bucketName = "wearables-ap-prelude";
				// filePath="data-extracts/WAM/08-19-2021/WAM_COAST_20210819.xlsx";
				LOGGER.info("bucketName :::  " + bucketName);
				LOGGER.info("filePath :::  " + filePath);
				Blob blob = getStorageInstance().get(bucketName.trim()).get(filePath.trim());
				if (blob != null && StringUtils.isNotBlank(blob.getName())) {
					String blobName = blob.getName();
					URL signedUrl = getStorageInstance().signUrl(BlobInfo.newBuilder(bucketName, blobName).build(), 30,
							TimeUnit.MINUTES);
					url = signedUrl.toExternalForm();
					LOGGER.info("signed url :::  " + url);
				}
			} catch (Exception e) {
				LOGGER.error("error while google storage getDownloaFiledUrl", e);
			}
		}
		return url;
	}

	@SuppressWarnings("unused")
	private Boolean deleteFile(String fileName, String gcFolderName) {
		Boolean isFileDeleted = false;

		Blob blob = getStorageInstance().get(bucketName).get(uploadFolderPath + gcFolderName + "/" + fileName);
		if (blob.exists()) {
			isFileDeleted = blob.delete();
		}

		LOGGER.info("isFileDeleted :::  " + isFileDeleted);
		return isFileDeleted;
	}

	public Map<String, String> getDownloadGcpFileUrlWithSize(String fileName, String gcFolderName) {
		Map<String, String> fileData = new HashMap<>();
		String url = "";
		LOGGER.info("bucketName :::  " + bucketName);
		LOGGER.info("filePath :::  " + uploadFolderPath + gcFolderName + "/" + fileName);
		try {
			Blob blob = getStorageInstance().get(bucketName).get(uploadFolderPath + gcFolderName + "/" + fileName);
			if (blob != null && StringUtils.isNotBlank(blob.getName())) {
				String blobName = blob.getName();
				URL signedUrl = getStorageInstance().signUrl(BlobInfo.newBuilder(bucketName, blobName).build(), 30,
						TimeUnit.MINUTES);
				url = signedUrl.toExternalForm();
				fileData.put("URL", url);
				fileData.put("SIZE", blob.getSize() + "");
			}
		} catch (Exception e) {
			LOGGER.error("error while google storage getDownloaFiledUrl", e);
		}

		return fileData;
	}

	public Map<String, String> getDownloadFirebaseFileUrlWithSize(String fileName) {
		Map<String, String> fileData = new HashMap<>();
		String url = "";
		LOGGER.info("bucketName :::  " + firebaseBucketName);
		LOGGER.info("filePath :::  " + fileName);
		try {
			Blob blob = getStorageInstance().get(firebaseBucketName).get(fileName);
			if (blob != null && StringUtils.isNotBlank(blob.getName())) {
				String blobName = blob.getName();
				URL signedUrl = getStorageInstance().signUrl(BlobInfo.newBuilder(firebaseBucketName, blobName).build(),
						30, TimeUnit.MINUTES);
				url = signedUrl.toExternalForm();
				fileData.put("URL", url);
				fileData.put("SIZE", blob.getSize() + "");
			}
		} catch (Exception e) {
			LOGGER.error("error while google storage getDownloadFileUrlWithSize", e);
		}

		return fileData;
	}

	public String uploadFileByteArray(InputStream uploadedInputStream, FormDataBodyPart bodyPart, String gcFolderName) {
		LOGGER.error("inside uploadFileByteArray");
		String fileName = "";
		try {
			LOGGER.info("gcFolderName  :::  " + gcFolderName);
			Bucket bucket = getBucket(bucketName);
			if (bucket != null) {
				String contentType = bodyPart.getMediaType().toString();
				String UploadedFileName = bodyPart.getContentDisposition().getFileName();
				fileName = generateFileName(UploadedFileName);
				String gcFileName = generateGCFileName(fileName, gcFolderName);
				bucket.create(gcFileName, uploadedInputStream, contentType);
				uploadedInputStream.close();

			} else {
				LOGGER.info("Bucket not found :::: " + bucketName);
			}
		} catch (IOException e) {
			LOGGER.error("error while uploading the images to google storage", e);
			throw new ServiceExecutionException(e.getMessage());
		} catch (Exception e) {
			LOGGER.error("error while google storage", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		LOGGER.error("end of uploadFileByteArray " + fileName);
		return fileName;
	}

	public static Storage getStorageInstance() {
		try {
			if (storage == null) {

				/* String jsonPath = "D:\\CTE_Projects\\gcp_storage_credentials\\gcp-credentials_local.json";
				GoogleCredentials credentials = null;
				try {
					credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath));
				} catch (IOException e) {
					e.printStackTrace();
				}
				storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService(); */

				storage = StorageOptions.getDefaultInstance().getService();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return storage;
	}

}

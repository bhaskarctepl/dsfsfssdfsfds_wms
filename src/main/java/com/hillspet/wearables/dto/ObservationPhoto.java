package com.hillspet.wearables.dto;

public class ObservationPhoto {
	private int observationPhotoId;
	private String fileName;
	private String filePath;
	private int isDeleted;

	public int getObservationPhotoId() {
		return observationPhotoId;
	}

	public void setObservationPhotoId(int observationPhotoId) {
		this.observationPhotoId = observationPhotoId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

}

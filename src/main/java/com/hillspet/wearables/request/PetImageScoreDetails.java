package com.hillspet.wearables.request;

public class PetImageScoreDetails {
	private Integer imageScoringDtlsId;
	private String imageUrl;
	private String thumbnailUrl;
	private String value;
	private Integer uom;

	public Integer getUom() {
		return uom;
	}

	public void setUom(Integer uom) {
		this.uom = uom;
	}

	public Integer getImageScoringDtlsId() {
		return imageScoringDtlsId;
	}

	public void setImageScoringDtlsId(Integer imageScoringDtlsId) {
		this.imageScoringDtlsId = imageScoringDtlsId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

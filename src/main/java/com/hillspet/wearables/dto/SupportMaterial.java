package com.hillspet.wearables.dto;

import java.util.List;

public class SupportMaterial {

	List<SupportMaterialDetails> fags;
	List<SupportMaterialDetails> videos;
	List<SupportMaterialDetails> userGuides;

	public List<SupportMaterialDetails> getFags() {
		return fags;
	}

	public void setFags(List<SupportMaterialDetails> fags) {
		this.fags = fags;
	}

	public List<SupportMaterialDetails> getVideos() {
		return videos;
	}

	public void setVideos(List<SupportMaterialDetails> videos) {
		this.videos = videos;
	}

	public List<SupportMaterialDetails> getUserGuides() {
		return userGuides;
	}

	public void setUserGuides(List<SupportMaterialDetails> userGuides) {
		this.userGuides = userGuides;
	}

}

package com.hillspet.wearables.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GCStorageUtil {

	@Autowired
	private GCPClientUtil gcpClientUtil;
	
	public List<String> getMediaSignedUrlList(String mediaPathCSV, String mediaDir) {
		List<String> mediaPathList = new ArrayList<>();
		if(!StringUtils.isEmpty(mediaPathCSV)) {		
		String[] mediaPathCSVValues = mediaPathCSV.split(",");		
		
		for(String mediaPathValue : mediaPathCSVValues) {
			if (mediaPathValue != null && !mediaPathValue.trim().equals("")) {
				if(!mediaPathValue.contains("firebasestorage")) {
					String mediaSignedUrl =	gcpClientUtil.getDownloaFiledUrl(mediaPathValue, mediaDir);
					mediaPathList.add(mediaSignedUrl);
				}else {
					mediaPathList.add(mediaPathValue);
				}				
			}						
		}		
	}
	return mediaPathList;
}
	
}

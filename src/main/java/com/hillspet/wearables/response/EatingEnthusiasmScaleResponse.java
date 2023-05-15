package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.EatingEnthusiasmScale;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EatingEnthusiasmScaleResponse {
	private List<EatingEnthusiasmScale> eatingEnthusiasmScales;

	public List<EatingEnthusiasmScale> getEatingEnthusiasmScales() {
		return eatingEnthusiasmScales;
	}

	public void setEatingEnthusiasmScales(List<EatingEnthusiasmScale> eatingEnthusiasmScales) {
		this.eatingEnthusiasmScales = eatingEnthusiasmScales;
	}

}

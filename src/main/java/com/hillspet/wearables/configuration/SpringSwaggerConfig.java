package com.hillspet.wearables.configuration;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.annotation.Configuration;

import com.hillspet.wearables.common.constants.Constants;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringSwaggerConfig {

	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage(Constants.WEARABLES_BASE_PACKAGE)).paths(PathSelectors.any())
				.build().enable(true).apiInfo(apiInfo());
	}

	public ApiInfo apiInfo() {
		StringVendorExtension vendorExtension = new StringVendorExtension("", "");
		Collection<VendorExtension> vendorExtensions = new ArrayList<>();
		vendorExtensions.add(vendorExtension);

		Contact contactInfo = new Contact("", "", "");

		return new ApiInfo("Wearables Portal Mobile Webservices Documentation",
				"This document contains all the details about the webservices which are using in Wearables Portal Mobile Application",
				"1.2", "For Dev only", contactInfo, "", "", vendorExtensions);
	}
}

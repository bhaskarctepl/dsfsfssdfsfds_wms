package com.hillspet.wearables.configuration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.hillspet.wearables.common.constants.Constants;

import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@Component
@Primary
public class CombinedResourceProvider implements SwaggerResourcesProvider {

	@Resource
	private InMemorySwaggerResourcesProvider inMemorySwaggerResourcesProvider;

	public List<SwaggerResource> get() {

		SwaggerResource jerseySwaggerResource = new SwaggerResource();
		jerseySwaggerResource.setLocation(Constants.SWAGGER_JSON_PATH);
		jerseySwaggerResource.setName("Wearables Operations");

		return Stream.concat(Stream.of(jerseySwaggerResource), inMemorySwaggerResourcesProvider.get().stream())
				.collect(Collectors.toList());
	}

}
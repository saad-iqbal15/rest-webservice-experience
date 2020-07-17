package com.webservice.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class ConfigSwagger {
	 
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.groupName("Spring Rest API").apiInfo(apiInfo()).select()
				.paths(regex("/userdetails.*")).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("User Details Service")
				.description("Documentation for User Detail Rest API")
				.version("1.0").build();
	}

}

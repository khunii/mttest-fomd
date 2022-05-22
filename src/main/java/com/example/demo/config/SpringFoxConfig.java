package com.example.demo.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;

@Configuration
@EnableSwagger2
public class SpringFoxConfig{
	
	private Predicate<String> uriPaths(String mainRegex){
		return or(PathSelectors.regex(mainRegex),
				  PathSelectors.regex("/auth/.*")
				);
	}
	
	@Bean
	public Docket configProjectProd() {
		ApiInfoBuilder apiInfoT = new ApiInfoBuilder();
		apiInfoT.title("MTTEST_fomd").version("1.0").build(); //상품
		
		//security도 포함
		Docket docketT = new Docket(DocumentationType.SWAGGER_2)
				.securityContexts(Lists.newArrayList(securityContext()))
				.securitySchemes(Lists.newArrayList(apiKey()));
		
		//swagger에 표시할 api들에 대한 선언
		docketT.groupName("fomd").select().apis(RequestHandlerSelectors.any())
		.paths(uriPaths("/fo/md/.*"))
		.paths(Predicates.not(PathSelectors.regex("/error.*"))) //제외할 URI path 지정
		.build().apiInfo(apiInfoT.build());
		
		return docketT;
	}
	
	@Bean
	public Docket configProjectOrder() {
		ApiInfoBuilder apiInfoT = new ApiInfoBuilder();
		apiInfoT.title("PRJCODE_food").version("1.0").build();//주문
		
		//security도 포함
		Docket docketT = new Docket(DocumentationType.SWAGGER_2)
				.securityContexts(Lists.newArrayList(securityContext()))
				.securitySchemes(Lists.newArrayList(apiKey()));
		
		//swagger에 표시할 api들에 대한 선언
		docketT.groupName("food").select().apis(RequestHandlerSelectors.any())
		.paths(PathSelectors.regex("/fo/od/.*"))
		.paths(Predicates.not(PathSelectors.regex("/error.*"))) //제외할 URI path 지정
		.build().apiInfo(apiInfoT.build());
		
		return docketT;
	}

	@Bean
	public Docket configProjectEvent() {
		ApiInfoBuilder apiInfoT = new ApiInfoBuilder();
		apiInfoT.title("PRJCODE_foev").version("1.0").build();//이벤트
		
		//security도 포함
		Docket docketT = new Docket(DocumentationType.SWAGGER_2)
				.securityContexts(Lists.newArrayList(securityContext()))
				.securitySchemes(Lists.newArrayList(apiKey()));
		
		//swagger에 표시할 api들에 대한 선언
		docketT.groupName("foev").select().apis(RequestHandlerSelectors.any())
		.paths(PathSelectors.regex("/fo/ev/.*"))
		.paths(Predicates.not(PathSelectors.regex("/error.*"))) //제외할 URI path 지정
		.build().apiInfo(apiInfoT.build());
		
		return docketT;
	}

	@Bean
	public Docket configProjectCustomer() {
		ApiInfoBuilder apiInfoT = new ApiInfoBuilder();
		apiInfoT.title("PRJCODE_focu").version("1.0").build();//회원
		
		//security도 포함
		Docket docketT = new Docket(DocumentationType.SWAGGER_2)
				.securityContexts(Lists.newArrayList(securityContext()))
				.securitySchemes(Lists.newArrayList(apiKey()));
		
		//swagger에 표시할 api들에 대한 선언
		docketT.groupName("focu").select().apis(RequestHandlerSelectors.any())
		.paths(PathSelectors.regex("/fo/cu/.*"))
		.paths(Predicates.not(PathSelectors.regex("/error.*"))) //제외할 URI path 지정
		.build().apiInfo(apiInfoT.build());
		
		return docketT;
	}
	
//	@Bean
//	public Docket api() {
//		
//		Docket docket = new Docket(DocumentationType.SWAGGER_2)
//		.apiInfo(apiEndPointsInfo())
//		.pathMapping("/")
//		.forCodeGeneration(true)
//		.genericModelSubstitutes(ResponseEntity.class)
//		.securityContexts(Lists.newArrayList(securityContext()))
//        .securitySchemes(Lists.newArrayList(apiKey()))
//		.useDefaultResponseMessages(false);
//		
//		return docket.select()
//				.paths(PathSelectors.any())
//				.build();
//	}
//	
//	
//	private ApiInfo apiEndPointsInfo() {
//		return new ApiInfoBuilder().title("SDET Sample REST API2")
//	            .description("This documents describes about Sample API")
//	            .license("Apache 2.0")
//	            .version("v1")
//	            .build();
//	}
//
    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private springfox.documentation.spi.service.contexts.SecurityContext securityContext() {
        return springfox.documentation.spi.service.contexts.SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(PathSelectors.any())
            .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("JWT", authorizationScopes));
    }
}

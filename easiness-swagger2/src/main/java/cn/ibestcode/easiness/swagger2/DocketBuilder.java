/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.swagger2;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2019/11/28 20:58
 */
public class DocketBuilder {
  private ApiInfoBuilder apiInfoBuilder;
  private String basePackage;
  private String groupName;
  private List<Parameter> parameters;

  private DocketBuilder() {
    apiInfoBuilder = new ApiInfoBuilder()
      .title("API")
      .description("接口")
      .version("1.0");

    parameters = new ArrayList<>();
  }

  public static DocketBuilder getInstance() {
    return new DocketBuilder();
  }

  public DocketBuilder basePackage(String basePackage) {
    this.basePackage = basePackage;
    return this;
  }

  public DocketBuilder groupName(String groupName) {
    this.groupName = groupName;
    return this;
  }

  public DocketBuilder addHttpHeaderParameter(String name, String description) {
    parameters.add(generateParameter(name, description, "header"));
    return this;
  }

  public DocketBuilder addHttpBodyParameter(String name, String description) {
    parameters.add(generateParameter(name, description, "body"));
    return this;
  }

  public DocketBuilder addHttpQueryParameter(String name, String description) {
    parameters.add(generateParameter(name, description, "query"));
    return this;
  }

  public DocketBuilder addHttpCookieParameter(String name, String description) {
    parameters.add(generateParameter(name, description, "cookie"));
    return this;
  }

  public DocketBuilder title(String title) {
    apiInfoBuilder.title(title);
    return this;
  }

  public DocketBuilder description(String description) {
    apiInfoBuilder.description(description);
    return this;
  }

  public DocketBuilder version(String version) {
    apiInfoBuilder.version(version);
    return this;
  }

  public DocketBuilder license(String license) {
    apiInfoBuilder.license(license);
    return this;
  }

  protected Parameter generateParameter(String name, String description, String type) {
    return new ParameterBuilder()
      .name(name)
      .description(description)
      .modelRef(new ModelRef("string"))
      .parameterType(type)
      .required(false)
      .build();
  }

  public Docket build() {
    return new Docket(DocumentationType.SWAGGER_2)
      .groupName(groupName)
      .apiInfo(apiInfoBuilder.build())
      .globalOperationParameters(parameters)
      .select()
      .apis(RequestHandlerSelectors.basePackage(basePackage))
      .paths(PathSelectors.any())
      .build();
  }
}

/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed to the IBESTCODE under one or more agreements.
 * The IBESTCODE licenses this file to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 */

package cn.ibestcode.easiness.spring.restClient;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.client.*;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.Map;

/**
 * @author WFSO (仵士杰)
 * create by WFSO (仵士杰) at 2020/1/17 18:41
 */
public class RestTemplateExt extends RestTemplate {

  public RestTemplateExt(HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory) {
    super(httpComponentsClientHttpRequestFactory);
  }

  private static <T> T nonNull(@Nullable T result) {
    Assert.state(result != null, "No result");
    return result;
  }

  //region GET

  @Nullable
  public <T> T getForObject(String url,
                            ParameterizedTypeReference<T> responseType,
                            Object... uriVariables) throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(null, type);
    HttpMessageConverterExtractor<T> responseExtractor =
      new HttpMessageConverterExtractor<>(type, getMessageConverters());
    return execute(url, HttpMethod.GET, requestCallback, responseExtractor, uriVariables);
  }


  @Nullable
  public <T> T getForObject(String url,
                            ParameterizedTypeReference<T> responseType,
                            Map<String, ?> uriVariables) throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(null, type);
    HttpMessageConverterExtractor<T> responseExtractor =
      new HttpMessageConverterExtractor<>(type, getMessageConverters());
    return execute(url, HttpMethod.GET, requestCallback, responseExtractor, uriVariables);
  }


  @Nullable
  public <T> T getForObject(URI url,
                            ParameterizedTypeReference<T> responseType) throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(null, type);
    HttpMessageConverterExtractor<T> responseExtractor =
      new HttpMessageConverterExtractor<>(type, getMessageConverters());
    return execute(url, HttpMethod.GET, requestCallback, responseExtractor);
  }


  public <T> ResponseEntity<T> getForEntity(String url,
                                            ParameterizedTypeReference<T> responseType,
                                            Object... uriVariables)
    throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(null, type);
    ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(type);
    return nonNull(execute(url, HttpMethod.GET, requestCallback, responseExtractor, uriVariables));
  }


  public <T> ResponseEntity<T> getForEntity(String url,
                                            ParameterizedTypeReference<T> responseType,
                                            Map<String, ?> uriVariables)
    throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(null, type);
    ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(type);
    return nonNull(execute(url, HttpMethod.GET, requestCallback, responseExtractor, uriVariables));
  }


  public <T> ResponseEntity<T> getForEntity(URI url,
                                            ParameterizedTypeReference<T> responseType)
    throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(null, type);
    ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(type);
    return nonNull(execute(url, HttpMethod.GET, requestCallback, responseExtractor));
  }

  //endregion

  //region POST


  @Nullable
  public <T> T postForObject(String url, @Nullable Object request,
                             ParameterizedTypeReference<T> responseType,
                             Object... uriVariables) throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(request, type);
    HttpMessageConverterExtractor<T> responseExtractor =
      new HttpMessageConverterExtractor<>(type, getMessageConverters());
    return execute(url, HttpMethod.POST, requestCallback, responseExtractor, uriVariables);
  }


  @Nullable
  public <T> T postForObject(String url, @Nullable Object request, ParameterizedTypeReference<T> responseType,
                             Map<String, ?> uriVariables) throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(request, type);
    HttpMessageConverterExtractor<T> responseExtractor =
      new HttpMessageConverterExtractor<>(type, getMessageConverters());
    return execute(url, HttpMethod.POST, requestCallback, responseExtractor, uriVariables);
  }


  @Nullable
  public <T> T postForObject(URI url, @Nullable Object request, ParameterizedTypeReference<T> responseType)
    throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(request, type);
    HttpMessageConverterExtractor<T> responseExtractor =
      new HttpMessageConverterExtractor<>(type, getMessageConverters());
    return execute(url, HttpMethod.POST, requestCallback, responseExtractor);
  }


  public <T> ResponseEntity<T> postForEntity(String url, @Nullable Object request,
                                             ParameterizedTypeReference<T> responseType,
                                             Object... uriVariables) throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(request, type);
    ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(type);
    return nonNull(execute(url, HttpMethod.POST, requestCallback, responseExtractor, uriVariables));
  }


  public <T> ResponseEntity<T> postForEntity(String url, @Nullable Object request,
                                             ParameterizedTypeReference<T> responseType,
                                             Map<String, ?> uriVariables) throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(request, type);
    ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(type);
    return nonNull(execute(url, HttpMethod.POST, requestCallback, responseExtractor, uriVariables));
  }


  public <T> ResponseEntity<T> postForEntity(URI url, @Nullable Object request,
                                             ParameterizedTypeReference<T> responseType)
    throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(request, type);
    ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(type);
    return nonNull(execute(url, HttpMethod.POST, requestCallback, responseExtractor));
  }

  //endregion

  //region PUT

  @Nullable
  public <T> T putForObject(String url, @Nullable Object request,
                            ParameterizedTypeReference<T> responseType,
                            Object... uriVariables) throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(request, type);
    HttpMessageConverterExtractor<T> responseExtractor =
      new HttpMessageConverterExtractor<>(type, getMessageConverters());
    return execute(url, HttpMethod.PUT, requestCallback, responseExtractor, uriVariables);
  }

  @Nullable
  public <T> T putForObject(String url, @Nullable Object request,
                            ParameterizedTypeReference<T> responseType,
                            Map<String, ?> uriVariables)
    throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(request, type);
    HttpMessageConverterExtractor<T> responseExtractor =
      new HttpMessageConverterExtractor<>(type, getMessageConverters());
    return execute(url, HttpMethod.PUT, requestCallback, responseExtractor, uriVariables);
  }

  @Nullable
  public <T> T putForObject(URI url, @Nullable Object request,
                            ParameterizedTypeReference<T> responseType) throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(request, type);
    HttpMessageConverterExtractor<T> responseExtractor =
      new HttpMessageConverterExtractor<>(type, getMessageConverters());
    return execute(url, HttpMethod.PUT, requestCallback, responseExtractor);
  }


  public <T> ResponseEntity<T> putForEntity(String url, @Nullable Object request,
                                            ParameterizedTypeReference<T> responseType,
                                            Object... uriVariables) throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(request, type);
    ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(type);
    return nonNull(execute(url, HttpMethod.PUT, requestCallback, responseExtractor, uriVariables));
  }


  public <T> ResponseEntity<T> putForEntity(String url, @Nullable Object request,
                                            ParameterizedTypeReference<T> responseType,
                                            Map<String, ?> uriVariables)
    throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(request, type);
    ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(type);
    return nonNull(execute(url, HttpMethod.PUT, requestCallback, responseExtractor, uriVariables));
  }


  public <T> ResponseEntity<T> putForEntity(URI url, @Nullable Object request,
                                            ParameterizedTypeReference<T> responseType)
    throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(request, type);
    ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(type);
    return nonNull(execute(url, HttpMethod.PUT, requestCallback, responseExtractor));
  }

  //endregion

  //region PATCH


  @Nullable
  public <T> T patchForObject(String url, @Nullable Object request,
                              ParameterizedTypeReference<T> responseType,
                              Object... uriVariables) throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(request, type);
    HttpMessageConverterExtractor<T> responseExtractor =
      new HttpMessageConverterExtractor<>(type, getMessageConverters());
    return execute(url, HttpMethod.PATCH, requestCallback, responseExtractor, uriVariables);
  }


  @Nullable
  public <T> T patchForObject(String url, @Nullable Object request,
                              ParameterizedTypeReference<T> responseType,
                              Map<String, ?> uriVariables) throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(request, type);
    HttpMessageConverterExtractor<T> responseExtractor =
      new HttpMessageConverterExtractor<>(type, getMessageConverters());
    return execute(url, HttpMethod.PATCH, requestCallback, responseExtractor, uriVariables);
  }


  @Nullable
  public <T> T patchForObject(URI url, @Nullable Object request,
                              ParameterizedTypeReference<T> responseType)
    throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(request, type);
    HttpMessageConverterExtractor<T> responseExtractor =
      new HttpMessageConverterExtractor<>(type, getMessageConverters());
    return execute(url, HttpMethod.PATCH, requestCallback, responseExtractor);
  }


  public <T> ResponseEntity<T> patchForEntity(String url, @Nullable Object request,
                                              ParameterizedTypeReference<T> responseType,
                                              Object... uriVariables) throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(request, type);
    ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(type);
    return nonNull(execute(url, HttpMethod.PATCH, requestCallback, responseExtractor, uriVariables));
  }


  public <T> ResponseEntity<T> patchForEntity(String url, @Nullable Object request,
                                              ParameterizedTypeReference<T> responseType,
                                              Map<String, ?> uriVariables) throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(request, type);
    ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(type);
    return nonNull(execute(url, HttpMethod.PATCH, requestCallback, responseExtractor, uriVariables));
  }


  public <T> ResponseEntity<T> patchForEntity(URI url, @Nullable Object request,
                                              ParameterizedTypeReference<T> responseType)
    throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(request, type);
    ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(type);
    return nonNull(execute(url, HttpMethod.PATCH, requestCallback, responseExtractor));
  }

  //endregion


  //region DELETE


  @Nullable
  public <T> T deleteForObject(String url,
                               ParameterizedTypeReference<T> responseType,
                               Object... uriVariables) throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(null, type);
    HttpMessageConverterExtractor<T> responseExtractor =
      new HttpMessageConverterExtractor<>(type, getMessageConverters());
    return execute(url, HttpMethod.DELETE, requestCallback, responseExtractor, uriVariables);
  }

  @Nullable
  public <T> T deleteForObject(String url,
                               ParameterizedTypeReference<T> responseType,
                               Map<String, ?> uriVariables) throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(null, type);
    HttpMessageConverterExtractor<T> responseExtractor =
      new HttpMessageConverterExtractor<>(type, getMessageConverters());
    return execute(url, HttpMethod.DELETE, requestCallback, responseExtractor, uriVariables);
  }

  @Nullable
  public <T> T deleteForObject(URI url,
                               ParameterizedTypeReference<T> responseType)
    throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(null, type);
    HttpMessageConverterExtractor<T> responseExtractor =
      new HttpMessageConverterExtractor<>(type, getMessageConverters());
    return execute(url, HttpMethod.DELETE, requestCallback, responseExtractor);
  }


  public <T> ResponseEntity<T> deleteForEntity(String url,
                                               ParameterizedTypeReference<T> responseType,
                                               Object... uriVariables) throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(null, type);
    ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(type);
    return nonNull(execute(url, HttpMethod.DELETE, requestCallback, responseExtractor, uriVariables));
  }


  public <T> ResponseEntity<T> deleteForEntity(String url,
                                               ParameterizedTypeReference<T> responseType,
                                               Map<String, ?> uriVariables) throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(null, type);
    ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(type);
    return nonNull(execute(url, HttpMethod.DELETE, requestCallback, responseExtractor, uriVariables));
  }


  public <T> ResponseEntity<T> deleteForEntity(URI url,
                                               ParameterizedTypeReference<T> responseType)
    throws RestClientException {
    Type type = responseType.getType();
    RequestCallback requestCallback = httpEntityCallback(null, type);
    ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(type);
    return nonNull(execute(url, HttpMethod.DELETE, requestCallback, responseExtractor));
  }

  //endregion

}

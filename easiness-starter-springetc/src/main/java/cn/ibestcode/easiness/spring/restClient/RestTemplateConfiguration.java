package cn.ibestcode.easiness.spring.restClient;

import cn.ibestcode.easiness.utils.exception.UtilsException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Configuration
public class RestTemplateConfiguration {
  /**
   * 创建支持 HTTPS 的 HTTP Client
   * 参考： http://blog.csdn.net/ychau/article/details/53905886
   *
   * @return CloseableHttpClient
   */
  @Bean("closeableHttpClient")
  public CloseableHttpClient closeableHttpClient() {
    CloseableHttpClient client = null;
    try {
      HttpClientBuilder b = HttpClientBuilder.create();

      SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (arg0, arg1) -> true).build();
      b.setSSLContext(sslContext);

      HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;

      SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
      Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
        .register("http", PlainConnectionSocketFactory.getSocketFactory())
        .register("https", sslSocketFactory)
        .build();

      PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
      connMgr.setMaxTotal(200);
      connMgr.setDefaultMaxPerRoute(100);
      b.setConnectionManager(connMgr);

      client = b.build();
    } catch (NoSuchAlgorithmException e) {
      log.error(e.getMessage(), e);
      throw new UtilsException("NoSuchAlgorithmException", e);
    } catch (KeyStoreException e) {
      log.error(e.getMessage(), e);
      throw new UtilsException("KeyStoreException", e);
    } catch (KeyManagementException e) {
      log.error(e.getMessage(), e);
      throw new UtilsException("KeyManagementException", e);
    }
    return client;
  }

  @Bean("httpComponentsClientHttpRequestFactory")
  public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory(@Autowired @Qualifier("closeableHttpClient") CloseableHttpClient closeableHttpClient) {
    return new HttpComponentsClientHttpRequestFactory(closeableHttpClient);
  }


  @Bean("restTemplate")
  @ConditionalOnMissingBean
  public RestTemplate restTemplate(@Autowired @Qualifier("httpComponentsClientHttpRequestFactory") HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory) {
    RestTemplate restTemplate = new RestTemplate(httpComponentsClientHttpRequestFactory);
    int index = -1;
    for (int i = 0; i < restTemplate.getMessageConverters().size(); i++) {
      HttpMessageConverter converter = restTemplate.getMessageConverters().get(i);
      if (converter instanceof StringHttpMessageConverter) {
        index = i;
        break;
      }
    }
    //  处理默认字符集导致中文乱码的问题
    StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
    if (index > 0) {
      restTemplate.getMessageConverters().set(index, stringHttpMessageConverter);
    } else {
      restTemplate.getMessageConverters().add(stringHttpMessageConverter);
    }
    return restTemplate;
  }

}

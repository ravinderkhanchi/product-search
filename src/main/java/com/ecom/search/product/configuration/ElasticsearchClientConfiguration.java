

package com.ecom.search.product.configuration;

import javax.net.ssl.SSLContext;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.collect.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.ecom.search.product.repository")
//@ComponentScan(basePackages = "com.betterjavacode.elasticsearchdemo")
public class ElasticsearchClientConfiguration {

  @Value("${aws.es.endpoint}")
  private String endpoint;

  @Bean
  public RestHighLevelClient createSimpleElasticClient() throws Exception {
    try {
      final CredentialsProvider credentialsProvider =
          new BasicCredentialsProvider();
      credentialsProvider.setCredentials(AuthScope.ANY,
          new UsernamePasswordCredentials("admin", "Admin@123"));
      SSLContextBuilder sslBuilder = SSLContexts.custom()
          .loadTrustMaterial(null, (x509Certificates, s) -> true);
      final SSLContext sslContext = sslBuilder.build();
      RestHighLevelClient client = new RestHighLevelClient(RestClient
          .builder(new HttpHost(
              endpoint, 443,
              "https"))
//port number is given as 443 since its https schema
          .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(
                HttpAsyncClientBuilder httpClientBuilder) {
              return httpClientBuilder
                  .setSSLContext(sslContext)
                  .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                  .setDefaultCredentialsProvider(credentialsProvider);
            }
          })
          .setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(
                RequestConfig.Builder requestConfigBuilder) {
              return requestConfigBuilder.setConnectTimeout(50000)
                  .setSocketTimeout(120000);
            }
          }));
      System.out.println("elasticsearch client created");
      return client;
    } catch (Exception e) {
      System.out.println(e);
      throw new Exception("Could not create an elasticsearch client!!");
    }
  }

}

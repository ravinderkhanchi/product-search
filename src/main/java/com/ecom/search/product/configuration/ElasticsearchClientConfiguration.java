

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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.demo.aws.elasticsearch.data.repository")
//@ComponentScan(basePackages = "com.betterjavacode.elasticsearchdemo")
public class ElasticsearchClientConfiguration {
  @Bean
  public RestHighLevelClient createSimpleElasticClient() throws Exception {
    try {
      final CredentialsProvider credentialsProvider =
          new BasicCredentialsProvider();
      credentialsProvider.setCredentials(AuthScope.ANY,
          new UsernamePasswordCredentials("admin", "Admin@123"));

      BasicHeader h1 =
          new BasicHeader("Accept", "application/vnd.elasticsearch+json;compatible-with=7");
      BasicHeader h2 = new BasicHeader("Content-Type", "application/vnd.elasticsearch+json;");
      SSLContextBuilder sslBuilder = SSLContexts.custom()
          .loadTrustMaterial(null, (x509Certificates, s) -> true);
      final SSLContext sslContext = sslBuilder.build();
      RestHighLevelClient client = new RestHighLevelClient(RestClient
          .builder(new HttpHost(
              "search-catalogue-4zf2yrsluz6bqoykb4nbnf377e.ap-south-1.es.amazonaws.com", 443,
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

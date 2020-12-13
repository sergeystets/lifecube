package lifecube.config;

import static org.apache.commons.lang3.StringUtils.isBlank;

import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.http.AWSRequestSigningApacheInterceptor;
import lifecube.repository.elastic.SubmissionSearchRepository;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackageClasses = SubmissionSearchRepository.class)
public class ElasticSearchConfig {

  private static final String serviceName = "es";

  @Value("${aws.local.accessKey}")
  private String accessKey;

  @Value("${aws.local.secretKey}")
  private String secretKey;

  @Value("${aws.elasticsearch.endpoint}")
  private String endpoint;

  @Value("${aws.elasticsearch.region}")
  private String region;

  @Bean
  public ElasticsearchRestTemplate elasticsearchTemplate() {
    return new ElasticsearchRestTemplate(elasticsearchClient());
  }

  @Bean
  public RestHighLevelClient elasticsearchClient() {
    AWSCredentialsProvider credentialsProvider;
    if (isBlank(accessKey)) {
      credentialsProvider = new DefaultAWSCredentialsProviderChain();
    } else {
      credentialsProvider = new AWSStaticCredentialsProvider(
          new BasicAWSCredentials(accessKey, secretKey));
    }

    final AWS4Signer signer = new AWS4Signer();
    signer.setServiceName(serviceName);
    signer.setRegionName(region);

    final HttpRequestInterceptor interceptor =
        new AWSRequestSigningApacheInterceptor(serviceName, signer, credentialsProvider);

    return new RestHighLevelClient(RestClient.builder(HttpHost.create(endpoint))
        .setHttpClientConfigCallback(e -> e.addInterceptorLast(interceptor)));
  }

}

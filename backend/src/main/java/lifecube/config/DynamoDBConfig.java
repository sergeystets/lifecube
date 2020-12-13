package lifecube.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import lifecube.repository.dynamodb.SubmissionRepository;
import org.apache.commons.lang3.StringUtils;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(basePackageClasses = SubmissionRepository.class)
public class DynamoDBConfig {

  @Value("${aws.local.dynamodb.endpoint}")
  private String endpoint;

  @Value("${aws.local.dynamodb.region}")
  private String region;

  @Value("${aws.local.accessKey}")
  private String accessKey;

  @Value("${aws.local.secretKey}")
  private String secretKey;

  @Bean
  public AmazonDynamoDB amazonDynamoDB() {
    if (StringUtils.isNotBlank(endpoint)) {
      return AmazonDynamoDBClientBuilder.standard()
          .withCredentials(
              new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
          .withEndpointConfiguration(new EndpointConfiguration(endpoint, region)).build();
    }
    return AmazonDynamoDBClientBuilder.defaultClient();
  }
}

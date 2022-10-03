package co.epam.springtesting.infraestructure.configuration.sqs;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class SqsConfiguration {
    @Bean
    public SQSConnectionFactory connectionFactory() {
        return new SQSConnectionFactory(new ProviderConfiguration(), buildSqsClient());
    }

    @Bean
    public com.amazonaws.services.sqs.AmazonSQS amazonSqs() {
        return AmazonSQSClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
    }

    private AwsBasicCredentials getBasicCredentials() {
        DefaultAWSCredentialsProviderChain credentialsProviderChain = new DefaultAWSCredentialsProviderChain();
        return AwsBasicCredentials.create(
                credentialsProviderChain.getCredentials().getAWSAccessKeyId(),
                credentialsProviderChain.getCredentials().getAWSSecretKey());
    }

    private SqsClient buildSqsClient() {
        return SqsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(getBasicCredentials()))
                .build();
    }
}

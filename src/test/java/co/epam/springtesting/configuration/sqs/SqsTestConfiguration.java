package co.epam.springtesting.configuration.sqs;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;
import java.net.URISyntaxException;

@TestConfiguration
public class SqsTestConfiguration {
    @Value("${sqs.testcontainers.port}")
    private static int containerPort;

    @Bean
    public SQSConnectionFactory connectionFactory() throws URISyntaxException {
        return new SQSConnectionFactory(new ProviderConfiguration(), buildSqsClient());
    }

    @Bean
    public com.amazonaws.services.sqs.AmazonSQS amazonSqs() {
        return AmazonSQSClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
    }

    private AwsBasicCredentials getBasicCredentials() {
        return AwsBasicCredentials.create(
                "foo",
                "bar");
    }

    private SqsClient buildSqsClient() throws URISyntaxException {
        return SqsClient.builder()
                .endpointOverride(new URI("http://0.0.0.0:"+49153))
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(getBasicCredentials()))
                .build();
    }
}

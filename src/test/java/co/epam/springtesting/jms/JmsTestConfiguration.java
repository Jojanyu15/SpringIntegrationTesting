package co.epam.springtesting.jms;

import co.epam.springtesting.configuration.sqs.SqsTestConfiguration;
import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;
import java.net.URISyntaxException;

@TestConfiguration
@Import(SqsTestConfiguration.class)
public class JmsTestConfiguration {

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
                .endpointOverride(new URI("http://0.0.0.0:"+7777))
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(getBasicCredentials()))
                .build();
    }
    private final SQSConnectionFactory connectionFactory;

    @Autowired
    public JmsTestConfiguration(SQSConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() throws URISyntaxException {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMessageConverter(jacksonJmsMessageConverter());
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate() throws URISyntaxException {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());
        jmsTemplate.setDeliveryPersistent(true);
        jmsTemplate.setSessionTransacted(false);
        return jmsTemplate;
    }


}

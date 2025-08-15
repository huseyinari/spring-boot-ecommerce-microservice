package tr.com.huseyinari.ecommerce.storage.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class S3Configuration {
    private final ECommerceConfigurationProperties configurationProperties;

    @Bean
    public AmazonS3 amazonS3() {
        final String accessKeyId = this.configurationProperties.getAws().getS3().getAccessKeyId();
        final String secretKey = this.configurationProperties.getAws().getS3().getSecretKey();
        final String region = this.configurationProperties.getAws().getS3().getRegion();

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKeyId, secretKey);

        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}

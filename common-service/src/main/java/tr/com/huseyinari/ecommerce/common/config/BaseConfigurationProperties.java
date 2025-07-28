package tr.com.huseyinari.ecommerce.common.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseConfigurationProperties {
    private String baseUrl;
    private String storageObjectContentUrl;
    private KeycloakProperties keycloak;
    private AwsProperties aws;

    @Getter
    @Setter
    public static class KeycloakProperties {
        private String serverUrl;
        private String realm;
        private String clientId;
        private String clientSecret;
        private String adminUsername;
        private String adminPassword;
    }

    @Getter
    @Setter
    public static class AwsProperties {
        private S3Properties s3;
    }

    @Getter
    @Setter
    public static class S3Properties {
        private String accessKeyId;
        private String secretKey;
        private String bucketName;
        private String region;
    }
}

package KPODZ3.StorageService.Config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;

@Configuration
public class S3Config {

        @Value("${minio.url}")
        private String minioUrl;

        @Value("${minio.access-key}")
        private String accessKey;

        @Value("${minio.secret-key}")
        private String secretKey;

        @Value("${minio.bucket}")
        private String bucket;

        @Bean
        public S3Client s3Client() {
                S3Client client = S3Client.builder()
                                .endpointOverride(URI.create(minioUrl))
                                .region(Region.US_EAST_1)
                                .credentialsProvider(
                                                StaticCredentialsProvider.create(
                                                                AwsBasicCredentials.create(accessKey, secretKey)))
                                .serviceConfiguration(
                                                S3Configuration.builder()
                                                                .pathStyleAccessEnabled(true)
                                                                .build())
                                .build();

                try {
                        client.headBucket(b -> b.bucket(bucket));
                } catch (NoSuchBucketException e) {
                        client.createBucket(b -> b.bucket(bucket));
                }

                return client;
        }
}

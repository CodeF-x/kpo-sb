package KPODZ3.StorageService.Service;

import org.springframework.stereotype.Service;
import KPODZ3.StorageService.model.MinioProperties;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import java.net.URI;
import java.time.Duration;

@Service
public class FileUrlService {

    private final S3Presigner presigner;
    private final MinioProperties minioProperties;

    public FileUrlService( MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
        this.presigner = S3Presigner.builder()
                .region(Region.US_EAST_1)
            .credentialsProvider(StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(minioProperties.getAccessKey(), minioProperties.getSecretKey())
            ))
            .endpointOverride(URI.create(minioProperties.getUrl()))
            .serviceConfiguration(
                software.amazon.awssdk.services.s3.S3Configuration.builder()
                    .pathStyleAccessEnabled(true)
                    .build()
            )
            .build();
    }

    public String getFileUrlById(String fileId) {
        String bucketName = minioProperties.getBucket();
        String objectKey = fileId;

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(getObjectRequest)
                .signatureDuration(Duration.ofMinutes(5)) 
                .build();

        return presigner.presignGetObject(presignRequest).url().toString();
    }


}

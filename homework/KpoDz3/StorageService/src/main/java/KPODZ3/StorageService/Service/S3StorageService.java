package KPODZ3.StorageService.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import KPODZ3.StorageService.model.MinioProperties;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.InputStream;

@Service
public class S3StorageService {

    private final S3Client s3Client;
    private final MinioProperties minioProperties;

    public S3StorageService(S3Client s3Client, MinioProperties minioProperties) {
        this.s3Client = s3Client;
        this.minioProperties = minioProperties;
    }

    public void uploadFile(String fileId, MultipartFile file) throws Exception {
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(minioProperties.getBucket())
                        .key(fileId)
                        .build(),
                RequestBody.fromBytes(file.getBytes())
        );
    }

    public InputStream downloadFile(String key) {
        return s3Client.getObject(
                GetObjectRequest.builder()
                        .bucket(minioProperties.getBucket())
                        .key(key)
                        .build()
        );
    }
}


package KPODZ3.StorageService.Controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import KPODZ3.StorageService.Service.FileUrlService;
import KPODZ3.StorageService.Service.S3StorageService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api")
public class FileController {
    private final S3StorageService s3StorageService;
    private final FileUrlService fileUrlService;

    public FileController(S3StorageService s3StorageService, FileUrlService fileUrlService) {
        this.s3StorageService = s3StorageService;
        this.fileUrlService = fileUrlService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(
            @Parameter(description = "Файл работы") @RequestPart("file") MultipartFile file,
            @Parameter(description = "Id файла", schema = @Schema(type = "string", example = "1234.pdf")) @RequestPart("fileId") String fileId)
            throws Exception {

        s3StorageService.uploadFile(fileId, file);

        return ResponseEntity.ok("File uploaded");
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<String> getFileUrl(@PathVariable("id") String id) {
        return ResponseEntity.ok(fileUrlService.getFileUrlById(id));
    }

}

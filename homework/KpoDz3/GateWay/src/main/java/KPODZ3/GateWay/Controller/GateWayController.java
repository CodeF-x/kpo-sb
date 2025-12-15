package KPODZ3.GateWay.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
public class GateWayController {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String storageUrl = "http://storage-service:8080/api/upload";
    private final String analysisUrl = "http://analysis-service:8081/api/work";

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(
            @Parameter(description = "Файл работы") @RequestPart("file") MultipartFile file,
            @Parameter(description = "Имя сдающего", schema = @Schema(type = "string", example = "Kirill")) @RequestPart("userName") String userName,
            @Parameter(description = "Id работы", schema = @Schema(type = "string", example = "19")) @RequestPart("wordId") String workId)
            throws Exception {

        String generatedName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());
        body.add("fileId", generatedName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(storageUrl, requestEntity, String.class);
        HttpStatusCode status = response.getStatusCode();
        if (status != HttpStatus.OK) {
            return ResponseEntity.status(500).body("Storage server do not answer");
        }
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("fileId", generatedName);
        metadata.put("personName", userName);
        metadata.put("taskNumber", workId);

        HttpHeaders analysisHeaders = new HttpHeaders();
        analysisHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> analysisRequest = new HttpEntity<>(metadata, analysisHeaders);

        ResponseEntity<String> analysisResponse = restTemplate.postForEntity(analysisUrl, analysisRequest,
                String.class);
        status = response.getStatusCode();
        if (status != HttpStatus.OK) {
            return ResponseEntity.status(500).body("Analysis server do not answer");
        }

        return ResponseEntity.ok("done");
    }

    @GetMapping("/work/{taskId}/reports")
    public ResponseEntity<String> getFilesByTask(@PathVariable Long taskId) {
        try {
            String analysisUrl = "http://analysis-service:8081/api/tasks/" + taskId + "/files";
            
            ResponseEntity<String> response = restTemplate.getForEntity(analysisUrl, String.class);
            HttpStatusCode status = response.getStatusCode();
            if (status != HttpStatus.OK) {
                return ResponseEntity.status(500).body("Analysis server do not answer");
            }
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
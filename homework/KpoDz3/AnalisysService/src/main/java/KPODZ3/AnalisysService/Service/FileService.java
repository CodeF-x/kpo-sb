package KPODZ3.AnalisysService.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import KPODZ3.AnalisysService.Model.File;

@Service
public class FileService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final TextService textService;
    private final FingerPrintService fingerPrintService;
    private final UserService userService;

    public FileService(TextService textService, FingerPrintService fingerPrintService, UserService userService) {
        this.textService = textService;
        this.fingerPrintService = fingerPrintService;
        this.userService = userService;
    }

    public String getFileUrl(String fileId) {
        return restTemplate.getForObject(
                "http://storage-service:8080/api/files/{id}",
                String.class,
                fileId);
    }

    public byte[] downloadFile(String presignedUrl) throws URISyntaxException {
        URI uri = new URI(presignedUrl);
        return restTemplate.getForObject(uri, byte[].class);
    }

    public String saveAndCheckFile(String url, String username, Long taskId, String fileName) {
        try {
            byte[] file = downloadFile(url);
            String text = "no file";
            if (fileName.endsWith(".pdf")) {
                text = textService.extractTextFromPdf(file);
            } else if (fileName.endsWith(".docx")) {
                text = textService.extractTextFromDocx(file);
            } else if (fileName.endsWith(".txt")) {
                text = textService.extractTextFromTxt(file);
            }
            String normalized = textService.normalize(text);
            List<String> shingles = textService.getShingles(normalized, 5);
            Set<String> fingerprint = textService.hashShingles(shingles);
            List<Set<String>> oldprints = fingerPrintService.getFingerprintsByTask(taskId);
            File newFile = new File();
            newFile.setDate(new Date());
            newFile.setFilename(fileName);
            newFile.setFingerprint(fingerprint);
            newFile.setTaskId(taskId);
            
            if (textService.isPlagiate(oldprints, fingerprint)) {
                newFile.setPlagiate(true);
            }else{
                newFile.setPlagiate(false);
            }
            userService.createOrAddFiles(username, newFile);
            return "ok";
        } catch (Exception e) {
            return e.toString();
        }
    }
}

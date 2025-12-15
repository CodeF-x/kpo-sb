package KPODZ3.AnalisysService.Controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import KPODZ3.AnalisysService.Dto.FileBody;
import KPODZ3.AnalisysService.Dto.FileDto;
import KPODZ3.AnalisysService.Repository.FileRepository;
import KPODZ3.AnalisysService.Service.FileService;

@RestController
@RequestMapping("/api")
public class AnalisController {

    private final FileService fileService;
    private final FileRepository fileRepository;

    public AnalisController(FileService fileService, FileRepository fileRepository) {
        this.fileService = fileService;
        this.fileRepository = fileRepository;
    }

    @PostMapping(value = "/work", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createWork(@RequestBody FileBody request) {
        String url = fileService.getFileUrl(request.getFileId());
        System.out.println(url);
        String answer = fileService.saveAndCheckFile(url, request.getPersonName(), request.getTaskNumber(),
                request.getFileId());
        return ResponseEntity.ok(answer);
    }

    @GetMapping("/tasks/{taskId}/files")
    public List<FileDto> getFilesByTask(@PathVariable Long taskId) {

        return fileRepository.findAllByTaskId(taskId)
                .stream()
                .map(file -> new FileDto(
                        file.getFilename(),
                        file.getUser().getUsername(),
                        file.getPlagiate(),
                        file.getDate()))
                .toList();
    }
}

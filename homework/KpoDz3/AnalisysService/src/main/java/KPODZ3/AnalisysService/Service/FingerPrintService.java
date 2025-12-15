package KPODZ3.AnalisysService.Service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import KPODZ3.AnalisysService.Model.File;
import KPODZ3.AnalisysService.Repository.FileRepository;

@Service
public class FingerPrintService {

    private final FileRepository fileRepository;

    public FingerPrintService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    public List<Set<String>> getFingerprintsByTask(Long taskId) {
        return fileRepository.findAllByTaskId(taskId)
                .stream()
                .map(File::getFingerprint)
                .toList();
    }
}

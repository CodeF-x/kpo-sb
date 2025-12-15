package KPODZ3.AnalisysService.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import KPODZ3.AnalisysService.Model.File;


public interface FileRepository extends JpaRepository<File, Long> {
    @Transactional(readOnly = true)
    List<File> findAllByTaskId(Long taskId);
}

package KPODZ3.AnalisysService.Model;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    private Date date;

    private Boolean plagiate;

    private Long taskId;

    @Convert(converter = StringSetConverter.class)
    @Lob
    private Set<String> fingerprint;
 
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

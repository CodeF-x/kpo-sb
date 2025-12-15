package KPODZ3.AnalisysService.Dto;

import java.util.Date;

import lombok.Data;

@Data
public class FileDto {

    private String filename;
    private String user;
    private boolean plagiate;
    private Date date;

    public FileDto(String name, String userName, Boolean plagiate, Date date) {
        this.filename = name;
        this.user = userName;
        this.plagiate = plagiate;
        this.date = date;
    }

}
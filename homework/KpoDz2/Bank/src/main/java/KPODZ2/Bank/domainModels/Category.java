package KPODZ2.Bank.domainModels;

import java.util.UUID;

import KPODZ2.Bank.models.OperationType;

import lombok.Data;

@Data 
public class Category {
    private UUID Id;
    private OperationType type;
    private String name;

    public Category(UUID id, OperationType type, String name) {
        this.Id = id;
        this.type = type;
        this.name = name;
    }
}

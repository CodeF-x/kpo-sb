package KPODZ2.Bank.odt;

import java.util.UUID;

import KPODZ2.Bank.models.OperationType;

import lombok.Data;

@Data
public class CategoryOdt {
    private UUID Id;
    private OperationType type;
    private String name;

    public CategoryOdt(){}
    
    public CategoryOdt(UUID id, OperationType type, String name) {
        this.Id = id;
        this.type = type;
        this.name = name;
    }
} 

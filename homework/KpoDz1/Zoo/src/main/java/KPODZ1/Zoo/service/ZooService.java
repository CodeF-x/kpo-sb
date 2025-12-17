package KPODZ1.Zoo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import KPODZ1.Zoo.alive.Animal;
import KPODZ1.Zoo.interfaces.IClinic;
import KPODZ1.Zoo.interfaces.IInventory;

@Service
public class ZooService {

    private final IClinic vetClinicService;
    private final List<Animal> animalList;
    private final List<IInventory> inventoryList;

    public ZooService(IClinic vetClinicService) {
        this.vetClinicService = vetClinicService;
        this.animalList = new ArrayList<>();
        this.inventoryList = new ArrayList<>();
    }

    public boolean AddAnimal(Animal animal) {
        if (vetClinicService.doCheckUp(animal)) {
            animalList.add(animal);
            inventoryList.add(animal);
            return true;
        } else {
            inventoryList.add(animal);
            return false;
        }
    }

    public void AddInventory(IInventory item) {
        inventoryList.add(item);
    }

    public int getAllFood() {
        return animalList.stream()
                .mapToInt(animal -> animal.getFood())
                .sum();
    }

    public int getAnimalNumber() {
        return animalList.size();
    }

    public int getInventoryNumber() {
        return inventoryList.size();
    }

    public List<Animal> getContactAble() {
        return animalList.stream()
                .filter(animal -> animal.contactable())
                .collect(Collectors.toList());
    }

    public List<Animal> getAnimalList() {
        return animalList;
    }

    public List<IInventory> getInvetnoryList() {
        return inventoryList;
    }

}

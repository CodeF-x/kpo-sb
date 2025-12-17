package KPODZ1.Zoo.service;

import org.springframework.stereotype.Service;

import KPODZ1.Zoo.alive.Animal;
import KPODZ1.Zoo.interfaces.IClinic;

@Service
public class VetClinicService implements IClinic {
    public boolean doCheckUp(Animal animal) {
        return animal.checkUp();
    }
}

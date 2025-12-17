package KPODZ1.Zoo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import KPODZ1.Zoo.alive.Animal;
import KPODZ1.Zoo.interfaces.IClinic;
import KPODZ1.Zoo.interfaces.IInventory;
import KPODZ1.Zoo.service.ZooService;

@ExtendWith(MockitoExtension.class)
class ZooApplicationTests {

	@Mock
    private IClinic vetClinic;
    private ZooService zooService;

    @BeforeEach
    void setUp() {
        zooService = new ZooService(vetClinic);
    }

    @Test
    void testAddHealthyAnimal() {
        Animal rabbit = mock(Animal.class); 
        when(vetClinic.doCheckUp(rabbit)).thenReturn(true);
        boolean result = zooService.AddAnimal(rabbit);
        assertTrue(result);
        assertEquals(1, zooService.getAnimalNumber());
        assertEquals(1, zooService.getInventoryNumber());
    }

    @Test
    void testAddIllAnimal() {
        Animal tiger = mock(Animal.class);
        when(vetClinic.doCheckUp(tiger)).thenReturn(false);
        boolean result = zooService.AddAnimal(tiger);
        assertFalse(result);
        assertEquals(0, zooService.getAnimalNumber());
        assertEquals(1, zooService.getInventoryNumber());
    }

    @Test
    void testGetAllFoodSum() {
        Animal a1 = mock(Animal.class);
        Animal a2 = mock(Animal.class);
        when(vetClinic.doCheckUp(any())).thenReturn(true);
        when(a1.getFood()).thenReturn(5);
        when(a2.getFood()).thenReturn(10);
        zooService.AddAnimal(a1);
        zooService.AddAnimal(a2);
        assertEquals(15, zooService.getAllFood());
    }

	@Test
void testAddInventoryItem() {
    IInventory table = mock(IInventory.class);
    zooService.AddInventory(table);
    assertEquals(1, zooService.getInventoryNumber());
    assertEquals(0, zooService.getAnimalNumber());
}

@Test
void testGetContactAble() {
    Animal friendly = mock(Animal.class);
    Animal aggressive = mock(Animal.class);
    when(friendly.contactable()).thenReturn(true);
    when(aggressive.contactable()).thenReturn(false);
    when(vetClinic.doCheckUp(any())).thenReturn(true);
    zooService.AddAnimal(friendly);
    zooService.AddAnimal(aggressive);
    List<Animal> result = zooService.getContactAble();
    assertEquals(1, result.size());
    assertTrue(result.contains(friendly));
}
}

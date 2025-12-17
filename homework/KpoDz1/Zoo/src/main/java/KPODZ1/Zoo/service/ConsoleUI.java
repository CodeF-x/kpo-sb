package KPODZ1.Zoo.service;

import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import KPODZ1.Zoo.alive.Animal;
import KPODZ1.Zoo.alive.Monkey;
import KPODZ1.Zoo.alive.Rabbit;
import KPODZ1.Zoo.alive.Tiger;
import KPODZ1.Zoo.alive.Wolf;
import KPODZ1.Zoo.interfaces.IInventory;
import KPODZ1.Zoo.items.Computer;
import KPODZ1.Zoo.items.Table;

@Service
public class ConsoleUI {

    private final ZooService zooService;

    public ConsoleUI(ZooService zooService) {
        this.zooService = zooService;
    }

    private void ShowMenu() {
        System.out.println("-----------------Menu-----------------");
        System.out.println("Write 1 to add animal");
        System.out.println("Write 2 to print all animals");
        System.out.println("Write 3 to print all inventory");
        System.out.println("Write 4 to print all for contact zoo");
        System.out.println("Write 5 to print how much food needed");
        System.out.println("Write 6 to print animals number");
        System.out.println("Write 7 to print inventory number");
        System.out.println("Write 0 to finish");
        System.out.println("--------------------------------------");
        return;
    }

    private void AddAnimalMenu(Scanner scanner) {
        try {
            System.out.println("-------------Add animal--------------");
            System.out.println("Choose animal: ");
            System.out.println("Monkey - 1");
            System.out.println("Rabbit - 2");
            System.out.println("Tiger  - 3");
            System.out.println("Wolf   - 4");
            System.out.println("number: ");
            String input = scanner.nextLine();
            System.out.println("Write animal id: ");
            int number = Integer.parseInt(scanner.nextLine());
            System.out.println("Write animal name: ");
            String name = scanner.nextLine();
            System.out.println("Write how much food kg needed: ");
            int food = Integer.parseInt(scanner.nextLine());
            System.out.println("Write health (0 if bad, or 1 if good): ");
            boolean health = (Integer.parseInt(scanner.nextLine()) != 0);
            boolean correct = false;
            switch (input) {
                case "1" -> {
                    System.out.println("Write kindness level (1-10): ");
                    int kind = Integer.parseInt(scanner.nextLine());
                    correct = zooService.AddAnimal(new Monkey(number, name, food, health, kind));
                    if (correct) {
                        System.out.println("Animal added");
                    } else {
                        System.out.println("Animal was ill, so it was added to inventory");
                    }
                }
                case "2" -> {
                    System.out.println("Write kindness level (1-10): ");
                    int kind = Integer.parseInt(scanner.nextLine());
                    correct = zooService.AddAnimal(new Rabbit(number, name, food, health, kind));
                    if (correct) {
                        System.out.println("Animal added");
                    } else {
                        System.out.println("Animal was ill, so it was added to inventory");
                    }
                }
                case "3" -> {
                    correct = zooService.AddAnimal(new Tiger(number, name, food, health));
                    if (correct) {
                        System.out.println("Animal added");
                    } else {
                        System.out.println("Animal was ill, so it was added to inventory");
                    }
                }
                case "4" -> {
                    correct = zooService.AddAnimal(new Wolf(number, name, food, health));
                    if (correct) {
                        System.out.println("Animal added");
                    } else {
                        System.out.println("Animal was ill, so it was added to inventory");
                    }
                }
                default -> System.out.println("Error: no animal chosen");
            }
            
            System.out.println("--------------------------------------");
            return;
        } catch (Exception e) {
            System.out.println("ERROR");
            System.out.println("--------------------------------------");
        }

    }

    private void PrintAllAimals() {
        System.out.println("-------------All animals-------------");
        List<Animal> animals = zooService.getAnimalList();
        for (Animal animal : animals) {
            System.out.println("Number: " + animal.getNumber() + " Name: " + animal.getName());
        }
        System.out.println("-------------------------------------");
        return;
    }

    private void PrintAllInventory() {
        System.out.println("------------All inventory------------");
        List<IInventory> inventory = zooService.getInvetnoryList();
        for (IInventory item : inventory) {
            System.out.println("Number: " + item.getNumber() + " Name: " + item.getName());
        }
        System.out.println("-------------------------------------");
        return;
    }

    private void PrintAllContactZoo() {
        System.out.println("-------------Contact zoo-------------");
        List<Animal> animals = zooService.getContactAble();
        for (Animal animal : animals) {
            System.out.println("Number: " + animal.getNumber() + " Name: " + animal.getName());
        }
        System.out.println("-------------------------------------");
        return;
    }

    private void PrintAllFood() {
        System.out.println("-------------Food needed-------------");
        int food = zooService.getAllFood();
        System.out.println("Total food: " + food);
        System.out.println("-------------------------------------");
        return;
    }

    private void PrintAnimalNumber() {
        System.out.println("-----------Animals number------------");
        int animalNumber = zooService.getAnimalNumber();
        System.out.println("Total animals: " + animalNumber);
        System.out.println("-------------------------------------");
        return;
    }

    private void PrintInvetnoryNumber() {
        System.out.println("----------Invemtory number-----------");
        int inventoryNumber = zooService.getInventoryNumber();
        System.out.println("Total invetory: " + inventoryNumber);
        System.out.println("-------------------------------------");
        return;
    }

    public void run(String... args) {
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        // olready in
        zooService.AddAnimal(new Wolf(1, "Helthy wolf", 12, true));
        zooService.AddAnimal(new Tiger(2, "Ill tiger", 100, false));
        zooService.AddAnimal(new Monkey(3, "Angry Monky", 10, true, 2));
        zooService.AddAnimal(new Rabbit(4, "Chill rabbit", 50, true, 7));
        zooService.AddInventory(new Table(777, "Nice table"));
        zooService.AddInventory(new Computer(404, "Old computer"));
        // olready in
        ShowMenu();
        while (running) {
            String input = scanner.nextLine();
            switch (input) {
                case "" -> ShowMenu();
                case "1" -> AddAnimalMenu(scanner);
                case "2" -> PrintAllAimals();
                case "3" -> PrintAllInventory();
                case "4" -> PrintAllContactZoo();
                case "5" -> PrintAllFood();
                case "6" -> PrintAnimalNumber();
                case "7" -> PrintInvetnoryNumber();
                case "0" -> {
                    System.out.println("Ending process...");
                    running = false;
                }
                default -> System.out.println("Error: write number 0 to 7");
            }
        }
        return;
    }
}

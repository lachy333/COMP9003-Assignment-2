package com.group70.zoo;

import java.util.ArrayList;

import com.group70.keeper.Keeper;
import com.group70.animal.Animal;

import com.group70.animal.Lion;
import com.group70.animal.Elephant;
import com.group70.animal.InvalidPortionException;
import com.group70.animal.OverfeedException;
import com.group70.animal.Penguin;
import com.group70.animal.Owl;
import com.group70.keeper.ExpertiseMismatchException;

public class Zoo {
    // List to manage animals
    private ArrayList<Animal> animals;
    // List to manage keeper
    private ArrayList<Keeper> keepers;
    // Automated ID
    private int nextAnimalId = 1;
    private int nextKeeperId = 1;

    /**
     * constructor: initialize the empty animal list and keeper list.
     */
    public Zoo() {
        this.animals = new ArrayList<Animal>();
        this.keepers = new ArrayList<Keeper>();
    }

    /**
     * Assigns a keeper to a specific animal using their IDs.
     * If either ID is invalid, an error message is shown.
     */


    public void assignKeeperToAnimal(String keeperID, String animalID) {
        // Find keeper by keeperID
        Keeper keeper = findKeeperById(keeperID);
        if (keeper == null){
            System.out.println("Can't find this keeper: " + keeperID);
            return;
        }

        // Find animal by animalID
        Animal animal = findAnimalById(animalID);
        if (animal == null) {
            System.out.println("Can't find this animal: " + animalID);
            return;
        }
        
        try {
            keeper.assignAnimal(animal);
        } catch (ExpertiseMismatchException e) {
            System.out.println("Keeper " + keeperID + " lacks expertise for species: " + animal.getSpecies());
        } 

    }

    /**
     * Feeds the specified animal with a custom food portion.
     * Overloaded version of feedAnimal().
     */

    public void feedAnimal(String animalID) {
        // find animal by animal ID
        Animal a = findAnimalById(animalID);
            if (a == null){
                System.out.println("Can't find this animal: " + animalID);
                return;
            }
            
            try {
                a.feed(a.getDietProfile());
                System.out.println("Already fed: " + animalID);
            } catch (InvalidPortionException | OverfeedException e) {
                System.out.println("Failed to feed: " + e.getMessage());
            } catch (Exception e){
                System.out.println("Unexpected error: " + e.getMessage());
            }
    }
    

    /**
     * Feed the specified animal with the given portion size (kg)
     * If an exception occurs (e.g. invalid portion or overfeeding),
     * an error message is displayed and the program continues
     */

    public void feedAnimal(String animalID, double portionKg) {
        // find animal by animal ID
        Animal a = findAnimalById(animalID);
            if (a == null){
                System.out.println("Can't find this animal: " + animalID);
                return;
            }
            
            try {
                a.feed(a.getDietProfile(), portionKg);
                System.out.println("Already fed (" + portionKg + "kg): " + animalID);
            } catch (InvalidPortionException | OverfeedException e) {
                System.out.println("Failed to feed(" + portionKg + " kg): " + e.getMessage());
            } catch (Exception e){
                System.out.println("Unexpected error: " + e.getMessage());
            }
    }

    /**
     * Displays all animals in the list
     */

    public void displayAllAnimals() {
        if (animals.isEmpty()){
            System.out.println("No animals in the zoo.");
            return;
        }

        System.out.println("=== Animal List ===");
        for (Animal a : animals){
            if (a != null){
                System.out.println("Animal ID: " + a.getAnimalID());
                System.out.println("Name: " + a.getName());
                System.out.println("Species: " + a.getSpecies());
                System.out.println("Weight: " + a.getWeightKg() + " kg");
                System.out.println("Diet Profile: " + a.getDietProfile());
                System.out.println("Required Meals Per Day: " + a.getRequiredMealsPerDay());
                System.out.println("----------------------------");
            }
        }
    }

    


    /**
     * Displays all keepers in the list
     */

    public void displayAllKeepers() {
        if (keepers.isEmpty()){
            System.out.println("No keepers in the zoo.");
            return;
        }

        System.out.println("=== Keeper List ===");
        for (Keeper k : keepers){
            if (k != null){
                System.out.println("Keeper ID: " + k.getKeeperID());
                System.out.println("Name: " + k.getName());
                System.out.println("Expertise: " + k.getExpertise().toString());
                if (k.getAssignedAnimals().isEmpty()){
                    System.out.println("Assigned animals: (none)");
                } else{
                    System.out.println("Assigned animals: ");
                    for (Animal a : k.getAssignedAnimals()){
                        if (a != null){
                            System.out.println(" - " + a.getAnimalID() + "( " + a.getName() + ", " + a.getSpecies() + ")");
                        }
                    }
                }
                System.out.println("----------------------------");
            }
        }
    }

    /**
     * Display the number of total animals, total keepers, total meals fed. 
     */
    
    public void summary() {
        int totalAnimals = animals.size();
        int totalKeepers = keepers.size();
        int totalMeals = 0;

        for (Animal a : animals){
            if (a != null){
                totalMeals += a.getFeedCount();
            }
        }

        System.out.println("=== Zoo Summary ===");
        System.out.println("Total animals: " + totalAnimals);
        System.out.println("Total keepers: " + totalKeepers);
        System.out.println("Total meals fed: " + totalMeals);
        System.out.println("====================");
    
    }

    /**
     * Add the new animal to the list
     * If the error occurs, catch the exception and print it
     */
    
    public void addAnimal(String name, String species, double weightKg) {
        if (name == null || name.isEmpty()|| species == null || species.isEmpty() || weightKg <= 0){
            System.out.println("Invalid input for adding animal. Please check name, species, and weight.");
            return;
        }

        //add ID automatically
        String id = species.substring(0,1) + String.valueOf(nextAnimalId++);
        
        Animal animal = createAnimalBySpecies(id, name, species, weightKg);

        if (animal == null){
            System.out.println("Unexpected species: " + species);
            return;
        } 

        animals.add(animal);

        System.out.println("Added animal with ID: " + id);
        System.out.println("Name: " + name + ", Species: " + species + ", Weight: " + weightKg + "kg" + ", dietProfile: " 
                            + animal.getDietProfile() + ", requiredMealsPerDay: " + animal.getRequiredMealsPerDay());

    }

    /* //Input animal
    public void addAnimalInteractive(Scanner sc){
        System.out.println("Available species: Lion/ Elephant/ Penguin/ Owl");

        System.out.print("Animal name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()){
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.print("Species: ");
        String species = sc.nextLine().trim();
        if (species.isEmpty()){
            System.out.println("Species cannnot be empty.");
            return;
        }

        System.out.print("Weight (kg): ");
        String w = sc.nextLine().trim();
        double weight;
        try {
            weight = Double.parseDouble(w);
            if (weight <= 0){
                System.out.println("Weight must be positive.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number for weight.");
            return;
        }

        String id = String.valueOf(nextAnimalId++);
        Animal a = createAnimalBySpecies(id, name, species, weight);
        
        if (a == null){
            System.out.println("Unexpected species: " + species);
            return;
        }

        addAnimal(a);
        
    }
    */

    /**
     * Add the new keeper to the list
     */
    
    public void addKeeper(String name, ArrayList<String> expertiseSpecies){
        if (name == null || name.isEmpty()|| expertiseSpecies == null || expertiseSpecies.size() == 0){
            System.out.println("Invalid input for adding keeper. Please check name, expertise species.");
            return;
        }

        //add ID automatically
        String id = "K" + String.valueOf(nextKeeperId++);
        Keeper newKeeper = new Keeper(id, name, expertiseSpecies);
        
        keepers.add(newKeeper);

        System.out.println("Added Keeper with ID: " + id);
        System.out.println("Name: " + name + ", Species: " + expertiseSpecies.toString());


    }

    /* //Input Keeper
    public void addKeeperInteractive(Scanner sc){
        String keeperId = "K" + (nextKeeperId++);

        System.out.print("Keeper name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()){
            System.out.println("Name cannnot be empty.");
            return;
        }

        Keeper k = new Keeper(keeperId, name);

        System.out.println("Add expertise species (comma separated, e.g., Lion, Elephant): ");
        System.out.print("Expertise: ");
        String line = sc.nextLine().trim();
        if (!line.isEmpty()){
            for (String s : line.split(",")){
                String sp = s.trim();
                if (!sp.isEmpty()) k.addExpertise(sp);
            }
        }

        addKeeper(k);
        System.out.println("Add keeper: " + keeperId + "(" + name + ") expertise " + k.getExpertise());
    }
    */

    //Creating Animal Object
    private Animal createAnimalBySpecies(String id, String name, String species, double weight) {
        switch (species.toLowerCase()) {
            case "lion":
                return new Lion(id, name, species, weight, "CARNIVORE", 2, 0);
            case "elephant":
                return new Elephant(id, name, species, weight, "HERBIVORE", 3, 0);
            case "penguin":
                return new Penguin(id, name, species, weight, "OMNIVORE", 3, 0);
            case "owl":
                return new Owl(id, name, species, weight, "CARNIVORE", 2, 0);
            default:
                return null;
        }
    }
    

    /**
     * Helper method to find an animal by its ID
     * Returns null if no match is found.
     */
    
    private Animal findAnimalById(String animalID){
        for (Animal a: animals){
            if (a != null && a.getAnimalID().equals(animalID)){
                return a;
            }
        }
        return null;
    }

    /*

    //Checking the same animal ID
    private boolean existsAnimalId(String id){
        for (Animal a: animals){
            if (a != null && id.equals(a.getAnimalID())){
                return true;
            }
        }
        return false;
    }

    */

    /**
     * Helper method to find a keeper by their ID
     * Returns null if no match is found.
     */
    
    private Keeper findKeeperById(String keeperID){
        for (Keeper k: keepers){
            if(k != null && k.getKeeperID().equals(keeperID)){
                return k;
            }
        }
        return null;
    }

    /**
     * Remove the specific animal from the zoo based on its ID
     */

    public void removeAnimal(String animalID) {
        Animal a = findAnimalById(animalID);
        if (a == null){
            System.out.println("Animal not found: " + animalID);
            return;
        }
        animals.remove(a);
        System.out.println("Removed animal: " + animalID);
    }

    /**
     * Remove the specific keeper from the zoo based on its ID
     */
    public void removeKeeper(String keeperID){
        Keeper t = findKeeperById(keeperID);
        if (t == null){
            System.out.println("Keeper not found: " + keeperID);
            return;
        }
        keepers.remove(t);
        System.out.println("Removed keeper: " + keeperID);
    }


}
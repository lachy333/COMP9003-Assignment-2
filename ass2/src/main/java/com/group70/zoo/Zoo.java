package com.group70.zoo;

import java.util.ArrayList;

import com.group70.Keeper.Keeper;

public class Zoo {
    // List to manage animals
    private ArrayList<Animal> animals;
    // List to manage keeper
    private ArrayList<Keeper> keepers;

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
     * Handles ExpertiseMismatchException if keeper lacks expertise.
     */


    public void assignKeeperToAnimal(String keeperID, String animalID) {
        // Find keeper by keeperID
        Keeper keeper = findKeeperById(keeperID);
        if (keeper == null){
            System.out.println("Can't find this keeper: " + keeperID);
            return;
        }

        // Find animal by animaID
        Animal animal = findAnimalById(animalID);
        if (animal == null){
            System.out.println("Can't find this animal: " + animalID);
            return;
        }

        // Try to assign the keeper to the animal
        try {
            keeper.assignAnimal(animal);
            System.out.println("Assigned keeper " + keeperID + " to animal " + animalID);
            
        } catch (ExpertiseMismatchException e) {
            System.out.println("Assignment failed: " + e.getMessage());
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
                a.feed();
                System.out.println("Already fed: " + animalID);
            } catch (Exception e) {
                System.out.println("Failed to feed: " + e.getMessage());
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
                a.feed(portionKg);
                System.out.println("Already fed (" + portionKg + "kg): " + animalID);
            } catch (Exception e) {
                System.out.println("Failed to feed(" + portionKg + " kg): " + e.getMessage());
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

        System.out.println("===Animal List===");
        for (Animal a : animals){
            if (a != null){
                System.out.println("Animal ID: " + a.getAnimalID());
                System.out.println("Name: " + a.getName());
                System.out.println("Species: " + a.getSpecies());
                System.out.println("Weight: " + a.getWeight() + " kg");
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

        System.out.println("===Keeper List===");
        for (Keeper k : keepers){
            if (k != null){
                System.out.println("Keeper ID: " + k.getKeeperID());
                System.out.println("Name: " + k.getName());
                System.out.println("Expertise: " + k.getExpertise());
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

        System.out.println("===Zoo Summary===");
        System.out.println("Total animals: " + totalAnimals);
        System.out.println("Total keepers: " + totalKeepers);
        System.out.println("Total meals fed: " + totalMeals);
        System.out.println("====================");
    
    }

    /**
     * Add the new animal to the list
     * If the error occurs, catch the exception and print it
     */
    
    public void addAnimal(Animal animal) {
        try {
            this.animals.add(animal);
        } catch (Exception e) {
            System.out.println("Error adding animal: " + e.getMessage());
        }

    }

    /**
     * Add the new keeper to the list
     */
    
    public void addKeeper(Keeper keeper){
        try {
            this.keepers.add(keeper);
        } catch (Exception e) {
            System.out.println("Error adding keeper: " + e.getMessage());
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

    

}
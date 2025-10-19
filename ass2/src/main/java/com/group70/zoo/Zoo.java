package com.group70.zoo;

import java.util.ArrayList;

public class Zoo {
    private ArrayList<Animal> animals;
    private ArrayList<Keeper> keepers;

    public Zoo() {
        this.animals = new ArrayList<Animal>();
        this.keepers = new ArrayList<Keeper>();
    }


    public assignKeeperToAnimal(String keeperID, String animalID) {
        //TODO:


    }

    public feedAnimal(String animalID) {
        // TODO:


    }

    public feedAnimal(String animalID, double portionKg) {
        // TODO:


    }

    public displayAllAnimals() {
        // TODO:

    }

    public displayAllKeepers() {
    
    
    }

    public summary() {
        //TODO: shows a summary of totla animals, keepers, and meals fed.
    
    }

    public addAnimal(Animal animal) {
        try {
            this.animals.add(animal);
        } catch (Exception e) {
            System.out.println("Error adding animal: " + e.getMessage());
        }

    }

    public removeAnimal(String animalID) {
        
    }

    

}



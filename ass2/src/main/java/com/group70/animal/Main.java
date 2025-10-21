package com.group70.animal;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        // Create specific animal objects
        Lion simba = new Lion("A001", "Simba", "Lion", 180.0, "CARNIVORE", 2, 0);
        Elephant dumbo = new Elephant("A002", "Dumbo", "Elephant", 1200.0, "HERBIVORE", 3, 0);
        Penguin pingo = new Penguin("A003", "Pingo", "Penguin", 25.0, "OMNIVORE", 2, 0);
        Owl hedwig = new Owl("A004", "Hedwig", "Owl", 3.2, "CARNIVORE", 2, 0);

        // Store them in an ArrayList of Animal type
        ArrayList<Animal> zooAnimals = new ArrayList<>();
        zooAnimals.add(simba);
        zooAnimals.add(dumbo);
        zooAnimals.add(pingo);
        zooAnimals.add(hedwig);

        // Feed some animals
        try {
            simba.feed("CARNIVORE");
            dumbo.feed("HERBIVORE", 15.0);
            pingo.feed("OMNIVORE");
        } catch (InvalidPortionException | OverfeedException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Display all animal statuses
        System.out.println("\n=== Animal Status ===");
        for (Animal a : zooAnimals) {
            a.displayStatus();
            System.out.println();
        }
    }
}



package com.group70.keeper;

import java.util.ArrayList;

import com.group70.animal.Animal;

public class Keeper {
    // Fields
    private String keeperID;
    private String name;
    private ArrayList<String> expertiseSpecies;
    private ArrayList<Animal> assignedAnimals;

    // Constructor
    public Keeper(String keeperID, String name, ArrayList<String> expertiseSpecies) {
        this.keeperID = keeperID;
        this.name = name;
        this.expertiseSpecies = expertiseSpecies;
        this.assignedAnimals = new ArrayList<Animal>();
    }

    // Operation for Expertise
    // **Add the species that the keeper can handle */
    public void addExpertises(ArrayList<String> species) {
        if (species == null) {
            return;
        }
        for (String s : species) {
            addExpertise(s);
        }
        
    }

    private void addExpertise(String species) {
        if (species == null) {
            return;
        }

        String s = species.trim();
        if (s.length() == 0) {
            return;
        }

        // Add when it is not added yet
        boolean exists = false;
        for (String e : expertiseSpecies) {
            if (e.equalsIgnoreCase(s)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            expertiseSpecies.add(s);
        }
       
    }

    /**
     * Add an animal to this keeper.
     * Throws an exception if the keeper does not have the required expertise.
     *  
     */

    public void assignAnimal(Animal a) throws ExpertiseMismatchException {
        if (a == null) {
            return;
        }
        String species = a.getSpecies();

        // Check expertise
        boolean ok = false;
        for (String e : expertiseSpecies) {
            if (e.equalsIgnoreCase(species)) {
                ok = true;
                break;
            }
        }
        if (!ok) {
            throw new ExpertiseMismatchException(
                    "Keeper " + keeperID + " lacks expertise for species: " + species);
        }

        // Add if it is not already assigned
        boolean alreadyAssigned = false;
        for (Animal x : assignedAnimals) {
            if (x == a) {
                alreadyAssigned = true;
                break;
            }
        }
        if (!alreadyAssigned) {
            assignedAnimals.add(a);
            System.out.println("Animal " + a.getAnimalID() + " assigned to Keeper " + keeperID + ".");
        }

    }

    /**
     * display all assigned animals
     */

    public void displayAssignedAnimals() {
        if (assignedAnimals.isEmpty()) {
            System.out.println("No animals are assigned to the keeper " + keeperID + ".");
            return;
        }
        System.out.println("Assigned animals of " + keeperID + "(" + name + ")");
        for (Animal a : assignedAnimals) {
            if (a != null) {
                System.out.println("- " + a.getAnimalID() + " | " + a.getName() + " | " + a.getSpecies());

            }
        }
    }

    // Getter
    public String getKeeperID() {
        return keeperID;
    }

    public String getName() {
        return name;
    }

    /**
     * Converts the list of expertise into a string for Zoo.displayAllKeeper()
     */

    public String getExpertise() {
        if (expertiseSpecies.isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < expertiseSpecies.size(); i++) {
            sb.append(expertiseSpecies.get(i));
            if (i < expertiseSpecies.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public ArrayList<Animal> getAssignedAnimals() {
        return assignedAnimals;
    }

    @Override
    public String toString() {
        return "Keeper{id=" + keeperID + ", name=" + name + ", expertise=" + getExpertise() + "}";
    }
}

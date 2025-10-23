package com.group70.animal;

public abstract class Animal {

    // Fields
    private String animalId;
    private String name;
    private String species;
    private double weightkg;
    private String dietProfile;
    private int requiredMealsPerDay;
    private int feedCount;

    // Constructor
    public Animal(String animalId, String name, String species, double weightkg,
                  String dietProfile, int requiredMealsPerDay, int feedCount) {
        this.animalId = animalId;
        this.name = name;
        this.species = species;
        this.weightkg = weightkg;
        this.dietProfile = dietProfile;
        this.requiredMealsPerDay = requiredMealsPerDay;
        this.feedCount = feedCount;
    }

    public void feed(String food, double portionKg) 
        throws InvalidPortionException, OverfeedException {
    
        // Validate portion
        if (portionKg <= 0) {
            throw new InvalidPortionException("Portion must be positive.");
        }

        // Check daily feeding limit
        if (feedCount >= requiredMealsPerDay) {
            throw new OverfeedException(name + " has already been fed the required number of meals today.");
        }

        // Validate diet profile (use equalsIgnoreCase for string comparison)
        if (food.equalsIgnoreCase(dietProfile)) {
            setFeedCount(getFeedCount() + 1);
            System.out.println(name + " has been fed " + portionKg + " kg of " + food + ".");
        } else {
            System.out.println("Incorrect food type for " + name + ". Expected: " + dietProfile);
        }
    }


    public void feed(String food) 
        throws InvalidPortionException, OverfeedException {
    double portion = dailyPortionKg() / requiredMealsPerDay;  // Default portion
    feed(food, portion);  // Call overloaded version
    }


    public double dailyPortionKg() {
        /* Returns the daily proportion of food required by the animal */
        return 0;
    }

    public void displayStatus() {
        System.out.println("Animal ID: " + animalId);
        System.out.println("Name: " + name);
        System.out.println("Species: " + species);
        System.out.println("Weight (kg): " + weightkg);
        System.out.println("Diet: " + dietProfile);
        System.out.println("Meals Fed: " + feedCount + "/" + requiredMealsPerDay);
    }
    

    // Getter Methods
    public String getAnimalID() {
        return animalId;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public double getWeightKg() {
        return weightkg;
    }

    public String getDietProfile() {
        return dietProfile;
    }

    public int getRequiredMealsPerDay() {
        return requiredMealsPerDay;
    }

    public int getFeedCount() {
        return feedCount;
    }

    // Setter Methods
    public void setAnimalID(String animalId) {
        this.animalId = animalId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setWeightkg(double weightkg) {
        this.weightkg = weightkg;
    }

    public void setDietProfile(String dietProfile) {
        this.dietProfile = dietProfile;
    }

    public void setRequiredMealsPerDay(int requiredMealsPerDay) {
        this.requiredMealsPerDay = requiredMealsPerDay;
    }

    public void setFeedCount(int feedCount) {
        this.feedCount = feedCount;
    }

    public static void main(String[] args) {

    }
    
}



package com.group70.animal;

public class Penguin extends Animal {

    public Penguin(String animalId, String name, String species, double weightkg,
                   String dietProfile, int requiredMealsPerDay, int feedCount) {
        super(animalId, name, species, weightkg, dietProfile, requiredMealsPerDay, feedCount);
    }

    @Override
    public double dailyPortionKg() {
        return 0.06 * getWeightKg(); // Penguins eat 6% of their body weight daily
    }
}


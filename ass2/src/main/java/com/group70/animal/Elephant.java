package com.group70.animal;

public class Elephant extends Animal {

    public Elephant(String animalId, String name, String species, double weightkg,
                    String dietProfile, int requiredMealsPerDay, int feedCount) {
        super(animalId, name, species, weightkg, dietProfile, requiredMealsPerDay, feedCount);
    }

    @Override
    public double dailyPortionKg() {
        return 0.03 * getWeightKg(); // Elephants eat 3% of their body weight daily
    }
}

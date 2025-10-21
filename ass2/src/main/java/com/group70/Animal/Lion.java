package com.group70.animal;

public class Lion extends Animal {

    public Lion(String animalId, String name, String species, double weightkg,
                String dietProfile, int requiredMealsPerDay, int feedCount) {
        super(animalId, name, species, weightkg, dietProfile, requiredMealsPerDay, feedCount);
    }

    @Override
    public double dailyPortionKg() {
        return 0.05 * getWeightkg(); // Lions eat 5% of their body weight daily
    }
}


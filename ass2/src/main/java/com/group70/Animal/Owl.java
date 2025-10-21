package com.group70.animal;

public class Owl extends Animal {

    public Owl(String animalId, String name, String species, double weightkg,
               String dietProfile, int requiredMealsPerDay, int feedCount) {
        super(animalId, name, species, weightkg, dietProfile, requiredMealsPerDay, feedCount);
    }

    @Override
    public double dailyPortionKg() {
        return 0.08 * getWeightkg(); // Owls eat 8% of their body weight daily
    }
}


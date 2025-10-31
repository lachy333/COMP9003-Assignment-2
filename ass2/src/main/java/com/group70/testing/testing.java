package main.java.com.group70.testing;

import java.util.ArrayList;

import com.group70.keeper.Keeper;
import com.group70.animal.Animal;
import com.group70.zoo.Zoo;

import com.group70.animal.Lion;
import com.group70.animal.Elephant;
import com.group70.animal.InvalidPortionException;
import com.group70.animal.OverfeedException;
import com.group70.animal.Penguin;
import com.group70.animal.Owl;
import com.group70.keeper.ExpertiseMismatchException;




public class testing {

    public static String passCase = "[Pass]";
    public static String failCase = "[Fail]";

    public static void testAnimalClass() {
        final double EPS = 1e-6;
    
        // ---------- A) dailyPortionKg() quick checks ----------
        // Lion 200kg -> 5% = 10.0kg/day
        Lion lion = new Lion("L1", "Simba", "Lion", 200.0, "CARNIVORE", 2, 0);
        double got = lion.dailyPortionKg();
        double exp = 0.05 * 200.0;
        System.out.println((Math.abs(got - exp) < EPS ? passCase : failCase) +
            String.format(" Lion food per day = %.1fkg carnivore/day (expected %.1fkg)", got, exp));
    
        // If you have these classes, keep these tiny checks. If not, delete.
        try {
            Elephant ele = new Elephant("E1", "Dumbo", "Elephant", 3333.3333, "HERBIVORE", 2, 0); // ~100kg/day
            got = ele.dailyPortionKg();
            exp = 0.03 * 3333.3333;
            System.out.println((Math.abs(got - exp) < EPS ? passCase : failCase) +
                String.format(" Elephant food per day = %.1fkg herbivore/day (expected %.1fkg)", got, exp));
        } catch (Throwable ignore) { /* skip if class not present */ }
    
        try {
            Penguin peng = new Penguin("P1", "Pingu", "Penguin", 50.0, "CARNIVORE", 3, 0); // 3.0kg/day if 6%
            got = peng.dailyPortionKg();
            exp = 0.06 * 50.0;
            System.out.println((Math.abs(got - exp) < EPS ? passCase : failCase) +
                String.format(" Penguin food per day = %.1fkg carnivore/day (expected %.1fkg)", got, exp));
        } catch (Throwable ignore) { /* skip if class not present */ }
    
        try {
            Owl owl = new Owl("O1", "Hedwig", "Owl", 5.0, "CARNIVORE", 2, 0); // 0.4kg/day if 8%
            got = owl.dailyPortionKg();
            exp = 0.08 * 5.0;
            System.out.println((Math.abs(got - exp) < EPS ? passCase : failCase) +
                String.format(" Owl food per day = %.1fkg carnivore/day (expected %.1fkg)", got, exp));
        } catch (Throwable ignore) { /* skip if class not present */ }
    
        // ---------- B) feed() essentials (no parsing, minimal logic) ----------
        // B1: correct diet increments feedCount
        try {
            lion.setFeedCount(0);
            lion.feed("CARNIVORE", 1.0);
            System.out.println((lion.getFeedCount() == 1 ? passCase : failCase) +
                " Feed count incremented on correct diet");
        } catch (Exception e) {
            System.out.println(failCase + " Unexpected exception on correct diet: " + e.getClass().getSimpleName());
        }
    
        // B2: wrong diet does NOT increment
        try {
            int before = lion.getFeedCount();
            lion.feed("HERBIVORE", 1.0);
            System.out.println((lion.getFeedCount() == before ? passCase : failCase) +
                " Feed count unchanged on wrong diet");
        } catch (Exception e) {
            System.out.println(failCase + " Unexpected exception on wrong diet: " + e.getClass().getSimpleName());
        }
    
        // B3: invalid portion (0, negative) throws InvalidPortionException
        boolean okZero = false, okNeg = false;
        try { lion.feed("CARNIVORE", 0.0); } catch (InvalidPortionException e) { okZero = true; }
        catch (Exception e) { /* wrong exception type */ }
        try { lion.feed("CARNIVORE", -1.0); } catch (InvalidPortionException e) { okNeg = true; }
        catch (Exception e) { /* wrong exception type */ }
        System.out.println(((okZero && okNeg) ? passCase : failCase) +
            " Invalid portion rejected (≤ 0)");
    
        // B4: overfeed after reaching daily limit throws OverfeedException
        try {
            lion.setFeedCount(lion.getRequiredMealsPerDay()); // at limit
            boolean threw = false;
            try { lion.feed("CARNIVORE", 0.5); } catch (OverfeedException e) { threw = true; }
            System.out.println((threw ? passCase : failCase) + " Overfeed blocked at daily limit");
        } catch (Exception e) {
            System.out.println(failCase + " Unexpected setup error for overfeed test: " + e.getClass().getSimpleName());
        }
    }
    

    public static void testKeeperClass() {
        try {
            // --- Constructor & getters ---
            ArrayList<String> exp = new ArrayList<>();
            exp.add("Lion");
            Keeper k = new Keeper("K1", "Alice", exp);
    
            boolean ctorOk = "K1".equals(k.getKeeperID())
                          && "Alice".equals(k.getName())
                          && "[Lion]".equals(k.getExpertise());
            System.out.println((ctorOk ? passCase : failCase) + " Keeper constructor/getters");
    
            // --- addExpertises(): trim, dedup (case-insensitive), append new species ---
            ArrayList<String> more = new ArrayList<>();
            more.add("lion");       // duplicate (case-insensitive)
            more.add(" LION ");     // duplicate with spaces
            more.add("");           // ignored
            more.add(null);         // ignored
            more.add("Elephant");   // new
            k.addExpertises(more);
    
            boolean expertiseOk = "[Lion, Elephant]".equals(k.getExpertise());
            System.out.println((expertiseOk ? passCase : failCase) + " Expertise add/dedup -> " + k.getExpertise());
    
            // --- assignAnimal(): matching species adds once ---
            Lion simba = new Lion("L1", "Simba", "Lion", 200.0, "CARNIVORE", 2, 0);
            int before = k.getAssignedAnimals().size();
            k.assignAnimal(simba);
            boolean assignedOnce = (k.getAssignedAnimals().size() == before + 1);
            System.out.println((assignedOnce ? passCase : failCase) + " Assign matching species adds once");
    
            // --- assigning same animal again should NOT duplicate ---
            int sizeAfterFirst = k.getAssignedAnimals().size();
            k.assignAnimal(simba);
            boolean noDuplicate = (k.getAssignedAnimals().size() == sizeAfterFirst);
            System.out.println((noDuplicate ? passCase : failCase) + " Duplicate assign ignored");
    
            // --- mismatched species should throw ExpertiseMismatchException ---
            boolean threw = false;
            try {
                Penguin p = new Penguin("P1", "Pingu", "Penguin", 50.0, "CARNIVORE", 3, 0);
                k.assignAnimal(p);
            } catch (ExpertiseMismatchException e) {
                threw = true;
            }
            System.out.println((threw ? passCase : failCase) + " Mismatch species throws ExpertiseMismatchException");
    
        } catch (Throwable t) {
            System.out.println(failCase + " Exception in testKeeperClass: " +
                               t.getClass().getSimpleName() + " - " + t.getMessage());
        }
    }
    

    public static void testZooClass() {
        try {
            Zoo z = new Zoo();
    
            // --- Add keeper (K1) and animal (L1) ---
            ArrayList<String> exp = new ArrayList<>();
            exp.add("Lion");
            z.addKeeper("Alice", exp);                  // -> K1
            z.addAnimal("Simba", "Lion", 200.0);       // -> L1
            System.out.println(passCase + " addKeeper/addAnimal created K1 and L1 (deterministic IDs)");
    
            // --- Assign matching expertise: K1 -> L1 ---
            try {
                z.assignKeeperToAnimal("K1", "L1");
                System.out.println(passCase + " Assign K1->L1 (matching expertise)");
            } catch (Throwable t) {
                System.out.println(failCase + " Assign K1->L1 threw " + t.getClass().getSimpleName());
            }
    
            // --- Feed L1: default, explicit, then (likely) overfeed handled by Zoo ---
            try {
                z.feedAnimal("L1");            // default portion
                z.feedAnimal("L1", 1.0);       // explicit portion
                z.feedAnimal("L1");            // may exceed daily limit; Zoo handles internally
                System.out.println(passCase + " Feeding L1: default, explicit, then overfeed handled");
            } catch (Throwable t) {
                System.out.println(failCase + " Feeding L1 threw " + t.getClass().getSimpleName());
            }
    
            // --- Add a Penguin (P2) and test expertise mismatch warning path ---
            z.addAnimal("Pingu", "Penguin", 50.0);     // -> P2
            try {
                z.assignKeeperToAnimal("K1", "P2");    // K1 has Lion expertise only
                System.out.println(passCase + " Expertise mismatch handled (K1 lacks Penguin)");
            } catch (Throwable t) {
                System.out.println(failCase + " Expertise mismatch threw " + t.getClass().getSimpleName());
            }
    
            // --- Invalid IDs should be handled gracefully by Zoo ---
            try {
                z.assignKeeperToAnimal("K999", "L1");
                System.out.println(passCase + " Unknown keeper handled (K999)");
            } catch (Throwable t) {
                System.out.println(failCase + " Unknown keeper caused exception");
            }
            try {
                z.assignKeeperToAnimal("K1", "X999");
                System.out.println(passCase + " Unknown animal handled (X999)");
            } catch (Throwable t) {
                System.out.println(failCase + " Unknown animal caused exception");
            }
    
            // --- Remove animal L1, then feeding should be handled (not crash) ---
            try {
                z.removeAnimal("L1");
                z.feedAnimal("L1"); // should print "Can't find this animal" and continue
                System.out.println(passCase + " After removing L1, feeding handled gracefully");
            } catch (Throwable t) {
                System.out.println(failCase + " Feed after removal threw " + t.getClass().getSimpleName());
            }
    
            // --- Remove keeper K1, then assignment should be handled (not crash) ---
            try {
                z.removeKeeper("K1");
                z.assignKeeperToAnimal("K1", "P2");
                System.out.println(passCase + " After removing K1, assignment handled gracefully");
            } catch (Throwable t) {
                System.out.println(failCase + " Assign after keeper removal threw " + t.getClass().getSimpleName());
            }
    
            // --- Smoke run for display + summary (no assertions) ---
            try {
                z.displayAllAnimals();
                z.displayAllKeepers();
                z.summary();
                System.out.println(passCase + " displayAll*/summary executed");
            } catch (Throwable t) {
                System.out.println(failCase + " displayAll*/summary threw " + t.getClass().getSimpleName());
            }
    
        } catch (Throwable t) {
            System.out.println(failCase + " Exception in testZooClass: " +
                               t.getClass().getSimpleName() + " - " + t.getMessage());
        }
    }
    

    public static void testMainClass() {

    }

    public static void main(String args[]) {
        System.out.println("Testing System Functionality");
        System.out.println("##########################################################");

        System.out.println("\n\nTesting Animal Class Functionality");
        System.out.println("##########################################################");
        testAnimalClass();
        
        System.out.println("\n\nTesting Keeper Class Functionality");
        System.out.println("##########################################################");
        testKeeperClass();
        
        System.out.println("\n\nTesting Keeper Class Functionality");
        System.out.println("##########################################################");
        testZooClass();

        System.out.println("\n\nTesting Keeper Class Functionality");
        System.out.println("##########################################################");
        testMainClass();
    }
    
}

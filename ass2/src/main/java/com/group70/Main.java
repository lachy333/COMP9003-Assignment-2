package com.group70;

import java.util.ArrayList;
import java.util.Scanner;
import com.group70.zoo.Zoo;

/*
 * 7.	User Interaction (Main Class)
Create a menu-driven Main class that allows users to:
•	View zoo status (animals and keepers)
•	Add or remove animals
•	Add or remove keepers
•	Assign a keeper to an animal
•	Feed an animal (default or specified portion)
•	Exit the program
Requirements:
•	Use Scanner for user input.
•	Validate input (positive weights, no duplicates, valid IDs).
•	Catch and handle exceptions with clear messages.
•	Maintain updated data and print confirmation for each operation.
 
 */

public class Main {

    public static void main(String[] args) {
        programStart(args);
    }

    public static void programStart(String[] args) {

        Scanner scan = new Scanner(System.in);
        Zoo zoo = new Zoo();
        boolean running = true;

        while (running) {

            System.out.println("Menu:");
            System.out.println("1. View Zoo Status");
            System.out.println("2. Add/Remove Animals");
            System.out.println("3. Add/Remove Keepers");
            System.out.println("4. Assign Keeper to Animal");
            System.out.println("5. Feed an animal");
            System.out.println("6. Exit");
            System.out.print("Please Enter Menu Number: ");

            try {

                String menuChoice = scan.nextLine();

                switch (Integer.parseInt(menuChoice)) {
                    case 1:
                        zoo.displayAllAnimals();
                        zoo.displayAllKeepers();
                        break;
                    case 2:
                        animalMenu(scan, zoo);
                        break;

                    case 3:
                        keeperMenu(scan, zoo);
                        break;

                    case 4:
                        assign(scan, zoo);
                        break;

                    case 5:
                        feed(scan, zoo);
                        break;

                    case 6:
                        running = false;
                        scan.close();
                        System.out.println("System Terminated.");
                        break;

                    default:
                        System.out.println("Invalid menu number. Please enter valid menu number.");

                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid menu number. Please enter valid menu number.");
            }

            catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }

    }

    public static void animalMenu(Scanner scan, Zoo zoo) {

        while (true) {
            System.out.println("2-1. To add animal, enter 1");
            System.out.println("2-2. To remove animal, enter 2");
            System.out.println("To move back to main menu, enter 3");

            String animalChoice = scan.nextLine();
            switch (Integer.parseInt(animalChoice)) {
                case 1:
                    System.out.println("Available animal species: Lion, Elephant, Penguin, Owl.");
                    System.out.println("Please type an animal information to add using below format:");
                    System.out.println(
                            "input details in order of animal name, species(from above), weightKg using , as separator.");
                    System.out.println("e.g., Simba, Lion, 100.5");
                    String animalInfo = scan.nextLine();
                    animalInfo = animalInfo.replaceAll(" ", "");
                    String[] columns = animalInfo.split(",");
                    if (columns.length != 3) {
                        System.out.println(
                                "Invalid input format. Please, try again with animal name, species, wightKg.");
                        break;
                    } else {
                        zoo.addAnimal(columns[0], columns[1], Double.parseDouble(columns[2]));
                        break;
                    }

                case 2:
                    System.out.print("Enter Animal ID to remove: ");
                    String animalID = scan.nextLine();
                    zoo.removeAnimal(animalID);
                    break;

                case 3:
                    System.out.println("Returning to main menu.");
                    return;

                default:
                    System.out.println("Invalid menu number. Please enter valid menu number.");
                    break;
            }
        }
    }

    public static void keeperMenu(Scanner scan, Zoo zoo) {
        while (true) {
            System.out.println("3-1. To add keeper, enter 1");
            System.out.println("3-2. To remove keeper, enter 2");
            System.out.println("To move back to main menu, enter 3");
            String keeperChoice = scan.nextLine();
            switch (Integer.parseInt(keeperChoice)) {
                case 1:
                    System.out.print("Enter Keeper Name: ");
                    String keeperName = scan.nextLine();
                    System.out.print(
                            "Enter expertise species(Lion, Elephant, Penguin, Owl) using , as separator: ");
                    String keeperExp = scan.nextLine();
                    keeperExp = keeperExp.replaceAll(" ", "");
                    ArrayList<String> keeperColumns = new ArrayList<String>();
                    for (String s : keeperExp.split(",")) {
                        keeperColumns.add(s);
                    }

                    zoo.addKeeper(keeperName, keeperColumns);
                    break;

                case 2:
                    System.out.print("Enter Keeper ID to remove: ");
                    String keeperID = scan.nextLine();
                    zoo.removeKeeper(keeperID);
                    break;

                case 3:
                    System.out.println("Returning to main menu.");
                    return;

                default:
                    System.out.println("Invalid menu number. Please enter valid menu number.");
                    break;
            }
        }

    }

    public static void assign(Scanner scan, Zoo zoo) {
        while (true) {
            System.out.print("Enter KeeperID and AnimalID to assign using , as separator.");
            System.out.println("(e.g., K1,L1)");
            System.out.println("To move back to main menu, enter 3");

            String keeperID = scan.nextLine();
            if (keeperID.equals("3")) {
                System.out.println("Returning to main menu.");
                return;
            } else {
                String[] assignInfo = keeperID.split(",");
                if (assignInfo.length != 2) {
                    System.out.println(
                            "Invalid input format. Please, try again with KeeperID and AnimalID.");
                } else {
                    zoo.assignKeeperToAnimal(assignInfo[0], assignInfo[1]);
                }
            }
        }
    }

    public static void feed(Scanner scan, Zoo zoo) {
        while (true) {

            System.out.print("Enter Animal ID: ");
            System.out.println("To move back to main menu, enter 3");
            String feedAnimalID = scan.nextLine();
            if (feedAnimalID.equals("3")) {
                System.out.println("Returning to main menu.");
                break;
            }
            System.out.println("5-1. To feed default portion, enter 1");
            System.out.println("5-2. To feed specified portion, enter 2");
            System.out.println("To move back to main menu, enter 'back'");

            String feedChoice = scan.nextLine();
            switch (Integer.parseInt(feedChoice)) {
                case 1:
                    zoo.feedAnimal(feedAnimalID);
                    break;
                case 2:
                    System.out.print("Enter portion in Kg: ");
                    double portionKg = Double.parseDouble(scan.nextLine());
                    zoo.feedAnimal(feedAnimalID, portionKg);
                    break;
                case 3:
                    System.out.println("Returning to main menu.");
                    return;
                default:
                    System.out.println("Invalid menu number. Please enter valid menu number.");
            }

        }
    }

}

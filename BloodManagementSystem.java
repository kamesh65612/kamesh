package Kamesh3;

import java.util.ArrayList;
import java.util.Scanner;

class Donor {
    String name;
    int age;
    String bloodGroup;
    String contact;

    public Donor(String name, int age, String bloodGroup, String contact) {
        this.name = name;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.contact = contact;
    }

    public void display() {
        System.out.println("Name: " + name + ", Age: " + age + ", Blood Group: " + bloodGroup + ", Contact: " + contact);
    }
}

public class BloodManagementSystem {
    static ArrayList<Donor> donors = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n=== Blood Management System ===");
            System.out.println("1. Add Donor");
            System.out.println("2. View All Donors");
            System.out.println("3. Search Donor by Blood Group");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1:
                    addDonor();
                    break;
                case 2:
                    viewDonors();
                    break;
                case 3:
                    searchByBloodGroup();
                    break;
                case 4:
                    System.out.println("Exiting... Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 4);
    }

    public static void addDonor() {
        System.out.print("Enter Donor Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine(); 

        System.out.print("Enter Blood Group (e.g., A+, B-, O+): ");
        String bloodGroup = sc.nextLine();

        System.out.print("Enter Contact Number: ");
        String contact = sc.nextLine();

        Donor donor = new Donor(name, age, bloodGroup, contact);
        donors.add(donor);

        System.out.println("Donor registered successfully!");
    }

    public static void viewDonors() {
        if (donors.isEmpty()) {
            System.out.println("No donors found.");
        } else {
            System.out.println("\nList of Donors:");
            for (Donor d : donors) {
                d.display();
            }
        }
    }

    public static void searchByBloodGroup() {
        System.out.print("Enter Blood Group to search: ");
        String bg = sc.nextLine();
        boolean found = false;

        for (Donor d : donors) {
            if (d.bloodGroup.equalsIgnoreCase(bg)) {
                d.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No donors found with blood group " + bg);
        }
    }
}


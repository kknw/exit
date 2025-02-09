package view;

import model.SuperheroSuit; // ✅ Make sure this import is present
import java.util.Scanner;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class SuitView {
    private static final Scanner scanner = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8));

    public static void showMessage(String message) {
        System.out.println("\n" + message + "\n");
    }

    public static String getSuitIdInput() {
        System.out.print("Please enter the suit ID (6 digits, first cannot be 0): ");
        return scanner.next();
    }

    public static void showSuitInfo(SuperheroSuit suit) { // ✅ Now it should recognize SuperheroSuit
        System.out.println("\nSuit ID: " + suit.getSuitId() + 
                           "\nType: " + suit.getSuitType() + 
                           "\nDurability: " + suit.getDurability());
    }

    public static boolean askForRepair() {
        System.out.print("Do you want to repair the suit? (yes/no): ");
        return scanner.next().equalsIgnoreCase("yes");
    }
}

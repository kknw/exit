package model;

import java.io.*;
import java.util.*;

public class SuitDatabase {
    private static final String FILE_PATH = "suits.csv";

    public static List<SuperheroSuit> loadSuits() {
        List<SuperheroSuit> suits = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                suits.add(new SuperheroSuit(values[0], values[1], Integer.parseInt(values[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return suits;
    }

    public static void saveSuits(List<SuperheroSuit> suits) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bw.write("suit_id,suit_type,durability\n");
            for (SuperheroSuit suit : suits) {
                bw.write(suit.getSuitId() + "," + suit.getSuitType() + "," + suit.getDurability() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

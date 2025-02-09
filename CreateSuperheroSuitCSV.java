import java.io.*;
import java.util.*;

public class CreateSuperheroSuitCSV {
    private static final String FILE_NAME = "suits.csv";
    private static final String[] SUIT_TYPES = {"Power Suit", "Stealth Suit", "Disguise Suit"};
    private static final int NUM_RECORDS = 50;

    public static void main(String[] args) {
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"suit_id", "suit_type", "durability"}); // Header

        Random random = new Random();
        while (data.size() <= NUM_RECORDS) {
            String suitId = String.valueOf(random.nextInt(900000) + 100000);
            String suitType = SUIT_TYPES[random.nextInt(SUIT_TYPES.length)];
            int durability = generateDurability(suitType, random);
            data.add(new String[]{suitId, suitType, String.valueOf(durability)});
        }

        writeCSV(data);
        System.out.println("File " + FILE_NAME + " has been successfully created!");
    }

    private static int generateDurability(String suitType, Random random) {
        while (true) {
            int durability = random.nextInt(101);
            if (suitType.equals("Power Suit") && durability >= 70) return durability;
            if (suitType.equals("Stealth Suit") && durability >= 50) return durability;
            if (suitType.equals("Disguise Suit") && durability % 10 != 3 && durability % 10 != 7) return durability;
        }
    }

    private static void writeCSV(List<String[]> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String[] row : data) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

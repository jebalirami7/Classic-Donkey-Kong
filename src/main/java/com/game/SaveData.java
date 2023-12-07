package main.java.com.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SaveData {
    final static String filePath = "./src/main/resources/media/data.txt";

    public static void write(Player player) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true))) {
            writer.write("Name: " + player.getName() + "\n");
            writer.write("Score: " + player.getScore() + "\n");
            System.out.println("Player information has been written to " + filePath);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static Map<String, Integer> read() {
        Map<String, Integer> playerMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Name: ")) {
                    String name = line.substring("Name: ".length());
                    int score = Integer.parseInt(reader.readLine().substring("Score: ".length()));
                    playerMap.put(name, score);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }

        System.out.println(playerMap);

        return playerMap;
    }


    public static void update(String playerName, int newScore) {
        // Read existing players from the file into a map
        Map<String, Integer> playersMap = read();

        // Update the score in the map
        playersMap.put(playerName, newScore);

        // Write the updated information back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, Integer> entry : playersMap.entrySet()) {
                writer.write("Name: " + entry.getKey() + "\n");
                writer.write("Score: " + entry.getValue() + "\n");
            }
            System.out.println("Player information has been updated in " + filePath);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}




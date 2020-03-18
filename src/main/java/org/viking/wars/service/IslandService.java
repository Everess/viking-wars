package org.viking.wars.service;

import org.viking.wars.entity.Island;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * This class describes the logic for working with islands.
 */
public class IslandService {

    /**
     * Formation islands list.
     * @param bufferedReader Input file.
     * @return List of islands.
     * @throws IOException The exception that is thrown when reading from a file failed.
     */
    public List<Island> createIslands(BufferedReader bufferedReader) throws IOException {
        List<Island> islandList = new ArrayList<>();
        List<String> linesToSend = new ArrayList<>();
        String rowFromMap;

        while ((rowFromMap = bufferedReader.readLine()) != null) {
            islandList.add(
                    new Island(getIslandNameFromString(rowFromMap).toString())
            );
            linesToSend.add(rowFromMap);
        }

        // Identify neighboring islands for each island.
        islandList = defineNeighborForIsland(islandList, linesToSend);

        return islandList;
    }

    /**
     * Get island name from map.
     * @param rowFromMap Row in map file.
     * @return Island name.
     */
    public StringBuilder getIslandNameFromString(String rowFromMap) {
        StringBuilder islandName = new StringBuilder();

        for (int i = 0; i < rowFromMap.length(); i++) {
            if (rowFromMap.charAt(i) == ' ') {
                break;
            }
            islandName.append(rowFromMap.charAt(i));
        }

        return islandName;
    }

    /**
     * Identification of neighboring islands and their directions for each existing island.
     * @param islandList Islands.
     * @param rowsFromMap Rows in map file.
     * @return List of islands.
     */
    public List<Island> defineNeighborForIsland(List<Island> islandList, List<String> rowsFromMap) {
        int counter = 0;

        for (String line : rowsFromMap) {
            String[] wordsFromString = line.split("\\s");
            List<Island> neighborIsland = new ArrayList<>();
            for (int i = 1; i < wordsFromString.length; i++) {
                String[] cardinalPointAndIsland = wordsFromString[i].split("=");
                String islandName = cardinalPointAndIsland[1];
                neighborIsland.add(
                        getIslandByName(islandList, islandName)
                );
            }
            islandList.get(counter).setNeighborIslands(neighborIsland);
            counter++;
        }

        return islandList;
    }

    /**
     * This method allow get object island by name.
     * @param islandList List of islands.
     * @param islandName Name of concrete island.
     * @return Island found.
     */
    private Island getIslandByName(List<Island> islandList, String islandName) {
        // TODO Заменить orElse на orElseGet.
        return islandList.stream().filter(island -> island.getIslandName().equals(islandName)).findFirst().orElse(null);
    }

    /**
     * Search and delete islands with flag isPresent = false.
     * @return List of islands.
     */
    public List<Island> checkInaccessibleIslands(List<Island> islandList) {
        islandList.removeIf(concreteIsland -> !concreteIsland.isPresent());

        return islandList;
    }

    public List<String> getMapOfIslands(List<Island> islandList) {
        List<String> rowsForFileList = new ArrayList<>();
        for (Island concreteIsland : islandList) {
            StringBuilder rowForFile = new StringBuilder();
            rowForFile.append(concreteIsland.getIslandName());
            concreteIsland
                    .getNeighborIslands()
                    .stream()
                    .forEach(neighborIsland -> {
                        rowForFile.append(" " + neighborIsland.getIslandName());
                    });
            rowsForFileList.add(rowForFile.toString());
        }

        return rowsForFileList;
    }
}

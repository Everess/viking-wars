package org.viking.wars.service;

import javafx.util.Pair;
import org.viking.wars.entity.Island;
import org.viking.wars.entity.Viking;
import org.viking.wars.exception.IslandNotFoundException;

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
    public List<Island> createIslands(BufferedReader bufferedReader) throws IOException, IslandNotFoundException {
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
    public List<Island> defineNeighborForIsland(List<Island> islandList, List<String> rowsFromMap) throws IslandNotFoundException {
        int counter = 0;

        for (String line : rowsFromMap) {
            String[] wordsFromString = line.split("\\s");
            List<Pair<String, Island>> neighborList = new ArrayList<>();
            Pair<String, Island> pairCardinalPointAndIsland;
            for (int i = 1; i < wordsFromString.length; i++) {
                String[] cardinalPointAndIslandName = wordsFromString[i].split("=");
                String islandCardinalPoint = cardinalPointAndIslandName[0];
                String islandName = cardinalPointAndIslandName[1];
                pairCardinalPointAndIsland = new Pair<String, Island>(
                        islandCardinalPoint,
                        getIslandByName(islandList, islandName)
                );
                neighborList.add(pairCardinalPointAndIsland);
            }
            islandList.get(counter).setNeighborIslands(neighborList);
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
    private Island getIslandByName(List<Island> islandList, String islandName) throws IslandNotFoundException {
        try {
            return islandList.stream().filter(island -> island.getIslandName().equals(islandName)).findFirst().get();
        } catch (Exception e) {
            throw new IslandNotFoundException("Island not found");
        }
    }

    /**
     * Search and delete islands with flag isPresent = false.
     * @return List of islands.
     */
    public List<Island> checkInaccessibleIslands(List<Island> islandList) {
        for (Island concreteIsland : islandList) {
            if (!concreteIsland.isPresent()) {
                // Enter the cycle and walk around the neighbors of each island. Remove concreteIsland.
                for (int i = 0; i < islandList.size(); i++) {
                    List<Pair<String, Island>> listPairsNeighborIslands = islandList.get(i).getNeighborIslands();
                    islandList.get(i).setNeighborIslands(deletePairFromNeighborList(listPairsNeighborIslands, concreteIsland));
                }
            }
        }

        return islandList;
    }

    /**
     * This method allow delete pair from list of neighbors.
     * @param listPairsNeighborIslands List of neighbors.
     * @param islandToEquals Island to compare.
     * @return
     */
    private List<Pair<String, Island>> deletePairFromNeighborList(List<Pair<String, Island>> listPairsNeighborIslands, Island islandToEquals) {
        List<Pair<String, Island>> newListOfPairs = new ArrayList<>();
        for (Pair<String, Island> concretePair : listPairsNeighborIslands) {
            if (!concretePair.getValue().equals(islandToEquals)) {
                newListOfPairs.add(new Pair<>(concretePair.getKey(), concretePair.getValue()));
            }
        }

        return newListOfPairs;
    }

    /**
     * This method allow get map of islands.
     * @param islandList List of islands.
     * @return List of rows with islands.
     */
    public List<String> getMapOfIslands(List<Island> islandList) {
        List<String> rowsForFileList = new ArrayList<>();
        for (Island concreteIsland : islandList) {
            if (concreteIsland.isPresent()) {
                StringBuilder rowForFile = new StringBuilder();
                rowForFile.append(concreteIsland.getIslandName()).append(" ");
                concreteIsland
                        .getNeighborIslands()
                        .stream()
                        .forEach(neighborIsland -> {
                            rowForFile.append(neighborIsland.getKey());
                            rowForFile.append("=");
                            rowForFile.append(neighborIsland.getValue().getIslandName()).append(" ");
                        });
                rowsForFileList.add(rowForFile.toString());
            }
        }

        return rowsForFileList;
    }

    /**
     * This method checks the number of Vikings on a particular island at a given moment.
     * Updates their number.
     * @param island Concrete island for check vikings count.
     * @param concreteViking Concrete viking.
     */
    public void checkAndUpdateVikingsCountOnIsland(Island island, Viking concreteViking) {
        if (island.getVikingsCount() < 2) {
            // Change the number of Vikings on the previous island.
            if (concreteViking.getIsland().getVikingsCount() > 0) {
                concreteViking.getIsland().setVikingsCount(concreteViking.getIsland().getVikingsCount() - 1);
            }
            // Moving Viking and increasing the number of Vikings on the new island.
            concreteViking.setIsland(island);
            concreteViking.getIsland().setVikingsCount(island.getVikingsCount() + 1);
        }
    }
}

package org.viking.wars.service;

import org.viking.wars.entity.Island;
import org.viking.wars.entity.Viking;
import org.viking.wars.exception.IslandNotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * This interface describes methods for islands.
 */
public interface IslandService {

    /**
     * Formation islands list.
     * @param bufferedReader Input file.
     * @return List of islands.
     * @throws IOException The exception that is thrown when reading from a file failed.
     */
    List<Island> createIslands(BufferedReader bufferedReader) throws IOException, IslandNotFoundException;

    /**
     * Get island name from map.
     * @param rowFromMap Row in map file.
     * @return Island name.
     */
    StringBuilder getIslandNameFromString(String rowFromMap);

    /**
     * Identification of neighboring islands and their directions for each existing island.
     * @param islandList Islands.
     * @param rowsFromMap Rows in map file.
     * @return List of islands.
     */
    List<Island> defineNeighborForIsland(List<Island> islandList, List<String> rowsFromMap) throws IslandNotFoundException;

    /**
     * Search and delete islands with flag isPresent = false.
     * @return List of islands.
     */
    List<Island> checkInaccessibleIslands(List<Island> islandList);

    /**
     * This method allow get map of islands.
     * @param islandList List of islands.
     * @return List of rows with islands.
     */
    List<String> getMapOfIslands(List<Island> islandList);

    /**
     * This method checks the number of Vikings on a particular island at a given moment.
     * Updates their number.
     * @param island Concrete island for check vikings count.
     * @param concreteViking Concrete viking.
     */
    void checkAndUpdateVikingsCountOnIsland(Island island, Viking concreteViking);
}

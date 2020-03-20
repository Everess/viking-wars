package org.viking.wars.service;

import org.viking.wars.entity.Island;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * This interface describes work with map.
 */
public interface MapService {

    /**
     * Method for loading a game map from file.
     * @param pathToMap Path to game map.
     * @throws FileNotFoundException The exception thrown if the map file is not found.
     */
    List<Island> loadMap(String pathToMap) throws IOException;

    /**
     * Method for unload a game map to file.
     * @param islandList List of islands.
     * @return Unload map.
     * @throws IOException The exception that is thrown when reading from a file failed.
     */
    FileWriter unloadMap(List<Island> islandList) throws IOException;
}

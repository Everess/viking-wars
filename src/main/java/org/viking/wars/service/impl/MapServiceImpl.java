package org.viking.wars.service.impl;

import org.viking.wars.constants.AppConstants;
import org.viking.wars.entity.Island;
import org.viking.wars.exception.IslandNotFoundException;
import org.viking.wars.service.IslandService;
import org.viking.wars.service.MapService;

import java.io.*;
import java.util.List;

/**
 * This class describes loading maps.
 */
public class MapServiceImpl implements MapService {

    /**
     * Island logic layer.
     */
    private final IslandService islandService;

    public MapServiceImpl(IslandService islandService) {
        this.islandService = islandService;
    }

    /**
     * Method for loading a game map from file.
     * @param pathToMap Path to game map.
     * @throws FileNotFoundException The exception thrown if the map file is not found.
     */
    @Override
    public List<Island> loadMap(String pathToMap) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToMap))) {
            return islandService.createIslands(reader);
        } catch (FileNotFoundException | IslandNotFoundException fileNotFoundException) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        }
    }

    /**
     * Method for unload a game map to file.
     * @param islandList List of islands.
     * @return Unload map.
     * @throws IOException The exception that is thrown when reading from a file failed.
     */
    @Override
    public FileWriter unloadMap(List<Island> islandList) throws IOException {
        try (FileWriter writer = new FileWriter(AppConstants.OUTPUT_WRITE_PATH)) {
            List<String> rowsForFileList = islandService.getMapOfIslands(islandList);
            for (String rowForWrite : rowsForFileList) {
                writer.append(rowForWrite).append("\n");
            }
            writer.flush();
            return writer;
        } catch (IOException e) {
            throw new IOException();
        }
    }
}

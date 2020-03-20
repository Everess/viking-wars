package org.viking.wars.entity;

import javafx.util.Pair;

import java.util.List;

/**
 * This class describe island entity.
 */
public class Island {

    /**
     * Name of island.
     */
    private String islandName;

    /**
     * Flag of the existence of the island (exists - true, destroyed - false)
     */
    private boolean isPresent;

    /**
     * The number of Vikings on one island (maximum 2).
     */
    private Integer vikingsCount;

    /**
     * Neighbor islands.
     */
    private List<Pair<String, Island>> neighborIslands;

    public Island(String islandName) {
        this.islandName = islandName;
        this.isPresent = true;
        this.vikingsCount = 0;
    }

    public String getIslandName() {
        return islandName;
    }

    public void setIslandName(String islandName) {
        this.islandName = islandName;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public Integer getVikingsCount() {
        return vikingsCount;
    }

    public void setVikingsCount(Integer vikingsCount) {
        this.vikingsCount = vikingsCount;
    }

    public List<Pair<String, Island>> getNeighborIslands() {
        return neighborIslands;
    }

    public void setNeighborIslands(List<Pair<String, Island>> neighborIslands) {
        this.neighborIslands = neighborIslands;
    }
}

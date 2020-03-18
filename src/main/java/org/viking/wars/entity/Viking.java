package org.viking.wars.entity;

/**
 * This class describes entity of Viking.
 */
public class Viking {

    /**
     * Name of viking.
     */
    private Integer vikingName;

    /**
     * Origin island
     */
    private Island island;

    /**
     * Viking status indicator (alive/dead).
     */
    private boolean isAlive;

    public Viking(Integer vikingName, Island island) {
        this.vikingName = vikingName;
        this.island = island;
        this.isAlive = true;
    }

    public Integer getVikingName() {
        return vikingName;
    }

    public void setVikingName(Integer vikingName) {
        this.vikingName = vikingName;
    }

    public Island getIsland() {
        return island;
    }

    public void setIsland(Island island) {
        this.island = island;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}

package org.viking.wars.service;

import org.viking.wars.entity.Island;
import org.viking.wars.entity.Viking;

import java.util.List;

/**
 * This interface describes methods for vikings.
 */
public interface VikingService {

    /**
     * Create some vikings.
     * @param islandList List of islands.
     * @return List of vikings, which was created.
     */
    List<Viking> createListOfVikings(List<Island> islandList);

    /**
     * Formation of a scenario for every living Viking.
     * @param vikingsList List of vikings.
     */
    List<Viking> generateVikingsRoute(List<Viking> vikingsList);

    /**
     * Checking for a possible battle between the Vikings.
     * @param aliveAndDisplacedVikingList List of alive and displaced to other island vikings.
     * @return List of viking warriors.
     */
    List<Viking> checkForBattle(List<Viking> aliveAndDisplacedVikingList);

    /**
     * Search for the Vikings who are stuck on the island.
     * @param vikingList Vikings.
     */
    int checkBlockedVikings(List<Viking> vikingList);
}

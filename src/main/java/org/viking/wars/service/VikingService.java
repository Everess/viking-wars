package org.viking.wars.service;

import org.viking.wars.enums.MessageType;
import org.viking.wars.entity.Island;
import org.viking.wars.entity.Viking;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class describes the logic for working with vikings.
 */
public class VikingService {

    /**
     * Create some vikings.
     * @param islandList List of islands.
     * @return List of vikings, which was created.
     */
    public List<Viking> createListOfVikings(Island ... islandList) {
        List<Viking> vikingList = new ArrayList<>();
        int counter = 0;

        for (Island concreteIsland : islandList) {
            vikingList.add(
                    new Viking(counter++, concreteIsland)
            );
        }

        return vikingList;
    }

    /**
     * Formation of a scenario for every living Viking.
     * @param vikingsList List of vikings.
     */
    public List<Viking> generateVikingsRoute(List<Viking> vikingsList) {
        for (Viking concreteViking : vikingsList) {
            if (concreteViking.isAlive() && concreteViking.getIsland().isPresent()) {
                int islandsCount = concreteViking
                        .getIsland()
                        .getNeighborIslands()
                        .size();
                concreteViking.setIsland(
                        concreteViking
                                .getIsland()
                                .getNeighborIslands()
                                .get(new Random().nextInt(islandsCount))
                );
            }
        }

        return vikingsList;
    }

    /**
     * Checking for a possible battle between the Vikings.
     * @param aliveAndDisplacedVikingList List of alive and displaced to other island vikings.
     * @return
     */
    public List<Viking> checkForBattle(List<Viking> aliveAndDisplacedVikingList) {
        for (int i = 0; i < aliveAndDisplacedVikingList.size(); i++) {
            for (int j = 1; j < aliveAndDisplacedVikingList.size(); j++) {
                Viking firstViking = aliveAndDisplacedVikingList.get(i);
                Viking secondViking = aliveAndDisplacedVikingList.get(j);
                if (firstViking.getIsland().equals(secondViking.getIsland()) && !firstViking.equals(secondViking) && firstViking.isAlive() && secondViking.isAlive()) {
                    firstViking.getIsland().setPresent(false);

                    // Set vikings alive statuses.
                    firstViking.setAlive(false);
                    secondViking.setAlive(false);

                    produceMessage(
                            MessageType.ISLAND_DESTROYED,
                            new Viking[] {
                                    firstViking,
                                    secondViking
                            }
                    );

                    // Delete dead vikings from list.
                    aliveAndDisplacedVikingList.remove(firstViking);
                    aliveAndDisplacedVikingList.remove(secondViking);
                }
            }
        }

        return aliveAndDisplacedVikingList;
    }

    /**
     * Search for the Vikings who are stuck on the island.
     * @param vikingList Vikings.
     */
    public void checkBlockedVikings(List<Viking> vikingList) {
        for (Viking concreteViking : vikingList) {
            if (concreteViking.getIsland().getNeighborIslands().isEmpty() || concreteViking.getIsland().getNeighborIslands() == null) {
                produceMessage(
                        MessageType.VIKING_BLOCKED,
                        new Viking[] { concreteViking }
                        );
            }
        }
    }

    /**
     * This method allow write message to console.
     * @param messageType Type of message.
     * @param vikings Array of vikings.
     */
    public void produceMessage(MessageType messageType, Viking[] vikings) {
        switch (messageType) {
            case VIKING_BLOCKED:
                System.out.println(
                        "AGR !!! " +
                        vikings[0].getVikingName() +
                        " is stuck on island " +
                        vikings[0].getIsland().getIslandName() +
                        " and is no longer participating in the war"
                );
                break;
            case ISLAND_DESTROYED:
                System.out.println("Battle started.");
                System.out.println(
                        "AGR !!! Lighthouse destroyed on " +
                        vikings[0].getIsland().getIslandName() +
                        " thanks to vikings " + vikings[0].getVikingName() +
                        " and " + vikings[1].getVikingName()
                );
                break;
        }
    }
}

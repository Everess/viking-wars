package org.viking.wars.service.impl;

import javafx.util.Pair;
import org.viking.wars.enums.MessageType;
import org.viking.wars.entity.Island;
import org.viking.wars.entity.Viking;
import org.viking.wars.service.IslandService;
import org.viking.wars.service.VikingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class describes the logic for working with vikings.
 */
public class VikingServiceImpl implements VikingService {

    /**
     * Inject island service logic.
     */
    private final IslandService islandService;

    public VikingServiceImpl(IslandService islandService) {
        this.islandService = islandService;
    }

    /**
     * Create some vikings.
     * @param islandList List of islands.
     * @return List of vikings, which was created.
     */
    @Override
    public List<Viking> createListOfVikings(List<Island> islandList) {
        List<Viking> vikingList = new ArrayList<>();
        int counter = 0;

        for (Island concreteIsland : islandList) {
            vikingList.add(
                    new Viking(counter++, concreteIsland)
            );
            concreteIsland.setVikingsCount(1);
        }

        return vikingList;
    }

    /**
     * Formation of a scenario for every living Viking.
     * @param vikingsList List of vikings.
     */
    @Override
    public List<Viking> generateVikingsRoute(List<Viking> vikingsList) {
        for (Viking concreteViking : vikingsList) {
            if (concreteViking.isAlive() && concreteViking.getIsland().isPresent()) {
                int islandsCount = concreteViking
                        .getIsland()
                        .getNeighborIslands()
                        .size();
                if (islandsCount != 0) {
                    Pair<String, Island> neighborIsland = concreteViking
                            .getIsland()
                            .getNeighborIslands()
                            .get(new Random().nextInt(islandsCount));
                    islandService.checkAndUpdateVikingsCountOnIsland(neighborIsland.getValue(), concreteViking);
                    System.err.println("Viking " + concreteViking.getVikingName() + " was displaced to " + concreteViking.getIsland().getIslandName());
                }
            }
        }

        return vikingsList;
    }

    /**
     * Checking for a possible battle between the Vikings.
     * @param aliveAndDisplacedVikingList List of alive and displaced to other island vikings.
     * @return List of viking warriors.
     */
    @Override
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
                }
            }
        }

        return aliveAndDisplacedVikingList;
    }

    /**
     * Search for the Vikings who are stuck on the island.
     * @param vikingList Vikings.
     */
    @Override
    public int checkBlockedVikings(List<Viking> vikingList) {
        int counter = 0;
        for (Viking concreteViking : vikingList) {
            if (concreteViking.isAlive() &&
                    (concreteViking.getIsland().getNeighborIslands().isEmpty() || concreteViking.getIsland().getNeighborIslands() == null)) {
                counter++;
            }

            if (!concreteViking.getIsland().isPresent() && concreteViking.isAlive()) {
                concreteViking.setAlive(false);
            }

            if ((concreteViking.getIsland().getNeighborIslands().isEmpty() ||
                    concreteViking.getIsland().getNeighborIslands() == null) &&
                            concreteViking.isAlive()) {
                produceMessage(
                        MessageType.VIKING_BLOCKED,
                        new Viking[] { concreteViking }
                        );
            }
        }

        return counter;
    }


    /**
     * This method allow write message to console.
     * @param messageType Type of message.
     * @param vikings Array of vikings.
     */
    private void produceMessage(MessageType messageType, Viking[] vikings) {
        switch (messageType) {
            case VIKING_BLOCKED:
                System.out.println(
                        "AGR !!! Viking " +
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

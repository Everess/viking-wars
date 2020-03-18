package org.viking.wars;

import org.viking.wars.entity.Island;
import org.viking.wars.entity.Viking;
import org.viking.wars.service.VikingService;
import org.viking.wars.constants.AppConstants;
import org.viking.wars.service.IslandService;
import org.viking.wars.service.MapService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Matvey Timoshin
 */
public class Main {

    /**
     * Global list of islands.
     */
    private static List<Island> islandList = new ArrayList<>();

    /**
     * Global list of vikings.
     */
    private static List<Viking> vikingList = new ArrayList<>();

    /**
     * Entry point to program.
     */
    public static void main(String[] args) throws IOException {
        // Inject service layer.
        MapService mapService = new MapService(new IslandService());
        VikingService vikingService = new VikingService();
        IslandService islandService = new IslandService();

        // 1. Read map.
        islandList = mapService.loadMap(AppConstants.PATH_TO_MAP);
        System.out.println("Map loaded. Islands created.");

        // TODO Добавить возможность задавать количество викингов.
        // 2. Create vikings.
        for (int i = 0; i < AppConstants.VIKINGS_COUNT; i++) {

        }
        vikingList = vikingService.createListOfVikings(
                islandList.get(0),
                islandList.get(5),
                islandList.get(2),
                islandList.get(1),
                islandList.get(3),
                islandList.get(4)
        );
        System.out.println("Vikings are created and distributed across the islands.");

        // 3. Start game simulation.
        Main.runGame(vikingList, vikingService, islandService);

        // 4. Unload changed map to file.
        mapService.unloadMap(islandList);
    }

    /**
     * This method run the game.
     * @param vikingList Vikings.
     * @param vikingService Vikings logic layer.
     * @param islandService Island logic layer.
     */
    public static void runGame(List<Viking> vikingList, VikingService vikingService, IslandService islandService) {
        for (int i = 0; i < AppConstants.DAYS_COUNT; i++) {
            vikingList = vikingService.generateVikingsRoute(vikingList);
            vikingList = vikingService.checkForBattle(vikingList);
            islandList = islandService.checkInaccessibleIslands(islandList);
            vikingService.checkBlockedVikings(vikingList);
        }
    }
}

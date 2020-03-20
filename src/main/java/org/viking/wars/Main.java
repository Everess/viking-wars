package org.viking.wars;

import org.viking.wars.entity.Island;
import org.viking.wars.entity.Viking;
import org.viking.wars.service.*;
import org.viking.wars.constants.AppConstants;
import org.viking.wars.service.impl.IslandServiceImpl;
import org.viking.wars.service.impl.MapServiceImpl;
import org.viking.wars.service.impl.VikingServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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
        IslandService islandService = new IslandServiceImpl();
        MapServiceImpl mapServiceImpl = new MapServiceImpl(islandService);
        VikingServiceImpl vikingServiceImpl = new VikingServiceImpl(islandService);

        // 1. Read map.
        islandList = mapServiceImpl.loadMap(AppConstants.PATH_TO_MAP);
        System.out.println("Map loaded. Islands created.");

        // 2. Create vikings.
        vikingList = vikingServiceImpl.createListOfVikings(defineStartIslandForViking());
        System.out.println("Vikings are created and distributed across the islands.");

        // 3. Start game simulation.
        Main.runGame(vikingList, vikingServiceImpl, islandService);

        // 4. Unload changed map to file.
        mapServiceImpl.unloadMap(islandList);
    }

    /**
     * Define start island for every viking.
     * @return List of start islands.
     */
    private static List<Island> defineStartIslandForViking() {
        System.out.println("Enter vikings count:");
        Scanner scanner = new Scanner(System.in);
        int vikingsCount = scanner.nextInt();
        if (vikingsCount < 2 || vikingsCount > islandList.size() * 2) {
            System.err.println("Vikings count can't be < 2 and > islands count * 2");
            System.exit(123);
        }
        List<Island> startIslandForViking = new ArrayList<>();
        for (int i = 0; i < vikingsCount; i++) {
            startIslandForViking.add(islandList.get(new Random().nextInt(islandList.size())));
        }

        return startIslandForViking;
    }

    /**
     * This method run the game.
     * @param vikingList Vikings.
     * @param vikingService Vikings logic layer.
     * @param islandService Island logic layer.
     */
    private static void runGame(List<Viking> vikingList, VikingService vikingService, IslandService islandService) {
        for (int i = 0; i < AppConstants.DAYS_COUNT; i++) {
            if (vikingList.size() == 0) {
                System.err.println("There no alive vikings.");
                break;
            }
            vikingList = vikingService.generateVikingsRoute(vikingList);
            vikingList = vikingService.checkForBattle(vikingList);
            islandList = islandService.checkInaccessibleIslands(islandList);
            int blockedVikingsCounter = vikingService.checkBlockedVikings(vikingList);
            if (blockedVikingsCounter > 0) {
                System.err.println("All alive vikings was blocked.");
                break;
            }
        }
    }
}

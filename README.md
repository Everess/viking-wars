# Game Viking wars

The essence of the game is as follows: if two Vikings meet on the island, then a war begins between them. The island is collapsing and is no longer accessible to its neighboring islands.
If the island has no neighboring islands, and there is a Viking on it, then it is considered stuck.
The game ends if there is no reason for war.

## Entities
| Entity |
| :----- |
| Viking |
| Island |


## Logic classes
| Class         | Description                             |
| :------------ | :-------------------------------------- |
| Main          | Main class this application             |
| IslandService | Class for working with an entity Island |
| VikingService | Class for working with an entity Viking |
| MapService    | Class for working with map files        |

## Methods logic class IslandService
| Method                             | Description                                                                         |
| :--------------------------------- | :---------------------------------------------------------------------------------- |
| createIslands                      | This method allows you to create islands                                            | 
| getIslandNameFromString            | Get island name from map                                                            |
| defineNeighborForIsland            | Identification of neighboring islands and their directions for each existing island |
| getIslandByName                    | This method allow get object island by name                                         |
| checkInaccessibleIslands           | Search and delete islands with flag isPresent = false                               |
| getMapOfIslands                    | This method allow get map of islands                                                |
| checkAndUpdateVikingsCountOnIsland | This method checks the number of Vikings on a particular island at a given moment   |

## Methods logic class VikingService
| Method               | Description                                        |
| :------------------- | :------------------------------------------------- |
| createListOfVikings  | Create some vikings                                |
| generateVikingsRoute | Formation of a scenario for every living Viking    |
| checkForBattle       | Checking for a possible battle between the Vikings |
| checkBlockedVikings  | Search for the Vikings who are stuck on the island |
| produceMessage       | This method allow write message to console         |

## Methods logic class MapService
| Method    | Description                             |
| :------   | :-------------------------------------- |
| loadMap   | Method for loading a game map from file |
| unloadMap | Method for unload a game map to file    |

## Methods logic class Main
| Method                     | Description                             |
| :------------------------- | :-------------------------------------- |
| main                       | Entry point to program                  |
| defineStartIslandForViking | Define start island for every viking    |
| runGame                    | This method run the game                |

## Technical requirements
Java 8

## Launch instruction
1. In Maven, click the build button to build the project.
2. Right-click on the Mine class and select the launch point.
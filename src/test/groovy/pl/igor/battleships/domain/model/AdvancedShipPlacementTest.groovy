package pl.igor.battleships.domain.model

import pl.igor.battleships.application.game_configuration.PlaceableShip
import pl.igor.battleships.domain.adapters.InMemoryPlayerRepository
import pl.igor.battleships.presentation.PlayerDto
import spock.lang.Specification

class AdvancedShipPlacementTest extends Specification {

    public static final int TEST_BOARD_DIMENSION = 2
    GameService gameService
    PlayerService playerService
    BattlefieldCreator battlefieldCreator

    def setup() {
        def playerRepository = new InMemoryPlayerRepository()
        playerService = new PlayerService(new PlayerCreator(), playerRepository)

        battlefieldCreator = new BattlefieldCreator()
        gameService = new GameService(playerService, battlefieldCreator)
    }

    def "should place ships apart from each other of at least one tile"() {
        given:
            def shipsToPlace = twoShipsWith3And4Tiles()
            def board = aBoardWith10x10Grid()
            def placementStrategy = new AdvancedShipPlacementStrategy()
        when:
            board.populateWithShips(shipsToPlace, placementStrategy)
        then:
            def ships = board.playerShips
            isValid(ships, board)
    }

    private boolean isValid(List<Ship> ships, Board board) {
        for (ship in ships) {
            def tiles = ship.shipTiles
            for (tile in tiles) {
                def adjacentTiles = board.grid.getTilesInProximity(tile.tileNumber)
                for (adjTile in adjacentTiles) {
                    if (adjTile.tileState == TileState.WATER)
                        //water is ok
                        continue
                    if (adjTile.tileState == TileState.SHIP) {
                            if (!tiles.contains(adjTile.tileState)) {
                                //not the same ship
                                return false
                            }
                    }
                }
            }
        }
        return true
    }

    def "ship tiles should be of type ship"() { //TODO: spock
        given:
            def shipsToPlace = twoShipsWith3And4Tiles()
            def board = aBoardWith10x10Grid()
            def placementStrategy = new AdvancedShipPlacementStrategy()
        when:
            board.populateWithShips(shipsToPlace, placementStrategy)
        then:
            def ships = board.playerShips
            shipTilesHasOnlyShipTypeTile(ships)
    }

    private boolean shipTilesHasOnlyShipTypeTile(List<Ship> ships) {
        for (ship in ships) {
            def tiles = ship.shipTiles
            for (tile in tiles) {
                if (tile.tileState != TileState.SHIP) {
                    return false
                }
            }
        }

        return true
    }

    def "algorithm should tell if it cant place given tiles because of lack of size"() {
        given:
            def player = aHumanPlayer("Player1")
        when:
            def board = gameService.createBoard(player.id, TEST_BOARD_DIMENSION)
        then:
            board.unsunkenShips
    }

    private List<PlaceableShip> twoShipsWith3And4Tiles() {
        List.of(new PlaceableShip(4), new PlaceableShip(3))
    }

    private Board aBoardWith10x10Grid() {
        def player = aHumanPlayer("Player1")
        gameService.createBoard(player.id, 10)
    }

    private PlayerDto aHumanPlayer(String name) {
        playerService.createNewPlayer(name)
    }


}

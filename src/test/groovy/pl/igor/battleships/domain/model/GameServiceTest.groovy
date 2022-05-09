package pl.igor.battleships.domain.model

import pl.igor.battleships.application.game_configuration.PlaceableShip
import pl.igor.battleships.domain.adapters.InMemoryPlayerRepository
import pl.igor.battleships.presentation.PlayerDto
import spock.lang.Specification

class GameServiceTest extends Specification {

    public static final int TEST_BOARD_DIMENSION = 2
    GameService gameService
    PlayerService playerService

    def setup() {
        def playerRepository = new InMemoryPlayerRepository()
        playerService = new PlayerService(new PlayerCreator(), playerRepository)
        gameService = new GameService(playerService, new BattlefieldCreator())
    }

    def "should create a board for player"() {
        given:
            def player = aHumanPlayer("Player1")
        when:
            def board = gameService.createBoard(player.id, TEST_BOARD_DIMENSION)
        then:
            board.owner.id == player.id
            board.grid.size == TEST_BOARD_DIMENSION
    }

    def "board should show that it has unsunken ships after creation"() {
        given:
            def player = aHumanPlayer("Player1")
        when:
            def board = gameService.createBoard(player.id, TEST_BOARD_DIMENSION)
        then:
            board.unsunkenShips
    }

    def "created board has proper tile numbers"() {
        given:
            def player = aHumanPlayer("Player1")
        when:
            def board = gameService.createBoard(player.id, TEST_BOARD_DIMENSION)
        then:
            board.grid.//TODO:
    }

    def "board should not be in game just after creation"() {
        given:
            def player = aHumanPlayer("Player1")
        when:
            def board = gameService.createBoard(player.id, TEST_BOARD_DIMENSION)
        then:
            !board.inOngoingGame
    }

    def "board should have unsunken ships right after ship placement"() {
        given:
            def shipsToPlace = twoShipsWith3And4Tiles()
        and:
            def board = aBoardWith10x10Grid()
        when:
            board = gameService.placeShips(board, shipsToPlace, new AdvancedShipPlacementStrategy())
        then:
            board.unsunkenShips
    }

    def "board should be ready after ship placement"() {
        given:
            def shipsToPlace = twoShipsWith3And4Tiles()
        and:
            def board = aBoardWith10x10Grid()
        when:
            board = gameService.placeShips(board, shipsToPlace, new AdvancedShipPlacementStrategy())
        then:
            board.ready
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

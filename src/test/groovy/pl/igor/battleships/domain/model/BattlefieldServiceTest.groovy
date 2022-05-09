package pl.igor.battleships.domain.model

import pl.igor.battleships.application.game_configuration.PlaceableShip
import pl.igor.battleships.application.game_configuration.StandardBattleshipsConfiguration
import pl.igor.battleships.domain.adapters.InMemoryPlayerRepository
import pl.igor.battleships.presentation.PlayerDto
import spock.lang.Specification

class BattlefieldServiceTest extends Specification {

    private static final int TEST_BOARD_DIMENSION = 2
    BattlefieldService gameService
    PlayerService playerService

    def setup() {
        def playerRepository = new InMemoryPlayerRepository()
        playerService = new PlayerService(new PlayerCreator(), playerRepository)
        gameService = new BattlefieldService(playerService, new BattlefieldCreator())
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

            board.grid.getTile("A1")
            board.grid.getTile("A2")
            board.grid.getTile("B1")
            board.grid.getTile("B2")

        then:
            notThrown(IllegalArgumentException)
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

    def "should create battlefield with two boards for each player"() {
        given:
            def player1 = aHumanPlayer("Player1")
            def player2 = aHumanPlayer("Player1")
        when:
            def game = gameService.createNewGame(player1, player2, new StandardBattleshipsConfiguration())
        then:
            game.player1.id == player1.id
            game.player2.id == player2.id
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

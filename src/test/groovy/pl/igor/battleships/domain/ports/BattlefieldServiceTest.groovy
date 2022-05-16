package pl.igor.battleships.domain.ports

import pl.igor.battleships.application.game_configuration.AlwaysShipPlacementStrategy
import pl.igor.battleships.application.game_configuration.NeverShipPlacementStrategy
import pl.igor.battleships.application.game_configuration.StandardBattleshipsConfiguration
import pl.igor.battleships.application.game_configuration.TestShipsConfiguration
import pl.igor.battleships.domain.adapters.InMemoryBattlefieldRepository
import pl.igor.battleships.domain.model.BattleshipsDomainService
import pl.igor.battleships.presentation.PlayerDto
import spock.lang.Narrative
import spock.lang.Specification

@Narrative('''
Unit test only a entrance to this module
''')
class BattlefieldServiceTest extends Specification {

    private static final UUID PLAYER1_ID = UUID.randomUUID()
    private static final UUID PLAYER2_ID = UUID.randomUUID()
    BattleshipsService battleshipsService
    BattlefieldRepository battlefieldRepository

    def setup() {
        battlefieldRepository = new InMemoryBattlefieldRepository()
        battleshipsService = new BattleshipsService(new BattleshipsDomainService(), battlefieldRepository);
    }

    def "should create a game with two players"() {
        given:
            def player1 = aPlayerOne()
            def player2 = aPlayerTwo()
            def configuration = new StandardBattleshipsConfiguration()
        when:
            def battlefield = battleshipsService.createNewGame(player1, player2, configuration)
        then:
            battlefield.gameId != null
            battlefield.player1.id == player1.id
            battlefield.player2.id == player2.id
    }

    def "should return 'miss' when player misses" () {
        given:
            def player1 = aPlayerOne()
            def player2 = aPlayerTwo()
            def configuration = new TestShipsConfiguration(2, new NeverShipPlacementStrategy())
            def battlefield = battleshipsService.createNewGame(player1, player2, configuration)
        when:
            def shootResult = battleshipsService.shootAt(battlefield.gameId, player1.id, "A1")
        then:
            shootResult.result == "MISS"
    }

    def "should return 'hit' when player shoots an enemy ship" () {
        given:
            def player1 = aPlayerOne()
            def player2 = aPlayerTwo()
            def configuration = new TestShipsConfiguration(2, new AlwaysShipPlacementStrategy())
            def battlefield = battleshipsService.createNewGame(player1, player2, configuration)
        when:
            def shootResult = battleshipsService.shootAt(battlefield.gameId, player1.id, "A1")
        then:
            shootResult.result == "HIT"
    }

    def "should return 'sink' when player shoots whole enemy ship" () {
        given:
            def player1 = aPlayerOne()
            def player2 = aPlayerTwo()
            def configuration = new TestShipsConfiguration(2, new AlwaysShipPlacementStrategy())
            def battlefield = battleshipsService.createNewGame(player1, player2, configuration)
        when:
            def shootResult1 = battleshipsService.shootAt(battlefield.gameId, player1.id, "A1")
            def shootResult2 = battleshipsService.shootAt(battlefield.gameId, player1.id, "A2")
            def shootResult3 = battleshipsService.shootAt(battlefield.gameId, player1.id, "B1")
            def shootResult4 = battleshipsService.shootAt(battlefield.gameId, player1.id, "B2")
        then:
            shootResult1.result == "HIT"
            shootResult2.result == "HIT"
            shootResult3.result == "HIT"
            shootResult4.result == "SINK"
    }

    def "should return game over when player one destroys all ships" () {
        given:
            def player1 = aPlayerOne()
            def player2 = aPlayerTwo()
            def configuration = new TestShipsConfiguration(2, new AlwaysShipPlacementStrategy())
            def battlefield = battleshipsService.createNewGame(player1, player2, configuration)
        when:
            battleshipsService.shootAt(battlefield.gameId, player1.id, "A1")
            battleshipsService.shootAt(battlefield.gameId, player1.id, "A2")
            battleshipsService.shootAt(battlefield.gameId, player1.id, "B1")
            def shootResult = battleshipsService.shootAt(battlefield.gameId, player1.id, "B2")
        then:
            shootResult.gameOver
    }

    def "should return correct winner when player one destroys all ships" () {
        given:
            def player1 = aPlayerOne()
            def player2 = aPlayerTwo()
            def configuration = new TestShipsConfiguration(2, new AlwaysShipPlacementStrategy())
            def battlefield = battleshipsService.createNewGame(player1, player2, configuration)
        when:
            battleshipsService.shootAt(battlefield.gameId, player1.id, "A1")
            battleshipsService.shootAt(battlefield.gameId, player1.id, "A2")
            battleshipsService.shootAt(battlefield.gameId, player1.id, "B1")
            def shootResult = battleshipsService.shootAt(battlefield.gameId, player1.id, "B2")
        then:
            shootResult.winner == player1.id
    }

    def "should NOT return game over when attacked player still has some ships living" () {
        given:
            def player1 = aPlayerOne()
            def player2 = aPlayerTwo()
            def configuration = new TestShipsConfiguration(2, new AlwaysShipPlacementStrategy())
            def battlefield = battleshipsService.createNewGame(player1, player2, configuration)
        when:
            def shootResult = battleshipsService.shootAt(battlefield.gameId, player1.id, "B2")
        then:
            !shootResult.gameOver
    }

    def "should return 'already hit' when player hits same tile " () {
        given:
            def player1 = aPlayerOne()
            def player2 = aPlayerTwo()
            def configuration = new TestShipsConfiguration(2, new AlwaysShipPlacementStrategy())
            def battlefield = battleshipsService.createNewGame(player1, player2, configuration)
        when:
            def shootResult1 = battleshipsService.shootAt(battlefield.gameId, player1.id, "A1")
            def shootResult2 = battleshipsService.shootAt(battlefield.gameId, player1.id, "A1")
        then:
            shootResult1.result == "HIT"
            shootResult2.result == "ALREADY HIT"
    }

    def "should return 'can't hit' when player hits not existing tile" () {
        given:
            def player1 = aPlayerOne()
            def player2 = aPlayerTwo()
            def configuration = new TestShipsConfiguration(2, new AlwaysShipPlacementStrategy())
            def battlefield = battleshipsService.createNewGame(player1, player2, configuration)
        when:
            def shootResult = battleshipsService.shootAt(battlefield.gameId, player1.id, "A123")
        then:
            shootResult.result == "CAN'T HIT"
            shootResult.message == "WRONG TILE"
    }

    def "should return 'can't hit' when given player is not present at this game" () {
        given:
            def player1 = aPlayerOne()
            def player2 = aPlayerTwo()
            def configuration = new TestShipsConfiguration(2, new AlwaysShipPlacementStrategy())
            def battlefield = battleshipsService.createNewGame(player1, player2, configuration)
        when:
            def shootResult = battleshipsService.shootAt(battlefield.gameId, UUID.randomUUID(), "A1")
        then:
            shootResult.result == "CAN'T HIT"
            shootResult.message == "PLAYER DOESNT PLAY THIS GAME"
    }

    def "should return 'can't hit' when passed not existing battleships game" () {
        given:
            def player1 = aPlayerOne()
            def player2 = aPlayerTwo()
            def configuration = new TestShipsConfiguration(2, new AlwaysShipPlacementStrategy())
            battleshipsService.createNewGame(player1, player2, configuration)
        when:
            def shootResult = battleshipsService.shootAt(UUID.randomUUID(), UUID.randomUUID(), "A1")
        then:
            shootResult.result == "CAN'T HIT"
            shootResult.message == "THIS GAME IS NOT AVAILABLE"
    }

    def "should throw when battlefieldId is null" () {
        when:
            battleshipsService.shootAt(null, UUID.randomUUID(), "A1")
        then:
            thrown(NullPointerException)
    }

    def "should throw when playerId is null" () {
        when:
            battleshipsService.shootAt(UUID.randomUUID(), null, "A1")
        then:
            thrown(NullPointerException)
    }

    def "should throw when tileNumber is null" () {
        when:
            battleshipsService.shootAt(UUID.randomUUID(), UUID.randomUUID(), null)
        then:
            thrown(NullPointerException)
    }

    def "should create a game with grid size of 100"() {
        given:
            def player1 = aPlayerOne()
            def player2 = aPlayerTwo()
            def configuration = new TestShipsConfiguration(100, new AlwaysShipPlacementStrategy())
        when:
            battleshipsService.createNewGame(player1, player2, configuration)
        then:
            thrown(IllegalArgumentException)
    }

    def "should not create a game with grid size of 1"() {
        given:
            def player1 = aPlayerOne()
            def player2 = aPlayerTwo()
            def configuration = new TestShipsConfiguration(1, new AlwaysShipPlacementStrategy())
        when:
            battleshipsService.createNewGame(player1, player2, configuration)
        then:
            thrown(IllegalArgumentException)
    }

    def "should throw when creating game with null player id"() {
        given:
            def player2 = aPlayerTwo()
            def configuration = new TestShipsConfiguration(10, new AlwaysShipPlacementStrategy())
        when:
            battleshipsService.createNewGame(null, player2, configuration)
        then:
            thrown(NullPointerException)
    }

    def "should throw when creating game with null player2 id"() {
        given:
            def configuration = new TestShipsConfiguration(10, new AlwaysShipPlacementStrategy())
        when:
            battleshipsService.createNewGame(aPlayerTwo(), nullIdPlayer(), configuration)
        then:
            thrown(NullPointerException)
    }

    def "should throw when creating game with null player"() {
        given:
            def configuration = new TestShipsConfiguration(10, new AlwaysShipPlacementStrategy())
        when:
            battleshipsService.createNewGame(nullIdPlayer(), aPlayerTwo(), configuration)
        then:
            thrown(NullPointerException)
    }

    def "should throw when creating game with null player2"() {
        given:
            def player2 = aPlayerTwo()
            def configuration = new TestShipsConfiguration(10, new AlwaysShipPlacementStrategy())
        when:
            battleshipsService.createNewGame(player2, null, configuration)
        then:
            thrown(NullPointerException)
    }

    def "should throw when creating game with null configuration"() {
        given:
            def player2 = aPlayerTwo()
            def player1 = aPlayerOne()
        when:
            battleshipsService.createNewGame(player2, player1, null)
        then:
            thrown(NullPointerException)
    }

    private PlayerDto aPlayerOne() {
        PlayerDto.builder()
                .id(PLAYER1_ID)
                .name("Player1")
                .build()
    }

    private PlayerDto aPlayerTwo() {
        PlayerDto.builder()
                .id(PLAYER2_ID)
                .name("Player2")
                .build()
    }

    private PlayerDto nullIdPlayer() {
        PlayerDto.builder()
                .id(null)
                .name("Player1")
                .build()
    }



}

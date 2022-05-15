package pl.igor.battleships.domain.ports

import pl.igor.battleships.domain.adapters.InMemoryBattlefieldRepository
import pl.igor.battleships.domain.model.AllFieldsAsShipConfiguration
import pl.igor.battleships.domain.model.BattleshipsDomainService
import pl.igor.battleships.presentation.PlayerDto
import spock.lang.Narrative
import spock.lang.Specification

@Narrative('''
Unit test only a entrance to this module
''')
class BattlefieldServiceTest extends Specification {

    private static final int TEST_BOARD_DIMENSION = 2
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
            def configuration = new AllFieldsAsShipConfiguration()
        when:
            def battlefield = battleshipsService.createNewGame(player1, player2, configuration)
        then:
            battlefield.gameId != null
            battlefield.player1.id == player1.id
            battlefield.player2.id == player2.id
    }

    private PlayerDto aPlayerOne() {
        PlayerDto.builder()
                .id(PLAYER1_ID)
                .name("Player1")
                .build()
    }

    private PlayerDto aPlayerTwo() {
        PlayerDto.builder()
                .id(PLAYER1_ID)
                .name("Player2")
                .build()
    }


}

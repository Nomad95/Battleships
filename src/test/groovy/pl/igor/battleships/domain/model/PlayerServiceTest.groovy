package pl.igor.battleships.domain.model

import pl.igor.battleships.domain.adapters.InMemoryPlayerRepository
import pl.igor.battleships.domain.ports.PlayerRepository
import pl.igor.battleships.domain.ports.PlayerService
import pl.igor.battleships.presentation.PlayerType
import spock.lang.Specification

class PlayerServiceTest extends Specification {

    PlayerService playerService
    PlayerRepository repository

    def setup() {
        repository = new InMemoryPlayerRepository()
        playerService = new PlayerService(repository)
    }

    def "Should create human player"() {
        when:
            def player = playerService.createNewPlayer("Player1")
        then:
            player.getPlayerType() == PlayerType.HUMAN
            player.getName() == "Player1"
    }

    def "Should create computer player"() {
        when:
            def player = playerService.createNewComputerPlayer("CPU1", new RandomFireComputerDifficulty())
        then:
            player.getPlayerType() == PlayerType.COMPUTER
            player.getName() == "CPU1"
    }

    def "Should throw when passed null name"() {
        when:
            playerService.createNewPlayer(null)
        then:
            thrown(NullPointerException)
    }

    def "Should throw when passed null computer name"() {
        when:
            playerService.createNewComputerPlayer(null, new RandomFireComputerDifficulty())
        then:
            thrown(NullPointerException)
    }

    def "Should throw when passed null computer difficulty"() {
        when:
            playerService.createNewComputerPlayer("CPU", null)
        then:
            thrown(NullPointerException)
    }

}

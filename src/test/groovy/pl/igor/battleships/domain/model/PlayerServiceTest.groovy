package pl.igor.battleships.domain.model

import pl.igor.battleships.domain.adapters.InMemoryPlayerRepository
import pl.igor.battleships.domain.model.PlayerCreator
import pl.igor.battleships.domain.model.PlayerService
import pl.igor.battleships.domain.model.RandomFireComputerDifficulty
import pl.igor.battleships.domain.outbound_ports.PlayerRepository
import pl.igor.battleships.presentation.PlayerType
import spock.lang.Specification

class PlayerServiceTest extends Specification {

    PlayerService playerService
    PlayerRepository repository

    def setup() {
        repository = new InMemoryPlayerRepository()
        playerService = new PlayerService(new PlayerCreator(), repository)
    }

    def "Should create human player"() {
        when:
            def player = playerService.createNewPlayer("Player1")
        then:
            player.getPlayerType() == PlayerType.HUMAN
            player.getName() == "Player1"
    }

    def "Should store human player"() {
        when:
            def player = playerService.createNewPlayer("Player1")
        then:
            repository.getPlayer(player.getId()) != null
    }

    def "Should create computer player"() {
        when:
            def player = playerService.createNewComputerPlayer("CPU1", new RandomFireComputerDifficulty())
        then:
            player.getPlayerType() == PlayerType.COMPUTER
            player.getName() == "CPU1"
    }

    def "Should store computer player"() {
        when:
            def player = playerService.createNewPlayer("Player1")
        then:
            repository.getPlayer(player.getId()) != null
    }
}

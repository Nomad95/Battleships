package pl.igor.battleships;

import pl.igor.battleships.application.BattleshipsFacade;
import pl.igor.battleships.application.game_configuration.StandardBattleshipsConfiguration;
import pl.igor.battleships.domain.adapters.InMemoryBattlefieldRepository;
import pl.igor.battleships.domain.adapters.InMemoryPlayerRepository;
import pl.igor.battleships.domain.model.BattleshipsDomainService;
import pl.igor.battleships.domain.ports.BattleshipsService;
import pl.igor.battleships.domain.ports.PlayerService;
import pl.igor.battleships.presentation.BattlefieldDto;
import pl.igor.battleships.presentation.Difficulty;
import pl.igor.battleships.presentation.PlayerDto;

public class BattleshipGame {
    public static void main(String[] args) {
        //first inject dependencies
        InMemoryPlayerRepository playerRepository = new InMemoryPlayerRepository();
        PlayerService playerService = new PlayerService(playerRepository);
        BattleshipsDomainService battleshipsDomainService = new BattleshipsDomainService();
        InMemoryBattlefieldRepository inMemoryBattlefieldRepository = new InMemoryBattlefieldRepository();
        BattleshipsService battleshipsService = new BattleshipsService(battleshipsDomainService, inMemoryBattlefieldRepository);
        BattleshipsFacade battleships = new BattleshipsFacade(playerService, battleshipsService);

        //create players and a game
        PlayerDto humanPlayer = battleships.createHumanPlayer("HumanPlayer1");
        //read difficulty
        PlayerDto r2D2 = battleships.createComputerPlayer("R2D2", Difficulty.EASY);

        StandardBattleshipsConfiguration config = new StandardBattleshipsConfiguration();
        BattlefieldDto battleshipsGame = battleships.createGame(humanPlayer, r2D2, config);

        while (!battleshipsGame.isOver()) {

        }

        System.out.println("asd");

        //create players
        //generate battleships
        //let the play begin
    }
}

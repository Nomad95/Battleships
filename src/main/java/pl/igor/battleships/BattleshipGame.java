package pl.igor.battleships;

import pl.igor.battleships.application.BattleshipsFacade;
import pl.igor.battleships.application.GridHelper;
import pl.igor.battleships.application.game_configuration.RandomFireComputerDifficulty;
import pl.igor.battleships.application.game_configuration.StandardBattleshipsConfiguration;
import pl.igor.battleships.domain.adapters.InMemoryBattlefieldRepository;
import pl.igor.battleships.domain.adapters.InMemoryPlayerRepository;
import pl.igor.battleships.domain.model.BattleshipsDomainService;
import pl.igor.battleships.domain.ports.BattleshipsService;
import pl.igor.battleships.domain.ports.PlayerService;

import java.util.Scanner;

public class BattleshipGame {
    public static void main(String[] args) {
        //first inject dependencies
        final InMemoryPlayerRepository playerRepository = new InMemoryPlayerRepository();
        final PlayerService playerService = new PlayerService(playerRepository);
        final BattleshipsDomainService battleshipsDomainService = new BattleshipsDomainService();
        final InMemoryBattlefieldRepository inMemoryBattlefieldRepository = new InMemoryBattlefieldRepository();
        final BattleshipsService battleshipsService = new BattleshipsService(battleshipsDomainService, inMemoryBattlefieldRepository);
        final BattleshipsFacade battleshipsFacade = new BattleshipsFacade(playerService, battleshipsService);

        StandardBattleshipsConfiguration config = new StandardBattleshipsConfiguration();

        HumanVsComputerGame game = HumanVsComputerGame.builder()
                .computerName("R2D2")
                .playerName("Bartek")
                .battleshipsFacade(battleshipsFacade)
                .computerDifficulty(new RandomFireComputerDifficulty(config.getGridSize(), new GridHelper()))
                .scanner(new Scanner(System.in))
                .gridHelper(new GridHelper())
                .battleshipsConfiguration(config)
                .build();


        game.startGame();

        System.out.println("Game concluded");
    }
}

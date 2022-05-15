package pl.igor.battleships;

import pl.igor.battleships.application.AsciiCommons;
import pl.igor.battleships.application.BattleshipsFacade;
import pl.igor.battleships.application.game_configuration.StandardBattleshipsConfiguration;
import pl.igor.battleships.domain.adapters.InMemoryBattlefieldRepository;
import pl.igor.battleships.domain.adapters.InMemoryPlayerRepository;
import pl.igor.battleships.domain.model.BattleshipsDomainService;
import pl.igor.battleships.domain.ports.BattleshipsService;
import pl.igor.battleships.domain.ports.PlayerService;
import pl.igor.battleships.presentation.BattlefieldDto;
import pl.igor.battleships.presentation.PlayerDto;
import pl.igor.battleships.presentation.ShootResultDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class BattleshipGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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
        PlayerDto r2D2 = battleships.createComputerPlayer("R2D2");

        StandardBattleshipsConfiguration config = new StandardBattleshipsConfiguration();

        List<String> availableTiles = new ArrayList<>();

        for (int i = 0; i < config.getGridSize(); i++) {
            for (int j = 0; j < config.getGridSize(); j++) {
                String tileNumber = String.format("%s%s", AsciiCommons.I.arrayIndexToLetter(i), j + 1);
                System.out.println(tileNumber);
                availableTiles.add(tileNumber);
            }
        }

        BattlefieldDto battleshipsGame = battleships.createGame(humanPlayer, r2D2, config);

        while (!battleshipsGame.isOver()) {
            System.out.println("Your turn");
            System.out.println("Enter tileNumber to shoot at: ");
            String tileNumber = scanner.nextLine();
            System.out.println("Shooting at: " + tileNumber);
            ShootResultDto shootResultDto = battleships.shootAt(battleshipsGame.getGameId(), humanPlayer.getId(), tileNumber);
            System.out.println(shootResultDto.getResult());
            while(shootResultDto.shouldRetry()) {
                System.out.println("Enter tileNumber to shoot at: ");
                tileNumber = scanner.nextLine();
                shootResultDto = battleships.shootAt(battleshipsGame.getGameId(), humanPlayer.getId(), tileNumber);
                System.out.println(shootResultDto.getResult());
            }
            if (shootResultDto.isGameOver()) {
                System.out.printf("Game over! %s won!%n", humanPlayer.getName());
                break;
            }

            System.out.println("Computer turn");

            String computerTileNumber = availableTiles.get(ThreadLocalRandom.current().nextInt(0, availableTiles.size()));
            System.out.println("Shooting at: " + computerTileNumber);
            ShootResultDto shootResultDto2 = battleships.shootAt(battleshipsGame.getGameId(), r2D2.getId(), computerTileNumber);
            System.out.println(shootResultDto2.getResult());
            while(shootResultDto2.shouldRetry()) {
                computerTileNumber = availableTiles.get(ThreadLocalRandom.current().nextInt(0, availableTiles.size()));
                System.out.println("Shooting at: " + computerTileNumber);
                shootResultDto2 = battleships.shootAt(battleshipsGame.getGameId(), r2D2.getId(), computerTileNumber);
                System.out.println(shootResultDto2.getResult());
            }
            availableTiles.remove(computerTileNumber);
            if (shootResultDto2.isGameOver()) {
                System.out.printf("Game over! %s won!%n", r2D2.getName());
                break;
            }
        }

        System.out.println("asd");

        //create players
        //generate battleships
        //let the play begin
    }
}

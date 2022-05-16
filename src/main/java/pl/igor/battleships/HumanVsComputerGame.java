package pl.igor.battleships;

import lombok.*;
import pl.igor.battleships.application.BattleshipsFacade;
import pl.igor.battleships.application.GridHelper;
import pl.igor.battleships.application.game_configuration.BattleshipsConfiguration;
import pl.igor.battleships.application.game_configuration.ComputerDifficulty;
import pl.igor.battleships.presentation.BattlefieldDto;
import pl.igor.battleships.presentation.PlayerDto;
import pl.igor.battleships.presentation.ShootResultDto;

import java.util.Scanner;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class HumanVsComputerGame {

    private final @NonNull BattleshipsFacade battleshipsFacade;
    private final @NonNull Scanner scanner;
    private final @NonNull BattleshipsConfiguration battleshipsConfiguration;
    private final @NonNull String playerName;
    private final @NonNull String computerName;
    private final @NonNull GridHelper gridHelper;
    private final @NonNull ComputerDifficulty computerDifficulty;
    
    public void startGame() {
        PlayerDto humanPlayer = battleshipsFacade.createHumanPlayer(playerName);
        PlayerDto r2D2 = battleshipsFacade.createComputerPlayer(computerName);

        BattlefieldDto battleshipsGame = battleshipsFacade.createGame(humanPlayer, r2D2, battleshipsConfiguration);

        while (!battleshipsGame.isOver()) {
            GameConclusion GameConclusionPlayer = completePlayerTurn(humanPlayer, battleshipsGame);
            if (GameConclusionPlayer.gameHasEnded) break;

            GameConclusion gameConclusionCpu = completeComputerTurn(r2D2, battleshipsGame);
            if (gameConclusionCpu.gameHasEnded) break;
        }
    }

    private GameConclusion completePlayerTurn(PlayerDto humanPlayer, BattlefieldDto battleshipsGame) {
        printToConsole("Your turn");
        printToConsole("Enter tileNumber to shoot at: ");
        String tileNumber = getUserInput();
        printToConsole("Shooting at: " + tileNumber);
        ShootResultDto shootResultDto = battleshipsFacade.shootAt(battleshipsGame.getGameId(), humanPlayer.getId(), tileNumber);
        printToConsole(shootResultDto.getResult());
        while(shootResultDto.shouldRetry()) {
            printToConsole("Enter tileNumber to shoot at: ");
            tileNumber = getUserInput();
            shootResultDto = battleshipsFacade.shootAt(battleshipsGame.getGameId(), humanPlayer.getId(), tileNumber);
            printToConsole(shootResultDto.getResult());
        }
        if (shootResultDto.isGameOver()) {
            System.out.printf("Game over! %s won!%n", humanPlayer.getName());
            return GameConclusion.hasEnded;
        }
        return GameConclusion.stillInPlay;
    }

    private GameConclusion completeComputerTurn(PlayerDto r2D2, BattlefieldDto battleshipsGame) {
        printToConsole("Computer turn");
        String computerTileNumber = computerDifficulty.getNextTileToShootAt();
        printToConsole("Shooting at: " + computerTileNumber);
        ShootResultDto shootResultDto2 = battleshipsFacade.shootAt(battleshipsGame.getGameId(), r2D2.getId(), computerTileNumber);
        printToConsole(shootResultDto2.getResult());
        computerDifficulty.acknowledgeHitting(computerTileNumber);
        while(shootResultDto2.shouldRetry()) {
            computerTileNumber = computerDifficulty.getNextTileToShootAt();
            printToConsole("Shooting at: " + computerTileNumber);
            shootResultDto2 = battleshipsFacade.shootAt(battleshipsGame.getGameId(), r2D2.getId(), computerTileNumber);
            printToConsole(shootResultDto2.getResult());
            computerDifficulty.acknowledgeHitting(computerTileNumber);
        }
        if (shootResultDto2.isGameOver()) {
            System.out.printf("Game over! %s won!%n", r2D2.getName());
            return GameConclusion.hasEnded;
        }
        return GameConclusion.stillInPlay;
    }

    private String getUserInput() {
        return scanner.nextLine();
    }

    private void printToConsole(String msg) {
        System.out.println(msg);
    }

    @Value
    private static class GameConclusion {
        static GameConclusion hasEnded = new GameConclusion(true);
        static GameConclusion stillInPlay = new GameConclusion(false);
        boolean gameHasEnded;
    }
}

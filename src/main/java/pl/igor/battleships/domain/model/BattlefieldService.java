package pl.igor.battleships.domain.model;

import lombok.RequiredArgsConstructor;
import pl.igor.battleships.application.game_configuration.BattleshipsConfiguration;
import pl.igor.battleships.application.game_configuration.PlaceableShip;
import pl.igor.battleships.presentation.BattlefieldDto;
import pl.igor.battleships.presentation.PlayerDto;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public final class BattlefieldService {
    private final PlayerService playerService;
    private final BattlefieldCreator battlefieldCreator;

    Board createBoard(UUID playerId, int dimension) {
        Player player = playerService.fetchPlayer(playerId);

        return battlefieldCreator.createBoard(player, dimension);
    }

    //todo maybe try to do it with one service?

    public BattlefieldDto createNewGame(PlayerDto player1, PlayerDto player2, BattleshipsConfiguration battleshipsConfiguration) {
        ShipPlacementStrategy shipPlacementStrategy = ShipPlacementFactory.I.getStrategyWithName(battleshipsConfiguration.getPlacementAlgorithmName());

        Player playerOne = playerService.fetchPlayer(player1.getId());
        Board playerOneBoard = createBoard(player1.getId(), battleshipsConfiguration.getGridSize());
        placeShips(playerOneBoard, battleshipsConfiguration.getShipsToPlace(), shipPlacementStrategy);

        Player playerTwo = playerService.fetchPlayer(player2.getId());
        Board playerTwoBoard = createBoard(player2.getId(), battleshipsConfiguration.getGridSize());
        placeShips(playerTwoBoard, battleshipsConfiguration.getShipsToPlace(), shipPlacementStrategy);

        Battlefield battlefield = new Battlefield(playerOne, playerOneBoard, playerTwo, playerTwoBoard);

        return BattlefieldDto.builder()
                .gameId(battlefield.getGameId())
                .player1(player1)
                .player2(player2)
                .build();
    }

    Board placeShips(Board board, List<PlaceableShip> placeableShips, ShipPlacementStrategy shipPlacementStrategy) {
        if (board.isInOngoingGame()) {
            throw new IllegalStateException("Can't populate ships, as the game with this board is currently being played");
        }

        board.populateWithShips(placeableShips, shipPlacementStrategy);

        return board;
    }
}

package pl.igor.battleships.domain.ports;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.igor.battleships.application.game_configuration.BattleshipsConfiguration;
import pl.igor.battleships.application.game_configuration.PlaceableShip;
import pl.igor.battleships.domain.ShipsMapping;
import pl.igor.battleships.domain.model.Battlefield;
import pl.igor.battleships.domain.model.BattleshipsDomainService;
import pl.igor.battleships.domain.model.Board;
import pl.igor.battleships.domain.model.ShootResult;
import pl.igor.battleships.presentation.BattlefieldDto;
import pl.igor.battleships.presentation.PlayerDto;
import pl.igor.battleships.presentation.ShootResultDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public final class BattleshipsService {


    private final BattleshipsDomainService battleshipsDomainService;
    private final BattlefieldRepository battlefieldRepository;

    public BattlefieldDto createNewGame(@NonNull PlayerDto player1, @NonNull PlayerDto player2, @NonNull BattleshipsConfiguration battleshipsConfiguration) {
        Board playerOneBoard = battleshipsDomainService.createBoard(player1.getId(), battleshipsConfiguration.getGridSize());
        List<PlaceableShip> shipsToPlace = battleshipsConfiguration.newShipsPlacementMapping();
        ShipsMapping playerOneShipsMapping = new ShipsMapping(shipsToPlace);
        battleshipsDomainService.placeShips(playerOneBoard, playerOneShipsMapping);

        Board playerTwoBoard = battleshipsDomainService.createBoard(player2.getId(), battleshipsConfiguration.getGridSize());
        List<PlaceableShip> shipsToPlace2 = battleshipsConfiguration.newShipsPlacementMapping();
        ShipsMapping playerTwoShipsMapping = new ShipsMapping(shipsToPlace2);
        battleshipsDomainService.placeShips(playerTwoBoard, playerTwoShipsMapping);

        Battlefield battlefield = new Battlefield(playerOneBoard, playerTwoBoard);

        battlefield = battlefieldRepository.save(battlefield);

        return BattlefieldDto.builder()
                .gameId(battlefield.getGameId())
                .player1(player1)
                .player2(player2)
                .build();
    }

    public ShootResultDto shootAt(@NonNull UUID gameId, @NonNull UUID shootingPlayerId, @NonNull String tileNumber) {
        Optional<Battlefield> battlefieldOptional = Optional.ofNullable(battlefieldRepository.getBattlefield(gameId));
        if (battlefieldOptional.isEmpty()) {
            return ShootResultDto.builder().result("CAN'T HIT").message("THIS GAME IS NOT AVAILABLE").build();
        }
        Battlefield battlefield = battlefieldOptional.get();
        ShootResult shootResult = battlefield.performAttackBy(shootingPlayerId, tileNumber);

        battlefieldRepository.save(battlefield);

        ShootResultDto.ShootResultDtoBuilder resultBuilder = ShootResultDto.builder()
                .result(shootResult.getShootResult())
                .message(shootResult.getMessage());

        boolean isGameOver = battlefield.isOver();

        if (isGameOver) {
            resultBuilder.gameOver(true).winner(shootingPlayerId);
        }

        return resultBuilder.build();
    }
}

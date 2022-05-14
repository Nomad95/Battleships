package pl.igor.battleships.domain.ports;

import lombok.RequiredArgsConstructor;
import pl.igor.battleships.application.game_configuration.BattleshipsConfiguration;
import pl.igor.battleships.application.game_configuration.PlaceableShip2;
import pl.igor.battleships.domain.ShipsMapping;
import pl.igor.battleships.domain.model.Battlefield;
import pl.igor.battleships.domain.model.BattleshipsDomainService;
import pl.igor.battleships.domain.model.Board;
import pl.igor.battleships.presentation.BattlefieldDto;
import pl.igor.battleships.presentation.PlayerDto;

import java.util.List;

@RequiredArgsConstructor
public final class BattleshipsService {

    private final BattleshipsDomainService battleshipsDomainService;
    private final BattlefieldRepository battlefieldRepository;

    public BattlefieldDto createNewGame(PlayerDto player1, PlayerDto player2, BattleshipsConfiguration battleshipsConfiguration) {
        Board playerOneBoard = battleshipsDomainService.createBoard(player1.getId(), battleshipsConfiguration.getGridSize());
        List<PlaceableShip2> shipsToPlace = battleshipsConfiguration.newShipsPlacementMapping();
        ShipsMapping playerOneShipsMapping = new ShipsMapping(shipsToPlace);
        battleshipsDomainService.placeShips(playerOneBoard, playerOneShipsMapping);

        Board playerTwoBoard = battleshipsDomainService.createBoard(player2.getId(), battleshipsConfiguration.getGridSize());
        List<PlaceableShip2> shipsToPlace2 = battleshipsConfiguration.newShipsPlacementMapping();
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

}

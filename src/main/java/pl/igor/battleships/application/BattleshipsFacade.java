package pl.igor.battleships.application;

import lombok.RequiredArgsConstructor;
import pl.igor.battleships.application.game_configuration.BattleshipsConfiguration;
import pl.igor.battleships.domain.ports.BattleshipsService;
import pl.igor.battleships.domain.ports.PlayerService;
import pl.igor.battleships.presentation.BattlefieldDto;
import pl.igor.battleships.presentation.PlayerDto;
import pl.igor.battleships.presentation.ShootResultDto;

import java.util.UUID;

//0 = {PlaceableShip@1063} "PlaceableShip(shipSize=4, tileNumbers=[D4, G4, F4, E4])"
//1 = {PlaceableShip@1064} "PlaceableShip(shipSize=4, tileNumbers=[I1, H1, G1, J1])"
//2 = {PlaceableShip@1065} "PlaceableShip(shipSize=5, tileNumbers=[I2, I3, I4, I5, I6])"



@RequiredArgsConstructor
public class BattleshipsFacade {

    private final PlayerService playersService;
    private final BattleshipsService battlefieldService;

    public PlayerDto createHumanPlayer(String name) {
        return playersService.createNewPlayer(name);
    }

    public PlayerDto createComputerPlayer(String name) {
        return playersService.createNewComputerPlayer(name);
    }

    public BattlefieldDto createGame(PlayerDto player1, PlayerDto player2, BattleshipsConfiguration battleshipsConfiguration) {
        return battlefieldService.createNewGame(player1, player2, battleshipsConfiguration);
    }

    public ShootResultDto shootAt(UUID gameId, UUID playerId, String tileNumber) {
        return battlefieldService.shootAt(gameId, playerId, tileNumber);
    }
}

package pl.igor.battleships.application;

import pl.igor.battleships.application.game_configuration.BattleshipsConfiguration;
import pl.igor.battleships.presentation.BattlefieldDto;
import pl.igor.battleships.presentation.PlayerDto;

public interface Game {
    BattlefieldDto createNewGame(PlayerDto player1, PlayerDto player2, BattleshipsConfiguration battleshipsConfiguration);
}

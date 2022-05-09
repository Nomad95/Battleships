package pl.igor.battleships.application;

import pl.igor.battleships.domain.model.ComputerDifficulty;
import pl.igor.battleships.presentation.PlayerDto;

public interface Players {
    PlayerDto createNewPlayer(String playerName);
    PlayerDto createNewComputerPlayer(String playerName, ComputerDifficulty computerDifficulty);
}

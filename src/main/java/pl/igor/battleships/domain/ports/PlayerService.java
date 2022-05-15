package pl.igor.battleships.domain.ports;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.igor.battleships.domain.model.ComputerDifficulty;
import pl.igor.battleships.domain.model.ComputerPlayer;
import pl.igor.battleships.domain.model.Player;
import pl.igor.battleships.domain.model.StandardPlayer;
import pl.igor.battleships.presentation.PlayerDto;
import pl.igor.battleships.presentation.PlayerType;

@RequiredArgsConstructor
public final class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerDto createNewPlayer(@NonNull String playerName) {
        Player player = new StandardPlayer(playerName);
        playerRepository.save(player);
        return PlayerDto.builder()
                .id(player.getId())
                .name(player.getName())
                .playerType(PlayerType.HUMAN)
                .build();
    }

    public PlayerDto createNewComputerPlayer(@NonNull String playerName, @NonNull ComputerDifficulty computerDifficulty) {
        ComputerPlayer player = new ComputerPlayer(playerName, computerDifficulty);
        playerRepository.save(player);
        return PlayerDto.builder()
                .id(player.getId())
                .name(player.getName())
                .playerType(PlayerType.COMPUTER)
                .build();
    }

}

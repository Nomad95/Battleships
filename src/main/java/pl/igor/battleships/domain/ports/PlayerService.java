package pl.igor.battleships.domain.ports;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.igor.battleships.domain.model.Player;
import pl.igor.battleships.presentation.PlayerDto;
import pl.igor.battleships.presentation.PlayerType;

@RequiredArgsConstructor
public final class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerDto createNewPlayer(@NonNull String playerName) {
        Player player = new Player(playerName, false);
        playerRepository.save(player);
        return PlayerDto.builder()
                .id(player.getId())
                .name(player.getName())
                .playerType(PlayerType.HUMAN)
                .build();
    }

    public PlayerDto createNewComputerPlayer(@NonNull String playerName) {
        Player player = new Player(playerName, true);
        playerRepository.save(player);
        return PlayerDto.builder()
                .id(player.getId())
                .name(player.getName())
                .playerType(PlayerType.COMPUTER)
                .build();
    }

}

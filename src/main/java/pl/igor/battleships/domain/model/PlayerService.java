package pl.igor.battleships.domain.model;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.igor.battleships.application.Players;
import pl.igor.battleships.domain.outbound_ports.PlayerRepository;
import pl.igor.battleships.presentation.PlayerDto;
import pl.igor.battleships.presentation.PlayerType;

import java.util.UUID;

@RequiredArgsConstructor
public final class PlayerService implements Players {

    private final PlayerCreator playerCreator;
    private final PlayerRepository playerRepository;

    public PlayerDto createNewPlayer(String playerName) {
        Player player = playerCreator.createPlayer(playerName);
        playerRepository.save(player);
        return PlayerDto.builder()
                .id(player.getId())
                .name(player.getName())
                .playerType(PlayerType.HUMAN)
                .build();
    }

    public PlayerDto createNewComputerPlayer(String playerName, ComputerDifficulty computerDifficulty) {
        Player player = playerCreator.createComputerPlayer(playerName, computerDifficulty);
        playerRepository.save(player);
        return PlayerDto.builder()
                .id(player.getId())
                .name(player.getName())
                .playerType(PlayerType.COMPUTER)
                .build();
    }

    Player fetchPlayer(@NonNull UUID playerId) {
        return playerRepository.getPlayer(playerId);
    }
}

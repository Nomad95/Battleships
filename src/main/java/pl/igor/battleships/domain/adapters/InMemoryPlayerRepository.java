package pl.igor.battleships.domain.adapters;

import lombok.NonNull;
import pl.igor.battleships.domain.model.Player;
import pl.igor.battleships.domain.ports.PlayerRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryPlayerRepository implements PlayerRepository {

    private final Map<UUID, Player> players = new HashMap<>();


    @Override
    public Player save(@NonNull Player player) {
        return players.put(player.getId(), player);
    }

    @Override
    public Player getPlayer(@NonNull UUID playerId) {
        return players.get(playerId);
    }
}

package pl.igor.battleships.domain.ports;

import pl.igor.battleships.domain.model.Player;

import java.util.UUID;

public interface PlayerRepository {

    Player save(Player player);
    Player getPlayer(UUID playerId);
}

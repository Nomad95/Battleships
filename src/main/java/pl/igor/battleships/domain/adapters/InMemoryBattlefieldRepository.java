package pl.igor.battleships.domain.adapters;

import pl.igor.battleships.domain.model.Battlefield;
import pl.igor.battleships.domain.outbound_ports.BattlefieldRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryBattlefieldRepository implements BattlefieldRepository {

    private final Map<UUID, Battlefield> battlefields = new HashMap<>();

    @Override
    public Battlefield save(Battlefield battlefield) {
        return battlefields.put(battlefield.getGameId(), battlefield);
    }

    @Override
    public Battlefield getBattlefield(UUID battlefieldId) {
        return battlefields.get(battlefieldId);
    }

}

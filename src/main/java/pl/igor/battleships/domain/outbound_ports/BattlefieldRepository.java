package pl.igor.battleships.domain.outbound_ports;

import pl.igor.battleships.domain.model.Battlefield;

import java.util.UUID;

public interface BattlefieldRepository {
    Battlefield save(Battlefield battlefield);
    Battlefield getBattlefield(UUID battlefieldId);
}

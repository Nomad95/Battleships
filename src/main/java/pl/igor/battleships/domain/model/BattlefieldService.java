package pl.igor.battleships.domain.model;

import lombok.RequiredArgsConstructor;
import pl.igor.battleships.domain.ports.BattlefieldRepository;
import pl.igor.battleships.domain.ports.PlayerService;

@RequiredArgsConstructor
public final class BattlefieldService {
    private final PlayerService playerService;
    private final BattlefieldRepository battlefieldRepository;

}

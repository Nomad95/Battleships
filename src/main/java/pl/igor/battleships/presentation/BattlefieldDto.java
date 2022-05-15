package pl.igor.battleships.presentation;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class BattlefieldDto {
    UUID gameId;
    PlayerDto player1;
    PlayerDto player2;
    boolean isOver;
}

package pl.igor.battleships.presentation;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class PlayerDto {
    String name;
    UUID id;
    PlayerType playerType;
}

package pl.igor.battleships.application.game_configuration;

import lombok.Value;

@Value
public class ShipConfig {
    String shipType;
    int shipSize;
    int numberOfShips;
}

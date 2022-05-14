package pl.igor.battleships.application.game_configuration;

import lombok.Value;

import java.util.Set;

@Value
public class PlaceableShip2 {
    int shipSize;
    Set<String> tileNumbers;
}
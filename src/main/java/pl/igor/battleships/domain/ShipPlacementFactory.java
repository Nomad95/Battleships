package pl.igor.battleships.domain;

import pl.igor.battleships.application.GridTraverser;
import pl.igor.battleships.application.game_configuration.AdvancedShipPlacementStrategy;
import pl.igor.battleships.application.game_configuration.ShipPlacementStrategy;

public enum ShipPlacementFactory {
    I;

    ShipPlacementStrategy getStrategyWithName(String name) {
        switch (name.toUpperCase()) {
            case "ADVANCED": return new AdvancedShipPlacementStrategy(new GridTraverser());
            default: throw new IllegalArgumentException(String.format("No strategy named %s available", name));
        }
    }
}

package pl.igor.battleships.domain;

import pl.igor.battleships.application.game_configuration.ShipPlacementStrategy;
import pl.igor.battleships.domain.model.AdvancedShipPlacementStrategy;

public enum ShipPlacementFactory {
    I;

    ShipPlacementStrategy getStrategyWithName(String name) {
        switch (name.toUpperCase()) {
            case "ADVANCED": return new AdvancedShipPlacementStrategy();
            default: throw new IllegalArgumentException(String.format("No strategy named %s available", name));
        }
    }
}

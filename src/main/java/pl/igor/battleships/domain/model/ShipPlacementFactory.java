package pl.igor.battleships.domain.model;

public enum ShipPlacementFactory {
    I;

    ShipPlacementStrategy getStrategyWithName(String name) {
        switch (name.toUpperCase()) {
            case "ADVANCED": return new AdvancedShipPlacementStrategy();
            default: throw new IllegalArgumentException(String.format("No strategy named %s available", name));
        }
    }
}

package pl.igor.battleships.application.game_configuration

import org.apache.commons.collections4.CollectionUtils
import pl.igor.battleships.application.GridTraverser
import pl.igor.battleships.application.game_configuration.AdvancedShipPlacementStrategy
import pl.igor.battleships.application.game_configuration.PlaceableShip
import pl.igor.battleships.application.game_configuration.ShipConfig
import spock.lang.Specification

import java.util.stream.Collectors

class AdvancedShipPlacementTest extends Specification {

    GridTraverser traverser
    AdvancedShipPlacementStrategy placementStrategy

    def setup() {
        placementStrategy = new AdvancedShipPlacementStrategy(new GridTraverser())
        traverser = new GridTraverser()
    }

    def "should place ships apart from each other of at least one tile"() {
        given:
            def shipDefinitions = List.of(
                    new ShipConfig("large", 5, 1),
                    new ShipConfig("small", 3, 1),
                    new ShipConfig("medium", 4, 1))

        when:
            def ships = placementStrategy.getShips(10, shipDefinitions)

        then:
            isValid(ships)
    }

    def "Number of ships must be equal to defined"() {
        given:
            def shipDefinitions = List.of(
                    new ShipConfig("large", 5, 1),
                    new ShipConfig("small", 3, 4),
                    new ShipConfig("medium", 4, 1))

        when:
            def ships = placementStrategy.getShips(10, shipDefinitions)

        then:
            ships.size() == 6
    }

    private boolean isValid(List<PlaceableShip> ships) {
        for (ship in ships) {
            def collect = ship.tileNumbers

            //get other ships without current
            def allShipsCopy = new ArrayList<PlaceableShip>(ships)
            allShipsCopy.remove(ship)

            //check if tiles interfere with other ships
            def otherShipsTiles = allShipsCopy.stream().map(plShip -> plShip.tileNumbers).collect(Collectors.toSet())

            def haveElementsInCommon = CollectionUtils.containsAny(collect, otherShipsTiles)

            if (haveElementsInCommon)
                return false;
        }

        return true
    }

    def "algorithm should tell if it cant place given tiles because of lack of size"() {
        given:
            def shipDefinitions = List.of(
                    new ShipConfig("large", 20, 1),
                    new ShipConfig("small", 10, 1),
                    new ShipConfig("medium", 5, 1))

        when:
            placementStrategy.getShips(2, shipDefinitions)

        then:
            thrown(IllegalArgumentException)
    }

    def "algorithm should tell if it cant place given tiles because it cant properly place all ships"() {
        given:
            def shipDefinitions = List.of(
                    new ShipConfig("small", 4, 5))

        when:
            placementStrategy.getShips(3, shipDefinitions)

        then:
            thrown(IllegalArgumentException)
    }

    def "algorithm should not place any ships if no ships are defined"() {
        given:
            List<ShipConfig> shipDefinitions = List.of()

        when:
            def ships = placementStrategy.getShips(2, shipDefinitions)

        then:
            ships.size() == 0
    }

}

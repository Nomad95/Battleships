package pl.igor.battleships.domain.model


import spock.lang.Specification

class GridNavigationTest extends Specification {

    def "should point to bottom left tile number"() {
        given:
            def tile = new Tile("C5")
        when:
            def tileNumber = tile.getTileNumberAtBottomLeft()
        then:
            tileNumber == "D4"
    }

    def "should point to bottom tile number"() {
        given:
            def tile = new Tile("C5")
        when:
            def tileNumber = tile.getTileNumberAtBottom()
        then:
            tileNumber == "D5"
    }

    def "should point to bottom right tile number"() {
        given:
            def tile = new Tile("C5")
        when:
            def tileNumber = tile.getTileNumberAtBottomRight()
        then:
            tileNumber == "D6"
    }

    def "should point to right tile number"() {
        given:
            def tile = new Tile("C5")
        when:
            def tileNumber = tile.getTileNumberAtRight()
        then:
            tileNumber == "C6"
    }

    def "should point to left tile number"() {
        given:
            def tile = new Tile("C5")
        when:
            def tileNumber = tile.getTileNumberAtLeft()
        then:
            tileNumber == "C4"
    }

    def "should point to top left tile number"() {
        given:
            def tile = new Tile("C5")
        when:
            def tileNumber = tile.getTileNumberAtTopLeft()
        then:
            tileNumber == "B4"
    }

    def "should point to top tile number"() {
        given:
            def tile = new Tile("C5")
        when:
            def tileNumber = tile.getTileNumberAtTop()
        then:
            tileNumber == "B5"
    }

    def "should point to top right tile number"() {
        given:
            def tile = new Tile("C5")
        when:
            def tileNumber = tile.getTileNumberAtTopRight()
        then:
            tileNumber == "B6"
    }
}

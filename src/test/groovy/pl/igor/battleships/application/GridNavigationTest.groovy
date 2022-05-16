package pl.igor.battleships.application


import spock.lang.Specification

class GridNavigationTest extends Specification {

    GridHelper traverser

    def setup() {
        this.traverser = new GridHelper()
    }

    def "should point to left tile number"() {
        expect:
            traverser.numberToCoordinates("C5").moveLeft().toTileNumber() == "B5"
    }

    def "should point to bottom tile number"() {
        expect:
            traverser.numberToCoordinates("C5").moveDown().toTileNumber() == "C6"
    }

    def "should point to bottom right tile number"() {
        expect:
            traverser.numberToCoordinates("C5").moveRight().toTileNumber() == "D5"
    }

    def "should point to upper tile number"() {
        expect:
            traverser.numberToCoordinates("C5").moveUp().toTileNumber() == "C4"
    }

}

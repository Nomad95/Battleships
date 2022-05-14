package pl.igor.battleships.domain.model;

public class StandardPlayer extends Player {

    public StandardPlayer(String name) {
        super(name);
    }

    @Override
    boolean isHuman() {
        return true;
    }

    @Override
    boolean isComputer() {
        return false;
    }
}

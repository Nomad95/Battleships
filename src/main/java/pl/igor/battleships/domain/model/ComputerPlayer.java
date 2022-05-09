package pl.igor.battleships.domain.model;

class ComputerPlayer extends Player {

    private final ComputerDifficulty computerDifficulty;

    ComputerPlayer(String name, ComputerDifficulty computerDifficulty) {
        super(name);
        this.computerDifficulty = computerDifficulty;
    }

    @Override
    public boolean isHuman() {
        return false;
    }

    @Override
    public boolean isComputer() {
        return true;
    }
}

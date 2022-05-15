package pl.igor.battleships.application;

import pl.igor.battleships.domain.model.ComputerDifficulty;
import pl.igor.battleships.domain.model.RandomFireComputerDifficulty;
import pl.igor.battleships.presentation.Difficulty;

public enum ComputerDifficultyFactory {
    I;

    public ComputerDifficulty getDifficulty(Difficulty difficulty) {
        if (difficulty == Difficulty.EASY) {
            return new RandomFireComputerDifficulty();
        }

        throw new IllegalArgumentException("Difficulty not defined");
    }
}

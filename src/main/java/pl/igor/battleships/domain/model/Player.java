package pl.igor.battleships.domain.model;

import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

public class Player {

    @Getter
    @NonNull
    private final String name;

    @Getter
    @NonNull
    private final UUID id;

    private final boolean computer;

    public Player(@NonNull String name, boolean computer) {
        this.name = name;
        this.computer = computer;
        this.id = UUID.randomUUID();
    }

    public boolean isHuman() {
        return !isComputer();
    }

    public boolean isComputer() {
        return computer;
    }
}

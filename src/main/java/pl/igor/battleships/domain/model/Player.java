package pl.igor.battleships.domain.model;

import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

public abstract class Player {

    @Getter
    @NonNull
    private final String name;

    @Getter
    @NonNull
    private final UUID id;

    public Player(@NonNull String name) {
        this.name = name;
        this.id = UUID.randomUUID();
    }

    abstract boolean isHuman();
    abstract boolean isComputer();
}

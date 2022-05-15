package pl.igor.battleships.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@AllArgsConstructor
public class ShootResult {

    static final ShootResult HIT = new ShootResult("HIT");
    static final ShootResult MISSED = new ShootResult("MISS");
    static final ShootResult ALREADY_HIT = new ShootResult("ALREADY HIT");
    static final ShootResult SINK = new ShootResult("SINK");
    static final ShootResult TILE_INCORRECT = new ShootResult("CAN'T HIT", "TILE INCORRECT");
    static final ShootResult INCORRECT_PLAYER = new ShootResult("CAN'T HIT", "PLAYER DOESNT PLAY THIS GAME");
    static final ShootResult WRONG_TILE = new ShootResult("CAN'T HIT", "WRONG TILE");

    String shootResult;
    String message;

    ShootResult(String result) {
        this(result, "");
    }
}

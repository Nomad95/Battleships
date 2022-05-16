package pl.igor.battleships.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@AllArgsConstructor
public class ShootResult {

    public static final String CAN_T_HIT = "CAN'T HIT";
    static final ShootResult HIT = new ShootResult("HIT");
    static final ShootResult MISSED = new ShootResult("MISS");
    static final ShootResult ALREADY_HIT = new ShootResult("ALREADY HIT");
    static final ShootResult SINK = new ShootResult("SINK");
    static final ShootResult TILE_INCORRECT = new ShootResult(CAN_T_HIT, "TILE INCORRECT");
    static final ShootResult INCORRECT_PLAYER = new ShootResult(CAN_T_HIT, "PLAYER DOESNT PLAY THIS GAME");
    static final ShootResult WRONG_TILE = new ShootResult(CAN_T_HIT, "WRONG TILE");
    public static final ShootResult GAME_CANT_BE_FOUND = new ShootResult(CAN_T_HIT, "THIS GAME IS NOT AVAILABLE");

    String shootResult;
    String message;

    ShootResult(String result) {
        this(result, "");
    }

    public boolean shouldRetry() {
        return this.shootResult.equals("ALREADY HIT") || this.shootResult.equals(CAN_T_HIT);
    }
}

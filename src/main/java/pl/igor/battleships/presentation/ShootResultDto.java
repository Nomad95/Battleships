package pl.igor.battleships.presentation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.UUID;

@Value
@Getter
@Builder
@AllArgsConstructor
public class ShootResultDto {
    String result;
    String message;
    boolean gameOver;
    UUID winner;
    boolean shouldRetry;


    public boolean shouldRetry() {
        return shouldRetry;
    }

    //2 = {PlaceableShip@1065} "PlaceableShip(shipSize=5, tileNumbers=[B4, B5, B6, B7, B8])"
    //1 = {PlaceableShip@1064} "PlaceableShip(shipSize=4, tileNumbers=[D4, C4, F4, E4])"
    //0 = {PlaceableShip@1063} "PlaceableShip(shipSize=4, tileNumbers=[I9, H9, G9, J9])"
}

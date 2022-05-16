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
}

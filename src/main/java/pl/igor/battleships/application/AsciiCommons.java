package pl.igor.battleships.application;

public enum AsciiCommons {
    I;

    public char arrayIndexToLetter(int index) {
        return (char) (index + 'A');
    }

    public int letterToArrayIndex(char letter) {
        return (letter - 'A');
    }
}

package no.uib.inf101.tetris.model.tetromino;

public class PatternedTetrominoFactory implements TetrominoFactory {
  private final String pattern;
  private int currentIndex;

  public PatternedTetrominoFactory(String pattern) {
    this.pattern = pattern;
    currentIndex = 0;
  }

  @Override
  public Tetromino getNext() {
    char typeSymbol = pattern.charAt(currentIndex);
    currentIndex = (currentIndex + 1) % pattern.length();

    return Tetromino.newTetromino(typeSymbol);
  }
}

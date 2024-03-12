package no.uib.inf101.tetris.model.tetromino;

public interface TetrominoFactory {
  /**
   * Get a new tetromino
   *
   * @return A new tetromino with top left corner at position (0,0),
   * what the piece will be is specified by the implementing class
   */
  Tetromino getNext();
}

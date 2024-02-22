package no.uib.inf101.tetris.model.tetromino;

public interface TetrominoFactory {
  /** @return a new tetromino to add to the game */
  Tetromino getNext();
}

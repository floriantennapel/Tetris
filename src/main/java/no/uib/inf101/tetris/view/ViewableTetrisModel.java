package no.uib.inf101.tetris.view;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.model.GameState;
import no.uib.inf101.tetris.model.tetromino.Tetromino;

public interface ViewableTetrisModel {
  /** @return Dimension of grid */
  GridDimension getDimension();

  /** @return Every GridCell object on board, does not include currently moving piece */
  Iterable<GridCell<Character>> getTilesOnBoard();

  /** @return Tiles of currently moving tetromino */
  Iterable<GridCell<Character>> getMovingTetrominoTiles();

  /** current state of game
   * @return one of the states specified by the GameState enum */
  GameState getGameState();

  /** get score of game */
  int getScore();

  /** get current level of game */
  int getLevel();

  /** get tiles of tetromino that will be added after the currently moving piece */
  Iterable<GridCell<Character>> getNext();

  /** get position of piece as if it has been dropped */
  Tetromino getDroppedPosition();

  /** get high score, this is read from a file, if there is an error it defaults to 0 */
  int getHighScore();

  /**
   * Is the music currently playing
   * @return true if music is on, false if music is disabled
   */
  boolean getMusicState();
}

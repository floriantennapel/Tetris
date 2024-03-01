package no.uib.inf101.tetris.view;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.model.GameState;
import no.uib.inf101.tetris.model.tetromino.Tetromino;

public interface ViewableTetrisModel {
  /** @return Dimension of grid */
  GridDimension getDimension();

  /** @return Every tile from tetris board containing position and symbol */
  Iterable<GridCell<Character>> getTilesOnBoard();

  /** @return Tiles of currently moving tetromino */
  Iterable<GridCell<Character>> getMovingTetrominoTiles();

  /** current state of game */
  GameState getGameState();

  /** get score of game */
  int getScore();

  /** get current level of game */
  int getLevel();

  /** get tiles of next tetromino */
  Iterable<GridCell<Character>> getNext();

  /** get position of piece as if it has been dropped */
  Tetromino getDroppedPosition();

  /** get high score, this is read from a file, if there is an error it defaults to 0 */
  int getHighScore();
}

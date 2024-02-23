package no.uib.inf101.tetris.view;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.model.GameState;

public interface ViewableTetrisModel {
  /** @return Dimension of grid */
  GridDimension getDimension();

  /** @return Every tile from tetris board containing position and symbol */
  Iterable<GridCell<Character>> getTilesOnBoard();

  /** @return Tiles of currently moving tetromino */
  Iterable<GridCell<Character>> getMovingTetrominoTiles();

  /** current state of game */
  GameState getGameState();
}

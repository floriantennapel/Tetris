package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.model.tetromino.Tetromino;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.ViewableTetrisModel;

public class TetrisModel implements ViewableTetrisModel {
  private final TetrisBoard board;
  private final TetrominoFactory tetrominoFactory;
  private final Tetromino currentlyFallingTetromino;

  public TetrisModel(TetrisBoard board, TetrominoFactory tetrominoFactory) {
    this.board = board;
    this.tetrominoFactory = tetrominoFactory;

    currentlyFallingTetromino = this.tetrominoFactory.getNext().shiftedToTopCenterOf(board);
  }

  @Override
  public GridDimension getDimension() {
    return board;
  }

  @Override
  public Iterable<GridCell<Character>> getTilesOnBoard() {
    return board;
  }

  @Override
  public Iterable<GridCell<Character>> getMovingTetrominoTiles() {
    return currentlyFallingTetromino;
  }
}

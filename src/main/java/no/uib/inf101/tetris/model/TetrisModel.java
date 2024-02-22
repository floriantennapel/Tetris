package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.controller.ControllableTetrisModel;
import no.uib.inf101.tetris.model.tetromino.Tetromino;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.ViewableTetrisModel;

public class TetrisModel implements ViewableTetrisModel, ControllableTetrisModel {
  private final TetrisBoard board;
  private final TetrominoFactory tetrominoFactory;
  private Tetromino currentlyFallingTetromino;

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

  @Override
  public boolean moveTetromino(int deltaRow, int deltaCol) {
    Tetromino moved = currentlyFallingTetromino.shiftedBy(deltaRow, deltaCol);

    if (!isValidPosition(moved)) {
      return false;
    }

    currentlyFallingTetromino = moved;
    return true;
  }

  //TODO implement super rotation system
  @Override
  public boolean rotateTetromino() {
    Tetromino rotated = currentlyFallingTetromino.rotated();

    if (!isValidPosition(rotated)) {
      return false;
    }

    currentlyFallingTetromino = rotated;
    return true;
  }

  private boolean isValidPosition(Tetromino tetromino) {
    for (GridCell<Character> gc : tetromino) {
      // out of bounds
      if (!board.positionIsOnGrid(gc.pos())) {
        return false;
      }
      // cell is occupied
      if (board.get(gc.pos()) != '-') {
        return false;
      }
    }

    return true;
  }
}

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
  private GameState gameState;

  public TetrisModel(TetrisBoard board, TetrominoFactory tetrominoFactory) {
    this.board = board;
    this.tetrominoFactory = tetrominoFactory;

    currentlyFallingTetromino = this.tetrominoFactory.getNext().shiftedToTopCenterOf(board);
    gameState = GameState.ACTIVE_GAME;
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
  public GameState getGameState() {
    return gameState;
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

  @Override
  public void dropTetromino() {
    Tetromino dropped = currentlyFallingTetromino.shiftedBy(0, 0); // logically equal to clone()

    while (isValidPosition(dropped)) {
      dropped = dropped.shiftedBy(1, 0);
    }

    // we actually did move, ensuring we don't move up out of screen
    if (!dropped.equals(currentlyFallingTetromino)) {
      // moved one too far down
      dropped = dropped.shiftedBy(-1, 0);
    }

    currentlyFallingTetromino = dropped;
    addTetrominoToBoard();
  }

  private void newTetromino() {
    currentlyFallingTetromino = tetrominoFactory.getNext().shiftedToTopCenterOf(board);

    if (!isValidPosition(currentlyFallingTetromino)) {
      gameState = GameState.GAME_OVER;
    }
  }

  private void addTetrominoToBoard() {
    for (GridCell<Character> gc : currentlyFallingTetromino) {
      board.set(gc.pos(), gc.value());
    }

    newTetromino();
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

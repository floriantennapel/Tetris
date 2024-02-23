package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.controller.ControllableTetrisModel;
import no.uib.inf101.tetris.model.tetromino.Tetromino;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.ViewableTetrisModel;


public class TetrisModel implements ViewableTetrisModel, ControllableTetrisModel {
  // given amount of rows removed, how much should score increase, rows removed is index
  // based on original NES scoring system https://tetris.wiki/Scoring
  private static final int[] scoring = {0, 40, 100, 300, 1200};

  private final TetrisBoard board;
  private final TetrominoFactory tetrominoFactory;

  private Tetromino currentlyFallingTetromino;
  private GameState gameState;
  private int points = 0;

  // milliseconds between every falling movement
  // increases with level
  private int deltaTime;

  // difficulty of game, increases with amount of points collected
  private int level = 1;

  public TetrisModel(TetrisBoard board, TetrominoFactory tetrominoFactory) {
    this.board = board;
    this.tetrominoFactory = tetrominoFactory;

    currentlyFallingTetromino = this.tetrominoFactory.getNext().shiftedToTopCenterOf(board);
    gameState = GameState.ACTIVE_GAME;
    deltaTime = 1000;
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
  public int getDeltaTime() {
    return deltaTime;
  }

  @Override
  public void clockTick() {
    if (!moveTetromino(1, 0)) {
      addTetrominoToBoardAndClearRows();
    }
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
    addTetrominoToBoardAndClearRows();
  }

  private void newTetromino() {
    currentlyFallingTetromino = tetrominoFactory.getNext().shiftedToTopCenterOf(board);

    if (!isValidPosition(currentlyFallingTetromino)) {
      gameState = GameState.GAME_OVER;
    }
  }

  private void addTetrominoToBoardAndClearRows() {
    for (GridCell<Character> gc : currentlyFallingTetromino) {
      board.set(gc.pos(), gc.value());
    }

    int rowsRemoved = board.clearRows();
    // original NES scoring system https://tetris.wiki/Scoring
    points += scoring[rowsRemoved] * level;

    System.out.println(points);

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

  public int getPoints() {
    return points;
  }

  public int getLevel() {
    return level;
  }
}

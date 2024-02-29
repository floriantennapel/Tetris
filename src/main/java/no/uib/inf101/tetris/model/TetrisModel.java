package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.controller.ControllableTetrisModel;
import no.uib.inf101.tetris.model.tetromino.Tetromino;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.ViewableTetrisModel;


public class TetrisModel implements ViewableTetrisModel, ControllableTetrisModel {
  // given amount of rows cleared, how much should score increase, amount of rows cleared is index
  // based on original NES scoring system https://tetris.wiki/Scoring
  private static final int[] SCORING = {0, 40, 100, 300, 1200};

  // level in which timer deltaTime will be 0
  private static final int FINAL_LEVEL = 20;
  private static final int START_DELTA_TIME = 1000;

  private final TetrominoFactory tetrominoFactory;

  private TetrisBoard board;
  private Tetromino nextTetromino;
  private Tetromino currentlyFallingTetromino;
  private GameState gameState;
  private int score;
  private int linesCleared;

  // milliseconds between every falling movement
  // increases with level
  private int deltaTime;

  // difficulty of game, also scales how many points are given for row clearing
  private int level;

  public TetrisModel(TetrisBoard board, TetrominoFactory tetrominoFactory) {
    this.board = board;
    this.tetrominoFactory = tetrominoFactory;

    currentlyFallingTetromino = this.tetrominoFactory.getNext().shiftedToTopCenterOf(board);
    nextTetromino = this.tetrominoFactory.getNext();
    gameState = GameState.ACTIVE_GAME;
    deltaTime = START_DELTA_TIME;
    level = 1;
    score = 0;
    linesCleared = 0;
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
  public void setGameState(GameState gameState) {
    this.gameState = gameState;
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
  public void resetGame() {
    board = new TetrisBoard(board.getRows(), board.getCols());

    gameState = GameState.ACTIVE_GAME;
    deltaTime = START_DELTA_TIME;
    level = 1;
    score = 0;
    linesCleared = 0;
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
    currentlyFallingTetromino = getDroppedPosition();
    addTetrominoToBoardAndClearRows();
  }

  private void newTetromino() {
    currentlyFallingTetromino = nextTetromino.shiftedToTopCenterOf(board);
    nextTetromino = tetrominoFactory.getNext();

    if (!isValidPosition(currentlyFallingTetromino)) {
      gameState = GameState.GAME_OVER;
    }
  }

  private void addTetrominoToBoardAndClearRows() {
    for (GridCell<Character> gc : currentlyFallingTetromino) {
      board.set(gc.pos(), gc.value());
    }

    int rowsCleared = board.clearRows();

    addPointsAndIncrementDifficulty(rowsCleared);
    newTetromino();
  }

  private void addPointsAndIncrementDifficulty(int rowsCleared) {
    score += SCORING[rowsCleared] * level;
    linesCleared += rowsCleared;
    level = linesCleared / 10 + 1;

    deltaTime = START_DELTA_TIME - (START_DELTA_TIME * (level - 1) / (FINAL_LEVEL - 1));
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

  @Override
  public int getScore() {
    return score;
  }

  @Override
  public int getLevel() {
    return level;
  }

  @Override
  public Iterable<GridCell<Character>> getNext() {
    return nextTetromino;
  }

  @Override
  public Tetromino getDroppedPosition() {
    Tetromino dropped = currentlyFallingTetromino.shiftedBy(0, 0); // logically equal to clone()

    while (true) {
      Tetromino nextPosition = dropped.shiftedBy(1, 0);
      if (!isValidPosition(nextPosition)) {
        break;
      }

      dropped = nextPosition;
    }

    return dropped;
  }
}

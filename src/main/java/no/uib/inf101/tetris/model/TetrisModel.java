package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.controller.ControllableTetrisModel;
import no.uib.inf101.tetris.model.tetromino.Tetromino;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.ViewableTetrisModel;

import java.io.*;
import java.util.List;
import java.util.Optional;


public class TetrisModel implements ViewableTetrisModel, ControllableTetrisModel {
  // given amount of rows cleared, how much should score increase, amount of rows cleared is index
  // based on original NES scoring system https://tetris.wiki/Scoring
  private static final int[] SCORING = {0, 40, 100, 300, 1200};

  // level in which timer deltaTime will be 0
  private static final int FINAL_LEVEL = 20;
  private static final int START_DELTA_TIME = 1000;

  // file is stored in root of current directory, writing to the resources folder is not allowed at runtime
  private static final String HIGH_SCORE_FILE = ".highscore.txt";

  private final TetrominoFactory tetrominoFactory;

  private TetrisBoard board;
  private Tetromino nextTetromino;
  private Tetromino currentlyFallingTetromino;
  private GameState gameState;
  private int score;
  private int linesCleared;
  private int highScore;
  private boolean soundOn;

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
    gameState = GameState.START_MENU;
    deltaTime = START_DELTA_TIME;
    level = 1;
    score = 0;
    linesCleared = 0;
    soundOn = false;

    highScore = readHighScore();
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

    gameState = GameState.START_MENU;
    deltaTime = START_DELTA_TIME;
    level = 1;
    score = 0;
    linesCleared = 0;
    highScore = readHighScore();
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

  @Override
  public boolean rotateTetromino(boolean clockwise) {
    Tetromino rotated = currentlyFallingTetromino.rotated(clockwise);

    // if the current rotated position is already valid, this method will do nothing
    Optional<Tetromino> finalRotatedPos = wallKick(rotated);

    if (finalRotatedPos.isEmpty()) {
      return false;
    }

    currentlyFallingTetromino = finalRotatedPos.get();
    return true;
  }

  @Override
  public void dropTetromino() {
    currentlyFallingTetromino = getDroppedPosition();
    addTetrominoToBoardAndClearRows();
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

  @Override
  public int getHighScore() {
    return highScore;
  }

  private void newTetromino() {
    currentlyFallingTetromino = nextTetromino.shiftedToTopCenterOf(board);
    nextTetromino = tetrominoFactory.getNext();

    if (!isValidPosition(currentlyFallingTetromino)) {
      gameState = GameState.GAME_OVER;
      if (score > highScore) {
        saveHighScore();
      }
    }
  }

  @Override
  public void toggleSound() {
    soundOn = !soundOn;
  }

  // This implementation is purely meant to be a quick fix,
  // it is nothing fancy and does not follow any official guidelines
  // for each direction right, left and down, checks if the rotated
  // piece could fit by a shift once of twice in the direction,
  // if it does, this position is returned.
  private Optional<Tetromino> wallKick(Tetromino rotated) {
    if (isValidPosition(rotated)) {
      return Optional.of(rotated);
    }

    List<CellPosition> shiftPositions = List.of(
        new CellPosition(0, 1), new CellPosition(0, 2),
        new CellPosition(0, -1), new CellPosition(0, -2),
        new CellPosition(1, 0), new CellPosition(2, 0)
    );

    for (CellPosition cp : shiftPositions) {
      Tetromino kicked = rotated.shiftedBy(cp.row(), cp.col());
      if (isValidPosition(kicked)) {
        return Optional.of(kicked);
      }
    }

    return Optional.empty();
  }

  @Override
  public boolean getMusicState() {
    return soundOn;
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

  private int readHighScore() {
    try {
      BufferedReader reader = new BufferedReader(
          new FileReader(HIGH_SCORE_FILE)
      );

      int highScore = Integer.parseInt(reader.readLine());

      reader.close();
      return highScore;
    } catch (IOException e) {
      System.err.println("Could not read high-score from file");
      return 0;
    }
  }


  @Override
  public void saveHighScore() {
    if (score <= highScore) {
      return;
    }

    try {
      File file = new File(HIGH_SCORE_FILE);
      if (file.exists()) {
        if (!file.delete()) {
          System.err.println("Could not replace high-score file");
          return;
        }
      }

      if (!file.createNewFile()) {
        System.err.println("Could not create new high-score file");
        return;
      }

      PrintStream writer = new PrintStream(file);
      writer.println(score);

      writer.close();
    } catch (IOException | NullPointerException e ) {
      System.err.println("Could not write high-score to file");
    }
  }
}
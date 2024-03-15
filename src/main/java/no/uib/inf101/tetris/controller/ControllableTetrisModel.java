package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.model.GameState;

public interface ControllableTetrisModel {

  /**
   * Resets game and returns back to menu screen
   */
  void resetGame();

  /**
   * Move the currently falling piece by specified amount
   *
   * @param deltaRow distance to move vertically
   * @param deltaCol distance to move horizontally
   * @return If the move was successful returns true, else returns false
   */
  boolean moveTetromino(int deltaRow, int deltaCol);

  /**
   * Rotate the currently falling piece 90 degrees
   *
   * @param clockwise Should the rotation be clockwise? If false the piece will rotate counter-clockwise
   * @return If the move was successful returns true, else returns false
   */
  boolean rotateTetromino(boolean clockwise);


  /**
   * Drops currently moving piece to bottom
   * <p/>
   * After dropping, the piece will be added to the board
   * and any rows that are filled will be cleared.
   */
  void dropTetromino();

  /**
   * Current state of the game, for instance ACTIVE_GAME or GAME_OVER
   * @return One of the states specified by the GameState enum
   */
  GameState getGameState();

  /**
   * Set current state of game, for instance PAUSED or GAME_OVER
   * @param gameState must be one of options specified by enum GameState
   */
  void setGameState(GameState gameState);

  /**
   * Time between every forced move downwards
   * <p/>
   * Is specified based on the current difficulty (level) of the game
   * @return the time in milliseconds
   */
  int getDeltaTime();

  /**
   * Move the currently moving piece down one row
   * <p/>
   * If the piece cannot move, it is instead added to
   * the board and any filled rows will be cleared.
   */
  void clockTick();

  /**
   * Turn sound on or off depending on the current state
   */
  void toggleSound();

  /**
   * Save score to file if it is higher than current high-score
   */
  void saveHighScore();
}

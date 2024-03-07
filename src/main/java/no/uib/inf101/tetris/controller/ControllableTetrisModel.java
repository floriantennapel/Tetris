package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.model.GameState;

public interface ControllableTetrisModel {

  /** Resets game to inital state, */
  void resetGame();

  /** move current piece by given distance
   * @return if the move was successful */
  boolean moveTetromino(int deltaRow, int deltaCol);

  /** rotates tetromino counter-clockwise
   * @param clockwise if the rotation should be made clockwise or counter-clockwise
   * @return if the rotation was successful */
  boolean rotateTetromino(boolean clockwise);

  /** drops tetromino down, adds piece to model and gets new falling tetromino */
  void dropTetromino();

  /** current state of game */
  GameState getGameState();

  /** Set current state of game, for instance paused or game-over
   *
   * @param gameState must be one of options specified by enum GameState
   */
  void setGameState(GameState gameState);

  /** @return milliseconds between every call to clockTick */
  int getDeltaTime();

  /** If gameState is active, updates tetris model by dropping piece and repainting view */
  void clockTick();
}

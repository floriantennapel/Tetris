package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.model.GameState;

public interface ControllableTetrisModel {

  /** move current piece by given distance
   * @return if the move was successful */
  boolean moveTetromino(int deltaRow, int deltaCol);

  /** rotates tetromino counter-clockwise
   * @return if the rotation was successful */
  boolean rotateTetromino();

  /** drops tetromino down, adds piece to model and gets new falling tetromino */
  void dropTetromino();

  /** current state of game */
  GameState getGameState();

  /** @return milliseconds between every call to clockTick */
  int getDeltaTime();

  /** If gameState is active, updates tetris model by dropping piece and repainting view */
  void clockTick();
}

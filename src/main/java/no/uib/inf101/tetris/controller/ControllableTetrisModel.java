package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.model.GameState;

public interface ControllableTetrisModel {

  /** move current piece by given distance
   * @return if the move was successful */
  //TODO use gridPosition deltaPosition instead
  boolean moveTetromino(int deltaRow, int deltaCol);

  /** rotates tetromino counter-clockwise
   * @return if the rotation was successful */
  boolean rotateTetromino();

  /** drops tetromino down as far as it can go */
  void dropTetromino();

  /** current state of game */
  GameState getGameState();
}

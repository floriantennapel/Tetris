package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.model.GameState;
import no.uib.inf101.tetris.view.TetrisView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TetrisController implements KeyListener {
  private final ControllableTetrisModel model;
  private final TetrisView view;
  private final Timer timer;

  public TetrisController(ControllableTetrisModel model, TetrisView view) {
    this.model = model;
    this.view = view;

    this.timer = new Timer(model.getDeltaTime(), this::clockTick);

    view.addKeyListener(this);
    view.setFocusable(true);
    timer.start();
  }

  private void clockTick(ActionEvent e) {
    if (model.getGameState() != GameState.ACTIVE_GAME) {
      return;
    }

    model.clockTick();
    setTimerDelay();
    view.repaint();
  }

  private void setTimerDelay() {
    timer.setDelay(model.getDeltaTime());
  }

  @Override
  public void keyPressed(KeyEvent keyEvent) {
    if (model.getGameState() != GameState.ACTIVE_GAME) {
      return;
    }

    switch(keyEvent.getKeyCode()) {
      case KeyEvent.VK_LEFT, KeyEvent.VK_A  -> model.moveTetromino(0, -1);
      case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> model.moveTetromino(0, 1);
      case KeyEvent.VK_DOWN, KeyEvent.VK_S  -> model.moveTetromino(1, 0);
      case KeyEvent.VK_UP, KeyEvent.VK_W    -> model.rotateTetromino();
      case KeyEvent.VK_SPACE -> model.dropTetromino();
    }

    view.repaint();
  }



  // unused methods

  @Override
  public void keyTyped(KeyEvent keyEvent) { }

  @Override
  public void keyReleased(KeyEvent keyEvent) { }
}

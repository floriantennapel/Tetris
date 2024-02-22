package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.view.TetrisView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TetrisController implements KeyListener {
  private final ControllableTetrisModel model;
  private final TetrisView view;

  public TetrisController(ControllableTetrisModel model, TetrisView view) {
    this.model = model;
    this.view = view;

    view.addKeyListener(this);
    view.setFocusable(true);
  }

  @Override
  public void keyPressed(KeyEvent keyEvent) {
    switch(keyEvent.getKeyCode()) {
      case KeyEvent.VK_LEFT  -> model.moveTetromino(0, -1);
      case KeyEvent.VK_RIGHT -> model.moveTetromino(0, 1);
      case KeyEvent.VK_DOWN  -> model.moveTetromino(1, 0);
      case KeyEvent.VK_UP    -> model.rotateTetromino();
    }

    view.repaint();
  }



  // unused methods

  @Override
  public void keyTyped(KeyEvent keyEvent) { }

  @Override
  public void keyReleased(KeyEvent keyEvent) { }
}

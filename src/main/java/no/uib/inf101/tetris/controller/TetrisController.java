package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.midi.TetrisSong;
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
  private final TetrisSong music;

  public TetrisController(ControllableTetrisModel model, TetrisView view) {
    this.model = model;
    this.view = view;

    this.timer = new Timer(model.getDeltaTime(), this::clockTick);
    this.music = new TetrisSong();

    view.addKeyListener(this);
    view.setFocusable(true);
    timer.start();
    music.run();
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
    if (keyEvent.getKeyChar() == 'm') {
      model.toggleSound();
      music.togglePauseMidiSounds();
    }

    switch (model.getGameState()) {
      case START_MENU -> {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
          model.setGameState(GameState.ACTIVE_GAME);
        }
      }
      case GAME_OVER -> {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
          model.resetGame();
        }
      }
      case PAUSED -> {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
          togglePause();
        }
      }
      case ACTIVE_GAME -> {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
          togglePause();
        }
        if (model.getGameState() == GameState.ACTIVE_GAME) {
          checkMoveKeys(keyEvent);
        }
      }
    }
    view.repaint();
  }

  private void checkMoveKeys(KeyEvent keyEvent) {
    switch(keyEvent.getKeyCode()) {
      case KeyEvent.VK_LEFT, KeyEvent.VK_A  -> model.moveTetromino(0, -1);
      case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> model.moveTetromino(0, 1);
      case KeyEvent.VK_DOWN, KeyEvent.VK_S  -> model.moveTetromino(1, 0);
      case KeyEvent.VK_UP, KeyEvent.VK_W    -> model.rotateTetromino(true);
      case KeyEvent.VK_SHIFT, KeyEvent.VK_E -> model.rotateTetromino(false);
      case KeyEvent.VK_SPACE -> model.dropTetromino();
    }
  }

  private void togglePause() {
    GameState currentState = model.getGameState();
    if (currentState.equals(GameState.GAME_OVER)) {
      return;
    }

    model.setGameState(
        currentState.equals(GameState.ACTIVE_GAME)
            ? GameState.PAUSED
            : GameState.ACTIVE_GAME
    );
  }



  // unused methods

  @Override
  public void keyTyped(KeyEvent keyEvent) { }

  @Override
  public void keyReleased(KeyEvent keyEvent) { }
}

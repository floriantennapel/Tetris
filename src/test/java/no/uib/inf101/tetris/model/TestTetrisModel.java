package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.tetris.model.tetromino.PatternedTetrominoFactory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestTetrisModel {
  @Test
  public void testMoveTetromino() {
    TetrisBoard board = new TetrisBoard(5, 5);
    TetrisModel model = new TetrisModel(board, new PatternedTetrominoFactory("S"));

    List<GridCell<Character>> objs = new ArrayList<>();
    for (GridCell<Character> gc : model.getMovingTetrominoTiles()) {
      objs.add(gc);
    }

    // check start position
    assertEquals(4, objs.size());
    assertTrue(objs.contains(new GridCell<>(new CellPosition(1, 1), 'S')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(1, 2), 'S')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 2), 'S')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 3), 'S')));

    // allowed movement returns true
    assertTrue(model.moveTetromino(1, 1));

    // did actually move piece
    objs = new ArrayList<>();
    for (GridCell<Character> gc : model.getMovingTetrominoTiles()) {
      objs.add(gc);
    }

    assertEquals(4, objs.size());
    assertTrue(objs.contains(new GridCell<>(new CellPosition(2, 2), 'S')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(2, 3), 'S')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(1, 3), 'S')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(1, 4), 'S')));


    // invalid movement returns false and does not move piece
    assertFalse(model.moveTetromino(300, 0));

    objs = new ArrayList<>();
    for (GridCell<Character> gc : model.getMovingTetrominoTiles()) {
      objs.add(gc);
    }

    assertEquals(4, objs.size());
    assertTrue(objs.contains(new GridCell<>(new CellPosition(2, 2), 'S')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(2, 3), 'S')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(1, 3), 'S')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(1, 4), 'S')));
  }

  @Test
  public void testMoveCollision() {
    TetrisBoard board = new TetrisBoard(5, 5);
    board.set(new CellPosition(1, 0), 'L');
    TetrisModel model = new TetrisModel(board, new PatternedTetrominoFactory("S"));

    assertFalse(model.moveTetromino(0, -1));
  }


  //TODO write this test
  @Test
  public void testDrop() {

  }

  //TODO write test for clocktick
}

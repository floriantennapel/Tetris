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

  @Test
  public void testDrop() {
    TetrisBoard board = new TetrisBoard(7, 5);
    TetrisModel model = new TetrisModel(board, new PatternedTetrominoFactory("TS"));
    model.dropTetromino();

    assertEquals('T', board.get(new CellPosition(5, 1)));
    assertEquals('T', board.get(new CellPosition(5, 2)));
    assertEquals('T', board.get(new CellPosition(5, 3)));
    assertEquals('T', board.get(new CellPosition(6, 2)));

    // test that pieces stack
    model.dropTetromino();

    assertEquals('S', board.get(new CellPosition(3, 2)));
    assertEquals('S', board.get(new CellPosition(3, 3)));
    assertEquals('S', board.get(new CellPosition(4, 1)));
    assertEquals('S', board.get(new CellPosition(4, 2)));
  }

  @Test
  public void testClockTick() {
    TetrisBoard board = new TetrisBoard(3, 5);
    TetrisModel model = new TetrisModel(board, new PatternedTetrominoFactory("T"));

    model.clockTick();

    // checking that piece moved
    List<GridCell<Character>> objs = new ArrayList<>();
    for (GridCell<Character> gc : model.getMovingTetrominoTiles()) {
      objs.add(gc);
    }

    assertEquals(4, objs.size());
    assertTrue(objs.contains(new GridCell<>(new CellPosition(1, 1), 'T')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(1, 2), 'T')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(1, 3), 'T')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(2, 2), 'T')));

    model.clockTick();
    // checking that next clocktick adds piece to board
    String expected = String.join("\n", new String[] {
        "-----",
        "-TTT-",
        "--T--"
    });
    assertEquals(expected, board.prettyString());
  }
}

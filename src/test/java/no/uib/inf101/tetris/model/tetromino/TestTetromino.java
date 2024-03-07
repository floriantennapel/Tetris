package no.uib.inf101.tetris.model.tetromino;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.tetris.model.TetrisBoard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestTetromino {
  @Test
  public void testHashCodeAndEquals() {
    Tetromino t1 = Tetromino.newTetromino('T');
    Tetromino t2 = Tetromino.newTetromino('T');
    Tetromino t3 = Tetromino.newTetromino('T').shiftedBy(1, 0);
    Tetromino s1 = Tetromino.newTetromino('S');
    Tetromino s2 = Tetromino.newTetromino('S').shiftedBy(0, 0);

    assertEquals(t1, t2);
    assertEquals(s1, s2);
    assertEquals(t1.hashCode(), t2.hashCode());
    assertEquals(s1.hashCode(), s2.hashCode());
    assertNotEquals(t1, t3);
    assertNotEquals(t1, s1);
  }

  @Test
  public void tetrominoIterationOfT() {
    // Create a standard 'T' tetromino placed at (10, 100) to test
    Tetromino tetro = Tetromino.newTetromino('T');
    tetro = tetro.shiftedBy(10, 100);

    // Collect which objects are iterated through
    List<GridCell<Character>> objs = new ArrayList<>();
    for (GridCell<Character> gc : tetro) {
      objs.add(gc);
    }

    // Check that we got the expected GridCell objects
    assertEquals(4, objs.size());
    assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 100), 'T')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 101), 'T')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 102), 'T')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(12, 101), 'T')));
  }

  @Test
  public void tetrominoIterationOfS() {
    // Create a standard 'S' tetromino placed at (10, 100) to test
    Tetromino tetro = Tetromino.newTetromino('S');
    tetro = tetro.shiftedBy(10, 100);

    // Collect which objects are iterated through
    List<GridCell<Character>> objs = new ArrayList<>();
    for (GridCell<Character> gc : tetro) {
      objs.add(gc);
    }

    // Check that we got the expected GridCell objects
    assertEquals(4, objs.size());
    assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 101), 'S')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(11, 102), 'S')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(12, 100), 'S')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(12, 101), 'S')));
  }

  @Test
  public void testCommutative() {
    Tetromino tetro1 = Tetromino.newTetromino('Z');
    Tetromino copy1 = tetro1.shiftedBy(0, 0);

    Tetromino tetro2 = Tetromino.newTetromino('Z');
    Tetromino copy2 = tetro1.shiftedBy(0, 0);

    for (int i = 0; i < 2; i++) {
      tetro1 = tetro1.shiftedBy(2, 6);
      tetro2 = tetro2.shiftedBy(-3, -0);
    }

    copy1 = copy1.shiftedBy(4, 12);
    copy2 = copy2.shiftedBy(-6, -0);

    assertEquals(tetro1, copy1);
    assertEquals(tetro2, copy2);
  }

  @Test
  public void testShiftedToTopCenterOf1() {
    TetrisBoard board = new TetrisBoard(5, 5);

    Tetromino tetro = Tetromino.newTetromino('T');
    tetro = tetro.shiftedToTopCenterOf(board);

    // Collect which objects are iterated through
    List<GridCell<Character>> objs = new ArrayList<>();
    for (GridCell<Character> gc : tetro) {
      objs.add(gc);
    }

    // Check that we got the expected GridCell objects
    assertEquals(4, objs.size());
    assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 1), 'T')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 2), 'T')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 3), 'T')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(1, 2), 'T')));
  }

  @Test
  public void testShiftedToTopCenterOf2() {
    TetrisBoard board = new TetrisBoard(5, 8);

    Tetromino tetro = Tetromino.newTetromino('I');
    tetro = tetro.shiftedToTopCenterOf(board);

    // Collect which objects are iterated through
    List<GridCell<Character>> objs = new ArrayList<>();
    for (GridCell<Character> gc : tetro) {
      objs.add(gc);
    }

    // Check that we got the expected GridCell objects
    assertEquals(4, objs.size());
    assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 2), 'I')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 3), 'I')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 4), 'I')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 5), 'I')));
  }

  @Test
  public void testRotationT() {
    Tetromino tetro = Tetromino.newTetromino('T');
    tetro = tetro.rotated(false);

    // Collect which objects are iterated through
    List<GridCell<Character>> objs = new ArrayList<>();
    for (GridCell<Character> gc : tetro) {
      objs.add(gc);
    }

    // Check that we got the expected GridCell objects
    assertEquals(4, objs.size());
    assertTrue(objs.contains(new GridCell<>(new CellPosition(0, 1), 'T')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(1, 1), 'T')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(1, 2), 'T')));
    assertTrue(objs.contains(new GridCell<>(new CellPosition(2, 1), 'T')));
  }

  @Test
  public void test4RotationsEndUpSame() {
    PatternedTetrominoFactory tetrominos = new PatternedTetrominoFactory(Tetromino.VALID_SHAPES);

    for (int i = 0; i < Tetromino.VALID_SHAPES.length(); i++) {
      Tetromino tetro = tetrominos.getNext();
      Tetromino copy = tetro.shiftedBy(0, 0); // logically equal to clone()

      assertEquals(tetro, copy);

      for (int j = 0; j < 4; j++) {
        copy = copy.rotated(true);
      }

      assertEquals(tetro, copy);
    }
  }

  //TODO write test for rotation that checks whether piece can be rotated to invalid position
}

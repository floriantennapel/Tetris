package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.CellPosition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTetrisBoard {
  @Test
  public void prettyStringTest() {
    TetrisBoard board = new TetrisBoard(3, 4);
    board.set(new CellPosition(0, 0), 'g');
    board.set(new CellPosition(0, 3), 'y');
    board.set(new CellPosition(2, 0), 'r');
    board.set(new CellPosition(2, 3), 'b');
    String expected = String.join("\n", new String[] {
        "g--y",
        "----",
        "r--b"
    });
    assertEquals(expected, board.prettyString());
  }

  @Test
  public void testRemoveFullRows() {
    // Tester at fulle rader fjernes i brettet:
    // -T
    // TT
    // LT
    // L-
    // LL

    TetrisBoard board = new TetrisBoard(5, 2);
    board.set(new CellPosition(0, 1), 'T');
    board.set(new CellPosition(1, 0), 'T');
    board.set(new CellPosition(1, 1), 'T');
    board.set(new CellPosition(2, 1), 'T');
    board.set(new CellPosition(4, 0), 'L');
    board.set(new CellPosition(4, 1), 'L');
    board.set(new CellPosition(3, 0), 'L');
    board.set(new CellPosition(2, 0), 'L');

    assertEquals(3, board.clearRows());

    String expected = String.join("\n", new String[] {
        "--",
        "--",
        "--",
        "-T",
        "L-"
    });
    assertEquals(expected, board.prettyString());
  }
}

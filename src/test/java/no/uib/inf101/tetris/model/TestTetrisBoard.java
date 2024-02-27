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
    // Tester at fulle rader fjernes i brettet
    TetrisBoard board = getTetrisBoardWithContents(new String[] {
        "-T",
        "TT",
        "LT",
        "L-",
        "LL"
    });

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

  @Test
  public void testNotRemoveBottomRow() {
    TetrisBoard board = getTetrisBoardWithContents(new String[] {
        "OO",
        "OO",
        "JJ",
        "J-",
        "J-"
    });

    assertEquals(3, board.clearRows());

    String expected = String.join("\n", new String[] {
        "--",
        "--",
        "--",
        "J-",
        "J-"
    });
    assertEquals(expected, board.prettyString());
  }

  @Test
  public void testOnlyRemovesTopRow() {
    TetrisBoard board = getTetrisBoardWithContents(new String[] {
        "JJI",
        "J-I",
        "J-I",
        "--I",
    });

    assertEquals(1, board.clearRows());

    String expected = String.join("\n", new String[] {
        "---",
        "J-I",
        "J-I",
        "--I",
    });
    assertEquals(expected, board.prettyString());
  }

  @Test
  public void testClearsWide() {
    TetrisBoard board = getTetrisBoardWithContents(new String[] {
        "--------J",
        "IIIIIIIIJ",
        "--ILJ--JJ",
        "--ILJJJOO",
        "--ILL--OO"
    });

    assertEquals(1, board.clearRows());

    String expected = String.join("\n", new String[] {
        "---------",
        "--------J",
        "--ILJ--JJ",
        "--ILJJJOO",
        "--ILL--OO"
    });
    assertEquals(expected, board.prettyString());
  }

  // method is package private, so I can reuse in other tests

  /** given a string array representing the model, returns a new TetrisBoard equivalent to the representation
   *
   * @param contents non-empty array, where every string is the representation of a row, all rows must have equal length
   * @return a new TetrisBoard
   */
  static TetrisBoard getTetrisBoardWithContents(String[] contents) {
    int rows = contents.length;
    int cols = contents[0].length();

    TetrisBoard board = new TetrisBoard(rows, cols);

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        char current = contents[i].charAt(j);

        if (current != '-') {
          board.set(new CellPosition(i, j), current);
        }
      }
    }

    return board;
  }
}

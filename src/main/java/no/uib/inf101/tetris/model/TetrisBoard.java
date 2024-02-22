package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;

public class TetrisBoard extends Grid<Character> {
  public TetrisBoard(int rows, int cols) {
    super(rows, cols, '-');
  }

  public String prettyString() {
    StringBuilder s = new StringBuilder();
    int charCount = 1;

    for (GridCell<Character> gc : this) {
      s.append(gc.value());

      if (charCount % cols() == 0) {
        s.append('\n');
      }

      ++charCount;
    }

    //remove last newline
    s.deleteCharAt(s.length() - 1);

    return s.toString();
  }
}

package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.tetris.model.tetromino.Tetromino;


public class TetrisBoard extends Grid<Character> {
  public TetrisBoard(int rows, int cols) {
    super(rows, cols, '-');
  }

  /**
   * Clear all rows on board that are filled with pieces
   * @return How many rows were cleared
   */
  public int clearRows() {
    int rows = getRows();

    int clearedCount = 0;
    int consecutive = 0;
    int[] movePlan = new int[rows];

    for (int row = rows - 1; row >= 0; row--) {
      if (rowHasOnly(Tetromino.VALID_SHAPES, row)) {
        ++clearedCount;
        ++consecutive;
      } else {
        movePlan[row] = consecutive;
        consecutive = 0;
      }
    }

    // if there was no non-filled row before end of loop
    if (consecutive != 0) {
      clearAllAbove(consecutive - 1);
    }

    // clearing rows
    int rowsRemovedSoFar = 0;
    for (int row = rows - 1; row >= 0; row--) {
      int toMove = movePlan[row];
      if (toMove != 0) {
        moveAboveRows(row + rowsRemovedSoFar, toMove);
      }

      rowsRemovedSoFar += toMove;
    }

    return clearedCount;
  }

  private void clearAllAbove(int lowestRowToClear) {
    for (int row = 0; row <= lowestRowToClear; row++) {
      for (int col = 0; col < getCols(); col++) {
        CellPosition pos = new CellPosition(row, col);

        set(pos, '-');
      }
    }
  }

  private void moveAboveRows(int lowestRowToMove, int steps) throws IndexOutOfBoundsException {
    for (int row = lowestRowToMove; row >= 0; row--) {
      moveRow(row, row + steps);
    }
  }

  private void moveRow(int from, int to) throws IndexOutOfBoundsException {
    for (int col = 0; col < getCols(); col++) {
      CellPosition fromPos = new CellPosition(from, col);
      CellPosition toPos   = new CellPosition(  to, col);

      set(toPos, get(fromPos));
      // clearing row we came from
      set(fromPos, '-');
    }
  }

  // does the row only contain the characters from the input string?
  // For instance, characters = "-" checks if row is empty
  // characters = Tetromino.VALID_SHAPES checks if row is filled
  private boolean rowHasOnly(String characters, int row) throws IndexOutOfBoundsException {
    for (int col = 0; col < getCols(); col++) {
      CellPosition pos = new CellPosition(row, col);

      // casting to String for contains()
      String cell = String.valueOf(get(pos));
      if (!characters.contains(cell)) {
        return false;
      }
    }

    return true;
  }

  /** Used for testing */
  String prettyString() {
    StringBuilder s = new StringBuilder();
    int charCount = 1;

    for (GridCell<Character> gc : this) {
      s.append(gc.value());

      if (charCount % getCols() == 0) {
        s.append('\n');
      }

      ++charCount;
    }

    //remove last newline
    s.deleteCharAt(s.length() - 1);

    return s.toString();
  }
}

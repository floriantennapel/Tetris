package no.uib.inf101.tetris.model.tetromino;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;

import java.util.*;

public class Tetromino implements Iterable<GridCell<Character>> {
  //TODO read shapes from text file
  private static final Map<Character, boolean[][]> shapes = new HashMap<>(Map.of(
      'L', new boolean[][] {
          {false, false, false},
          { true,  true,  true},
          { true, false, false}
      },
      'J', new boolean[][] {
          {false, false, false},
          { true,  true,  true},
          {false, false,  true}
      },
      'S', new boolean[][] {
          {false, false, false},
          {false,  true,  true},
          { true,  true, false}
      },
      'Z', new boolean[][] {
          {false, false, false},
          { true,  true, false},
          {false,  true, true}
      },
      'T', new boolean[][] {
          {false, false, false},
          { true,  true,  true},
          {false,  true, false}
      },
      'I', new boolean[][] {
          {false, false, false, false},
          { true,  true,  true,  true},
          {false, false, false, false},
          {false, false, false, false}
      },
      'O', new boolean[][] {
          {false, false, false, false},
          {false,  true,  true, false},
          {false,  true,  true, false},
          {false, false, false, false}
      }
  ));

  private final char typeSymbol;
  private final boolean[][] shape;
  private final CellPosition position;

  private Tetromino(char typeSymbol, boolean[][] shape, CellPosition position) {
    this.typeSymbol = typeSymbol;
    this.shape = shape;
    this.position = position;
  }

  protected static Tetromino newTetromino(char typeSymbol) throws IllegalArgumentException {
    if (!shapes.containsKey(typeSymbol)) {
      throw new IllegalArgumentException();
    }

    return new Tetromino(typeSymbol, shapes.get(typeSymbol), new CellPosition(0, 0));
  }

  //TODO change arguments to be CellPosition deltaPos
  /** copy of current tetromino shifted by deltaRow and deltaCol */
  // I assume that all arguments should be allowed, since we have no way of knowing if new position is valid
  public Tetromino shiftedBy(int deltaRow, int deltaCol) {
    CellPosition deltaPos = new CellPosition(deltaRow, deltaCol);

    return new Tetromino(typeSymbol, shape, position.add(deltaPos));
  }

  //TODO write test for this function
  /** copy of current tetromino shifted to starting position */
  public Tetromino shiftedToTopCenterOf(GridDimension gridDimension) {
    int cols = gridDimension.cols();

    int centerCol = cols / 2;

    // we want center column to either be directly in center
    // or if there are two center columns centerCol should be center-left
    if (cols % 2 != 0) {
      centerCol -= 1;
    }

    // at start position 0,0 center of tetromino is already 1
    int tetrominoCenter = 1;
    int deltaCol = centerCol - tetrominoCenter;

    return shiftedBy(-1, deltaCol);
  }

  @Override
  public Iterator<GridCell<Character>> iterator() {
    List<GridCell<Character>> usedCells = new ArrayList<>();

    for (int i = 0; i < shape.length; i++) {
      for (int j = 0; j < shape[0].length; j++) {

        if (shape[i][j]) {
          int actualRow = position.row() + i;
          int actualCol = position.col() + j;

          usedCells.add(new GridCell<>(
              new CellPosition(actualRow, actualCol),
              typeSymbol
          ));
        }
      }
    }

    return usedCells.iterator();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (this == obj) {
      return true;
    }
    // using java 17 pattern variable
    https://docs.oracle.com/en/java/javase/17/language/pattern-matching-instanceof-operator.html#GUID-843060B5-240C-4F47-A7B0-95C42E5B08A7
    if (!(obj instanceof Tetromino other)) {
      return false;
    }
    return typeSymbol == other.typeSymbol
        && Arrays.deepEquals(shape, other.shape)
        && position.equals(other.position);
  }

  @Override
  public int hashCode() {
    return Objects.hash(typeSymbol, Arrays.deepHashCode(shape), position);
  }
}

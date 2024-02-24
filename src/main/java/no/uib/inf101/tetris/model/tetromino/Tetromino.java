package no.uib.inf101.tetris.model.tetromino;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Tetromino implements Iterable<GridCell<Character>> {
  public static final String VALID_SHAPES = "IJLSZTO";

  private static final String SHAPE_FILE = "tetrominos.txt";
  private static final Map<Character, boolean[][]> shapes = readShapesFromFile();

  private final char typeSymbol;
  private final boolean[][] shape;
  private final CellPosition position;

  private Tetromino(char typeSymbol, boolean[][] shape, CellPosition position) {
    this.typeSymbol = typeSymbol;
    this.shape = shape;
    this.position = position;
  }

  static Tetromino newTetromino(char typeSymbol) throws IllegalArgumentException {
    if (!shapes.containsKey(typeSymbol)) {
      throw new IllegalArgumentException();
    }

    return new Tetromino(typeSymbol, shapes.get(typeSymbol), new CellPosition(0, 0));
  }

  /**Does not check if new position is within bounds of board
   * @return copy of current tetromino shifted by deltaRow and deltaCol */
  public Tetromino shiftedBy(int deltaRow, int deltaCol) {
    CellPosition deltaPos = new CellPosition(deltaRow, deltaCol);

    return new Tetromino(typeSymbol, shape, position.add(deltaPos));
  }

  //TODO write test for this function
  /** @return copy of current tetromino shifted to starting position
   * @throws IllegalArgumentException in case of null argument */
  public Tetromino shiftedToTopCenterOf(GridDimension gridDimension) throws IllegalArgumentException {
    if (gridDimension == null) {
      throw new IllegalArgumentException("gridDimension cannot be null");
    }

    int cols = gridDimension.getCols();

    int centerCol = cols / 2;

    // we want center column to either be directly in center
    // or if there are two center columns centerCol should be center-left
    if (cols % 2 == 0) {
      centerCol -= 1;
    }

    // at start position 0,0 center of tetromino is already 1
    int tetrominoCenter = 1;
    int deltaCol = centerCol - tetrominoCenter;

    return shiftedBy(-1, deltaCol);
  }

  /** @return A copy of this tetromino rotated 90 degrees counter-clockwise */
  public Tetromino rotated() {
    // 3x3 or 4x4
    int shapeDimension = shape.length;
    boolean[][] rotatedShape = new boolean[shapeDimension][shapeDimension];

    for (int i = 0; i < shapeDimension; i++) {
      for (int j = 0; j < shapeDimension; j++) {
        rotatedShape[i][j] = shape[j][shapeDimension - i - 1];
      }
    }

    return new Tetromino(typeSymbol, rotatedShape, position);
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

  private static Map<Character, boolean[][]> readShapesFromFile() throws RuntimeException {
    Map<Character, boolean[][]> shapes = new HashMap<>();
    try {
      BufferedReader reader = new BufferedReader(new FileReader(SHAPE_FILE));

      while (true) {
        // parse shape information
        String info = reader.readLine();
        char symbol = info.charAt(0);
        int dimension = Character.getNumericValue(info.charAt(2));

        // parse shape
        boolean[][] shape = new boolean[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
          String row = reader.readLine();

          for (int j = 0; j < dimension; j++) {
            shape[i][j] = row.charAt(j) == '*';
          }
        }

        shapes.put(symbol, shape);

        // parse blank line or end loop
        if (reader.readLine() == null) {
          break;
        }
      }

      reader.close();

      return shapes;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}

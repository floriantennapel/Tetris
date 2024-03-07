package no.uib.inf101.tetris.model.tetromino;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;

import java.io.*;
import java.util.*;

public class Tetromino implements Iterable<GridCell<Character>> {
  public static final String VALID_SHAPES = "IJLSZTO";

  private static final String SHAPE_FILE = "tetrominos.txt";
  private static final Map<Character, boolean[][]> SHAPES = readShapesFromFile();

  private final char typeSymbol;
  private final boolean[][] shape;
  private final CellPosition position;

  private Tetromino(char typeSymbol, boolean[][] shape, CellPosition position) {
    this.typeSymbol = typeSymbol;
    this.shape = shape;
    this.position = position;
  }

  /** constructor for Tetromino, actual constructor is private */
  static Tetromino newTetromino(char typeSymbol) throws IllegalArgumentException {
    if (!SHAPES.containsKey(typeSymbol)) {
      throw new IllegalArgumentException();
    }

    return new Tetromino(typeSymbol, SHAPES.get(typeSymbol), new CellPosition(0, 0));
  }

  /**Does not check if new position is within bounds of board
   * @return copy of current tetromino shifted by deltaRow and deltaCol */
  public Tetromino shiftedBy(int deltaRow, int deltaCol) {
    CellPosition deltaPos = new CellPosition(deltaRow, deltaCol);

    return new Tetromino(typeSymbol, shape, position.add(deltaPos));
  }

  /** @return copy of current tetromino shifted to starting position (centered vertically at top of screen)
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

  /** @param clockwise should the rotation be clockwise or counter-clockwise
   *  @return A copy of this tetromino rotated 90 degrees */
  public Tetromino rotated(boolean clockwise) {
    // 3x3 or 4x4
    int shapeDimension = shape.length;
    boolean[][] rotatedShape = new boolean[shapeDimension][shapeDimension];

    for (int i = 0; i < shapeDimension; i++) {
      for (int j = 0; j < shapeDimension; j++) {
        if (clockwise) {
          rotatedShape[i][j] = shape[shapeDimension - j - 1][i];
        } else {
          rotatedShape[i][j] = shape[j][shapeDimension - i - 1];
        }
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
      InputStream inputStream = Tetromino.class.getResourceAsStream(SHAPE_FILE);
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

      do {
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
      } while (reader.readLine() != null);

      reader.close();
      inputStream.close();

      return shapes;
    } catch (IOException | NullPointerException e) {
      throw new RuntimeException(e);
    }
  }
}

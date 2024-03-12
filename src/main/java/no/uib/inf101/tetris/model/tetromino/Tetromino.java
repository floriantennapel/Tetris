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

  /**
   * A constructor for the Tetromino class, the actual constructor is private
   * @param typeSymbol must be one of the shapes specified by this.VALID_SHAPES
   * @return a new tetromino at position 0, 0
   * @throws IllegalArgumentException if typeSymbol is not valid
   */
  static Tetromino newTetromino(char typeSymbol) throws IllegalArgumentException {
    if (!SHAPES.containsKey(typeSymbol)) {
      throw new IllegalArgumentException("typeSymbol must be one of Tetromino.VALID_SHAPES");
    }

    return new Tetromino(typeSymbol, SHAPES.get(typeSymbol), new CellPosition(0, 0));
  }

  /**
   * A copy of this shifted by a specified amount
   * <p/>
   * No checks are made to ensure that the piece is within bounds.
   *
   * @param deltaRow distance to move vertically
   * @param deltaCol distance to move horizontally
   */
  public Tetromino shiftedBy(int deltaRow, int deltaCol) {
    CellPosition deltaPos = new CellPosition(deltaRow, deltaCol);

    return new Tetromino(typeSymbol, shape, position.add(deltaPos));
  }

  /**
   * A copy of this shifted to starting position at the top-center of a specified grid
   * <p/>
   * If the parities of the piece and grid do not match,
   * the piece can be arbitrarily positioned left or right of center
   *
   * @param gridDimension Height and width of grid
   * @throws IllegalArgumentException if gridDimension is null
   */
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

  /**
   * A copy of this rotated by 90 degrees
   * @param clockwise Should the rotation be clockwise, if false the rotation is counter-clockwise
   */
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

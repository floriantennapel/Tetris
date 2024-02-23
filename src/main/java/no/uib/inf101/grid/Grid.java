package no.uib.inf101.grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Grid<E> implements IGrid<E> {
  private final List<List<GridCell<E>>> grid;
  private final int rows;
  private final int cols;

  public Grid(int rows, int cols, E defaultValue) {
    this.rows = rows;
    this.cols = cols;

    grid = new ArrayList<>(rows);
    for (int i = 0; i < rows; i++) {
      List<GridCell<E>> row = new ArrayList<>(cols);

      for (int j = 0; j < cols; j++) {
        CellPosition pos = new CellPosition(i, j);
        row.add(new GridCell<>(pos, defaultValue));
      }

      grid.add(row);
    }
  }

  public Grid(int rows, int cols) {
    this(rows, cols, null);
  }

  @Override
  public void set(CellPosition pos, E value) {
    if (!positionIsOnGrid(pos)) {
      System.err.println("method set in class Grid: invalid CellPosition");
      return;
    }

    grid.get(pos.row()).set(pos.col(), new GridCell<>(pos, value));
  }

  @Override
  public E get(CellPosition pos) throws IndexOutOfBoundsException {
    // det står ikke i javaDocen at funksjonen kan kaste indexOutOfBoundsException, så egentlig burde dette være med...
    /*
    if (!positionIsOnGrid(pos)) {
      System.err.println("method get in class Grid: invalid CellPosition");
      return null;
    }*/

    return grid.get(pos.row()).get(pos.col()).value();
  }

  @Override
  public boolean positionIsOnGrid(CellPosition pos) {
    int row = pos.row();
    int col = pos.col();

    return
        row >= 0 && row < rows &&
        col >= 0 && col < cols;
  }

  @Override
  public Iterator<GridCell<E>> iterator() {
    // https://stackoverflow.com/questions/44251460/how-to-convert-2d-list-to-1d-list-with-streams
    return grid.stream().
        flatMap(Collection::stream).
        iterator();
  }

  @Override
  public int getRows() {
    return rows;
  }

  @Override
  public int getCols() {
    return cols;
  }
}

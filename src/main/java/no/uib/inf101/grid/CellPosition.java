package no.uib.inf101.grid;

public record CellPosition(int row, int col) {
  /** Add two position, works like normal vector addition */
  public CellPosition add(CellPosition pos) {
    return new CellPosition(
        row + pos.row(),
        col + pos.col()
    );
  }
}

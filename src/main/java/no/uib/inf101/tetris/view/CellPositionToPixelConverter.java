package no.uib.inf101.tetris.view;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridDimension;

import java.awt.geom.Rectangle2D;

public class CellPositionToPixelConverter {
  private final Rectangle2D box;
  private final GridDimension gd;
  private final double margin;

  public CellPositionToPixelConverter(Rectangle2D box, GridDimension gd, double margin) {
    this.box = box;
    this.gd = gd;
    this.margin = margin;
  }

  /** converts a position on a grid to a rectangle that can be drawn on screen
   *
   * @param cp position in Grid of cell
   * @return a new Rectangle2D that can be drawn by Graphics2D object
   * @throws IllegalArgumentException if CellPosition is out of bounds
   */
  public Rectangle2D getBoundsForCell(CellPosition cp) throws IllegalArgumentException {
    int rows = gd.getRows();
    int cols = gd.getCols();
    int row = cp.row();
    int col = cp.col();

    if ((col < 0) || (col >= cols) || (row < 0) || (row >= rows)) {
      throw new IllegalArgumentException("Cell position out of bounds");
    }

    double cellWidth = (box.getWidth() - margin) / cols - margin;
    double cellHeight = (box.getHeight() - margin) / rows - margin;
    double cellX = box.getX() + margin + col * (margin + cellWidth);
    double cellY = box.getY() + margin + row * (margin + cellHeight);

    return new Rectangle2D.Double(cellX, cellY, cellWidth, cellHeight);
  }
}

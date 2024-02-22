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

  public Rectangle2D getBoundsForCell(CellPosition cp) {
    double cellWidth = (box.getWidth() - margin) / gd.cols() - margin;
    double cellHeight = (box.getHeight() - margin) / gd.rows() - margin;
    double cellX = box.getX() + margin + cp.col() * (margin + cellWidth);
    double cellY = box.getY() + margin + cp.row() * (margin + cellHeight);

    return new Rectangle2D.Double(cellX, cellY, cellWidth, cellHeight);
  }
}

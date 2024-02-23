package no.uib.inf101.tetris.view;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.model.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class TetrisView extends JPanel {
  private static final double OUTER_MARGIN = 5.0;
  private static final double INNER_MARGIN = 2.0;
  private static final int PREFERRED_CELL_SIZE = 50;

  private final ViewableTetrisModel model;
  private final ColorTheme colorTheme;

  public TetrisView(ViewableTetrisModel model) {
    this.model = model;
    this.colorTheme = new DefaultColorTheme();

    GridDimension dimension = model.getDimension();
    int height = (int) (dimension.rows() * (PREFERRED_CELL_SIZE + INNER_MARGIN) + INNER_MARGIN + 2 * OUTER_MARGIN);
    int width = (int) (dimension.cols() * (PREFERRED_CELL_SIZE + INNER_MARGIN) + INNER_MARGIN + 2 * OUTER_MARGIN);

    this.setPreferredSize(new Dimension(width, height));
    this.setBackground(colorTheme.getBackgroundColor());
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    drawGame(g2);
  }

  private void drawGame(Graphics2D g) {
    Rectangle2D box = new Rectangle2D.Double(
        OUTER_MARGIN,
        OUTER_MARGIN,
        this.getWidth() - 2 * OUTER_MARGIN,
        this.getHeight() - 2 * OUTER_MARGIN
    );

    g.setColor(colorTheme.getFrameColor());
    g.fill(box);

    CellPositionToPixelConverter posToPixel = new CellPositionToPixelConverter(
        box, model.getDimension(), INNER_MARGIN
    );

    drawCells(g, model.getTilesOnBoard(), posToPixel, colorTheme);
    drawCells(g, model.getMovingTetrominoTiles(), posToPixel, colorTheme);

    if (model.getGameState() == GameState.GAME_OVER) {
      drawGameOver(g);
    }
  }

  private static void drawCells(
      Graphics2D g,
      Iterable<GridCell<Character>> cells,
      CellPositionToPixelConverter posToPixel,
      ColorTheme colorTheme) {

    for (GridCell<Character> gc : cells) {
      Rectangle2D currentCell = posToPixel.getBoundsForCell(gc.pos());
      g.setColor(colorTheme.getCellColor(gc.value()));
      g.fill(currentCell);
    }
  }

  private void drawGameOver(Graphics2D g) {
    int height = this.getHeight();
    int width = this.getWidth();

    Rectangle2D foreground = new Rectangle2D.Double(0, 0, width, height);
    g.setColor(colorTheme.getGameOverForeground());
    g.fill(foreground);

    g.setColor(colorTheme.getGameOverFontColor());
    double x = width / 2.0;
    double y = height / 7.0 * 3; // slightly above center

    // for some reason, this single line adds a strange bug
    g.setFont(colorTheme.getGameOverFont());

    Inf101Graphics.drawCenteredString(g, "Game Over", x, y);
  }
}

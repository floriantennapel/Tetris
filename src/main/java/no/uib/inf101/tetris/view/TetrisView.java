package no.uib.inf101.tetris.view;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.model.GameState;
import no.uib.inf101.tetris.model.TetrisBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class TetrisView extends JPanel {
  private static final double OUTER_MARGIN = 5.0;
  private static final double INNER_MARGIN = 2.0;
  private static final int PREFERRED_CELL_SIZE = 30;

  private final ViewableTetrisModel model;
  private final ColorTheme colorTheme;
  private final ColorTheme shadowColorTheme;

  public TetrisView(ViewableTetrisModel model) {
    this.model = model;
    this.colorTheme = new DefaultColorTheme();
    this.shadowColorTheme = new ShadowColorTheme();

    GridDimension dimension = model.getDimension();
    int height = (int) (
        dimension.getRows() * (PREFERRED_CELL_SIZE + INNER_MARGIN)
            + INNER_MARGIN + 2 * OUTER_MARGIN
    );

    int width = (int) (
        dimension.getCols() * (PREFERRED_CELL_SIZE + INNER_MARGIN)
            + INNER_MARGIN + 2 * OUTER_MARGIN
    );

    int sidemenuWidth = width * 2 / 3;

    this.setPreferredSize(new Dimension(width + sidemenuWidth, height));
    this.setBackground(colorTheme.getBackgroundColor());
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    drawSideMenu(g2);
    drawGame(g2);
  }

  private void drawGame(Graphics2D g2) {
    // draw background
    Rectangle2D box = new Rectangle2D.Double(
        OUTER_MARGIN,
        OUTER_MARGIN,
        this.getWidth() * 3 / 5.0 - 2 * OUTER_MARGIN,
        this.getHeight() - 2 * OUTER_MARGIN
    );

    g2.setColor(colorTheme.getFrameColor());
    g2.fill(box);

    CellPositionToPixelConverter posToPixel = new CellPositionToPixelConverter(
        box, model.getDimension(), INNER_MARGIN
    );

    // draw tetris board
    drawCells(g2, model.getTilesOnBoard(), posToPixel, colorTheme);

    // draw moving tetromino on top
    drawCells(g2, model.getMovingTetrominoTiles(), posToPixel, colorTheme);

    // draw position tetromino will drop to
    drawCells(g2, model.getDroppedPosition(), posToPixel, shadowColorTheme);

    if (model.getGameState() == GameState.GAME_OVER) {
      drawGameOver(g2);
    }
    if (model.getGameState() == GameState.PAUSED) {
      drawPauseMenu(g2);
    }
  }

  private static void drawCells(
      Graphics2D g2,
      Iterable<GridCell<Character>> cells,
      CellPositionToPixelConverter posToPixel,
      ColorTheme colorTheme
  ) {
    for (GridCell<Character> gc : cells) {
      Rectangle2D currentCell = posToPixel.getBoundsForCell(gc.pos());
      g2.setColor(colorTheme.getCellColor(gc.value()));
      g2.fill(currentCell);
    }
  }

  private void drawSideMenu(Graphics2D g2) {
    int width = this.getWidth();
    int height = this.getHeight();

    double centerWidth = width * 4 / 5.0;

    g2.setColor(Color.DARK_GRAY);
    g2.setFont(new Font(colorTheme.getFontFamily(), Font.BOLD, width / 21));

    Inf101Graphics.drawCenteredString(g2, "HIGH SCORE", centerWidth, height / 10.0);
    Inf101Graphics.drawCenteredString(g2, getScoreAsString(model.getHighScore()), centerWidth, height * 1.5 / 10);

    Inf101Graphics.drawCenteredString(g2, "SCORE", centerWidth, height * 3 / 10.0);
    Inf101Graphics.drawCenteredString(g2, getScoreAsString(model.getScore()), centerWidth, height * 3.5 / 10);

    Inf101Graphics.drawCenteredString(g2, "LEVEL", centerWidth, height * 5.5 / 10);
    Inf101Graphics.drawCenteredString(g2, Integer.toString(model.getLevel()), centerWidth, height * 6 / 10.0);

    Inf101Graphics.drawCenteredString(g2, "NEXT PIECE", centerWidth, height * 7.5 / 10.0);
    TetrisBoard previewBoard = new TetrisBoard(4, 4);

    // width of cell on main board
    GridDimension dim = model.getDimension();
    double cellWidth = (width * 3 / 5.0 - 2 * OUTER_MARGIN) / dim.getCols();
    double cellHeight = (height - 2 * OUTER_MARGIN) / dim.getRows();

    Rectangle2D box = new Rectangle2D.Double(
        width * 7 / 10.0,
        height * 7.5 / 10,
        cellWidth * 4,
        cellHeight * 4
    );
    CellPositionToPixelConverter posToPixel = new CellPositionToPixelConverter(box, previewBoard, INNER_MARGIN);
    drawCells(g2, model.getNext(), posToPixel, colorTheme);
  }

  private String getScoreAsString(int score) {
    String s = Integer.toString(score);

    while (s.length() < 7) {
      s = "0" + s;
    }

    return s;
  }

  private void drawGameOver(Graphics2D g2) {
    int height = this.getHeight();
    int width = this.getWidth();

    g2.setColor(colorTheme.getPauseForeground());
    g2.fillRect(0, 0, width, height);

    g2.setColor(colorTheme.getBrightFontColor());
    double x = width / 2.0;
    double y = height / 7.0 * 3; // slightly above center

    String fontFamily = colorTheme.getFontFamily();
    Font big = new Font(fontFamily, Font.BOLD, width / 8);
    Font medium = new Font(fontFamily, Font.BOLD, width / 20);

    g2.setFont(big);
    Inf101Graphics.drawCenteredString(g2, "Game Over", x, y);

    g2.setFont(medium);
    Inf101Graphics.drawCenteredString(g2, "Press <Enter> to start a new game", x, y + height / 8.0);
  }

  private void drawPauseMenu(Graphics2D g2) {
    int height = this.getHeight();
    int width = this.getWidth();

    g2.setColor(colorTheme.getPauseForeground());
    g2.fillRect(0, 0, width, height);

    String fontFamily = colorTheme.getFontFamily();
    Font big = new Font(fontFamily, Font.BOLD, width / 8);
    Font medium = new Font(fontFamily, Font.BOLD, width / 20);

    g2.setFont(big);
    g2.setColor(colorTheme.getBrightFontColor());
    double x = width / 2.0;
    double y = height / 7.0 * 3; // slightly above center
    Inf101Graphics.drawCenteredString(g2, "Game paused", x, y);

    g2.setFont(medium);
    Inf101Graphics.drawCenteredString(g2, "Press <Esc> to resume game", x, y + height / 8.0);

  }
}

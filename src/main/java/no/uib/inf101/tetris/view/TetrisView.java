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
  private static final int PREFERRED_CELL_SIZE = 50;
  private static final int SIDE_MENU_WIDTH = 350;

  private final ViewableTetrisModel model;
  private final ColorTheme colorTheme;

  public TetrisView(ViewableTetrisModel model) {
    this.model = model;
    this.colorTheme = new DefaultColorTheme();

    GridDimension dimension = model.getDimension();
    int height = (int) (
        dimension.getRows() * (PREFERRED_CELL_SIZE + INNER_MARGIN)
            + INNER_MARGIN + 2 * OUTER_MARGIN
    );
    int width = (int) (
        dimension.getCols() * (PREFERRED_CELL_SIZE + INNER_MARGIN)
            + INNER_MARGIN + 2 * OUTER_MARGIN + SIDE_MENU_WIDTH
    );

    this.setPreferredSize(new Dimension(width, height));
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
        this.getWidth() - 3 * OUTER_MARGIN - SIDE_MENU_WIDTH,
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

    if (model.getGameState() == GameState.GAME_OVER) {
      drawGameOver(g2);
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
    int x1 = (int) (this.getWidth() - SIDE_MENU_WIDTH - 2 * OUTER_MARGIN);

    g2.setColor(Color.DARK_GRAY);
    g2.setFont(colorTheme.getSideFont());

    g2.drawString("SCORE", x1 + 80, 80);
    g2.drawString(getScoreAsString(), x1 + 80, 120);
    g2.drawString("LEVEL", x1 + 80, 300);
    g2.drawString(Integer.toString(model.getLevel()), x1 + 80, 340);

    g2.drawString("NEXT PIECE", x1 + 80, 560);
    TetrisBoard board = new TetrisBoard(4, 4);
    Rectangle2D box = new Rectangle2D.Double(x1 + 80, 600, PREFERRED_CELL_SIZE * 4, PREFERRED_CELL_SIZE * 4);
    CellPositionToPixelConverter posToPixel = new CellPositionToPixelConverter(box, board, INNER_MARGIN);
    drawCells(g2, model.getNext(), posToPixel, colorTheme);
  }

  private String getScoreAsString() {
    int score = model.getScore();
    String s = Integer.toString(score);

    while (s.length() < 6) {
      s = "0" + s;
    }

    return s;
  }

  private void drawGameOver(Graphics2D g2) {
    int height = this.getHeight();
    int width = this.getWidth();

    Rectangle2D foreground = new Rectangle2D.Double(0, 0, width, height);
    g2.setColor(colorTheme.getGameOverForeground());
    g2.fill(foreground);

    g2.setColor(colorTheme.getGameOverFontColor());
    double x = width / 2.0;
    double y = height / 7.0 * 3; // slightly above center

    // for some reason, this single line adds a slight delay on game-over
    g2.setFont(colorTheme.getGameOverFont());

    Inf101Graphics.drawCenteredString(g2, "Game Over", x, y);
  }
}

package no.uib.inf101.tetris.view;

import java.awt.*;

public interface ColorTheme {
  /** @return the assigned color to this symbol */
  Color getCellColor(char cellSymbol);

  /** @return color of frame surrounding box */
  Color getFrameColor();

  /** @return color of background, can be null */
  Color getBackgroundColor();

  /** font of game over text */
  Font getGameOverFont();

  /** font of screen information */
  Font getSideFont();

  /** color and opacity of game-over screen */
  Color getGameOverForeground();

  /** font-color of game-over message */
  Color getGameOverFontColor();
}

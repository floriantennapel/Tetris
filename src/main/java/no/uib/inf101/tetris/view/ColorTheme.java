package no.uib.inf101.tetris.view;

import java.awt.*;

public interface ColorTheme {
  /** @return the assigned color to this symbol */
  Color getCellColor(char cellSymbol);

  /** @return color of frame surrounding box */
  Color getFrameColor();

  /** @return color of background, can be null */
  Color getBackgroundColor();

  /** @return font family used by all text, for example Arial or Times */
  String getFontFamily();

  /** color and opacity of pause screen */
  Color getPauseForeground();

  /** font-color of game-over message */
  Color getBrightFontColor();

  /** color of start menu background */
  Color getStartMenuBackground();
}

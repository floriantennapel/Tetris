package no.uib.inf101.tetris.view;

import java.awt.*;

public interface ColorTheme {
  /** @return the assigned color to this symbol */
  Color getCellColor(char cellSymbol);

  /** @return color of frame surrounding box */
  Color getFrameColor();

  /** @return color of background, can be null */
  Color getBackgroundColor();

  /** Returns the font, either big, medium or small, defaults to medium
   *
   * @param size either big, medium og small
   * @return Font that a Graphics2D object can be set to
   */
  Font getFont(String size);


  /** color and opacity of pause screen */
  Color getPauseForeground();

  /** font-color of game-over message */
  Color getBrightFontColor();
}

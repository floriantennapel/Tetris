package no.uib.inf101.tetris.view;

import java.awt.Color;

public interface ColorTheme {
  /** @return the assigned color to this symbol */
  Color getCellColor(char cellSymbol);

  /** @return color of frame surrounding box */
  Color getFrameColor();

  /** @return color of background, can be null */
  Color getBackgroundColor();
}

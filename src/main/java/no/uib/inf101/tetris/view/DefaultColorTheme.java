package no.uib.inf101.tetris.view;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class DefaultColorTheme implements ColorTheme {
  private final Map<Character, Color> charToColorMap = new HashMap<>(Map.of(
      '-', Color.DARK_GRAY,
      'g', Color.GREEN,
      'y', Color.YELLOW,
      'r', Color.RED,
      'b', Color.BLUE
  ));

  @Override
  public Color getCellColor(char cellSymbol) throws IllegalArgumentException {
    if (!charToColorMap.containsKey(cellSymbol)) {
      throw new IllegalArgumentException();
    }

    return charToColorMap.get(cellSymbol);
  }

  @Override
  public Color getFrameColor() {
    return new Color(0, 0, 0, 0);
  }

  @Override
  public Color getBackgroundColor() {
    return null;
  }
}

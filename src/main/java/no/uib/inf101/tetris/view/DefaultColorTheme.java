package no.uib.inf101.tetris.view;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class DefaultColorTheme implements ColorTheme {
  // package private
  private final Map<Character, Color> charToColorMap;

  private final Font gameOverFont;
  private final Font sideFont;

  public DefaultColorTheme() {
    //TODO read colorMappings from file
    charToColorMap = new HashMap<>(Map.of(
        '-', Color.DARK_GRAY,
        'g', Color.GREEN,
        'y', Color.YELLOW,
        'r', Color.RED,
        'b', Color.BLUE,
        'I', Color.CYAN,
        'J', Color.BLUE,
        'L', Color.ORANGE,
        'O', Color.YELLOW,
        'S', Color.GREEN.darker()
    ));
    //Map.of has a max limit of 10 pairs
    charToColorMap.put('T', Color.MAGENTA);
    charToColorMap.put('Z', Color.RED);

    gameOverFont = new Font("Arial", Font.BOLD, 80);
    sideFont = new Font("Arial", Font.BOLD, 40);
  }

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

  @Override
  public Font getGameOverFont() {
    return gameOverFont;
  }

  @Override
  public Font getSideFont() {
    return sideFont;
  }

  @Override
  public Color getGameOverForeground() {
    return new Color(0, 0, 0, 128);
  }

  @Override
  public Color getGameOverFontColor() {
    return Color.LIGHT_GRAY.brighter();
  }
}

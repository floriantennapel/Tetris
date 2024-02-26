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
        '-', hexColor("8a8a8a"),
        'g', Color.GREEN,
        'y', Color.YELLOW,
        'r', Color.RED,
        'b', Color.BLUE,
        'I', hexColor("00bfc1"),
        'J', hexColor("004ec1"),
        'L', hexColor("ff6500"),
        'O', hexColor("e2f427"),
        'S', hexColor("0bdb3b")
    ));
    //Map.of has a max limit of 10 pairs
    charToColorMap.put('T', hexColor("ce28da"));
    charToColorMap.put('Z', hexColor("d82a2a"));

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

  private Color hexColor(String hexValue) {
    return new Color(Integer.parseInt(hexValue, 16));
  }
}

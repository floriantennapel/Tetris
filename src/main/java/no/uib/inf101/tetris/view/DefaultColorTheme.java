package no.uib.inf101.tetris.view;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class DefaultColorTheme implements ColorTheme {
  private static final String COLOR_FILE = "defaultColors.txt";

  private final Map<Character, Color> charToColorMap;
  private final Font bigFont;
  private final Font mediumFont;

  public DefaultColorTheme() {
    charToColorMap = new HashMap<>();
    readColorsFromFile();
    bigFont = new Font("Arial", Font.BOLD, 80);
    mediumFont = new Font("Arial", Font.BOLD, 40);
  }

  @Override
  public Color getCellColor(char cellSymbol) throws IllegalArgumentException {
    if (!charToColorMap.containsKey(cellSymbol)) {
      throw new IllegalArgumentException("Invalid tetromino symbol");
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
  public Font getBigFont() {
    return bigFont;
  }

  @Override
  public Font getMediumFont() {
    return mediumFont;
  }

  @Override
  public Color getPauseForeground() {
    return new Color(0, 0, 0, 128);
  }

  @Override
  public Color getBrightFontColor() {
    return Color.LIGHT_GRAY.brighter();
  }

  private Color hexColor(String hexValue) {
    return new Color(Integer.parseInt(hexValue, 16));
  }

  private void readColorsFromFile() throws RuntimeException {
    try {
      InputStream inputStream = DefaultColorTheme.class.getResourceAsStream(COLOR_FILE);
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

      String line;
      while ((line = reader.readLine()) != null) {
        char symbol = line.charAt(0);
        String color = line.substring(2, 8);

        charToColorMap.put(symbol, hexColor(color));
      }

      reader.close();
      inputStream.close();
    } catch (IOException | NullPointerException e) {
      throw new RuntimeException(e);
    }
  }
}

package no.uib.inf101.tetris.view;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class DefaultColorTheme implements ColorTheme {
  // color of pieces is stored in separate file
  private static final String COLOR_FILE = "defaultColors.txt";

  // PressStart2P font from Google fonts, licenced under the Open Font Licence
  private static final String FONT_FILE = "PressStart2P-Regular.ttf";

  private final Map<Character, Color> charToColorMap;
  private final Font font;

  public DefaultColorTheme() {
    charToColorMap = new HashMap<>();
    readColorsFromFile();
    font = readFontFromFile();
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
    return new Color(252, 248, 234);
  }

  @Override
  public Color getPauseForeground() {
    return new Color(0, 0, 0, 150);
  }

  @Override
  public Color getBrightFontColor() {
    return Color.WHITE;
  }

  @Override
  public Font getFont(double size) {
    // floating point default is double, however deriveFont only accepts float
    // instead of always casting to float when calling it is easier to do it here
    return font.deriveFont((float) size);
  }

  private Font readFontFromFile() {
    Font defaultFont = new Font("Courier", Font.BOLD, 12);
    try {
      InputStream stream = DefaultColorTheme.class.getResourceAsStream(FONT_FILE);
      if (stream == null) {
        System.err.println("could not read font from file");
        return defaultFont;
      }
      Font font = Font.createFont(Font.TRUETYPE_FONT, stream);
      stream.close();

      return font;
    } catch (IOException | FontFormatException e) {
      System.err.println("Could not read font from file");
      return defaultFont;
    }
  }

  private Color hexColor(String hexValue) {
    return new Color(Integer.parseInt(hexValue, 16));
  }

  private void readColorsFromFile() throws RuntimeException {
    try {
      InputStream inputStream = DefaultColorTheme.class.getResourceAsStream(COLOR_FILE);
      if (inputStream == null) {
        System.err.println("could not read tetromino colors from file");
        return;
      }
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

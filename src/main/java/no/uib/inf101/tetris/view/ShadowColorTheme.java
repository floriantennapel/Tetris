package no.uib.inf101.tetris.view;

import java.awt.*;

public class ShadowColorTheme extends DefaultColorTheme {
  @Override
  public Color getCellColor(char cellSymbol) throws IllegalArgumentException {
    Color original = super.getCellColor(cellSymbol);

    float[] components = new float[4];
    components = original.getColorComponents(components);

    return new Color(components[0], components[1], components[2], 0.6f);
  }
}

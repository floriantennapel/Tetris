package no.uib.inf101.tetris.view;

import java.awt.*;

public class ShadowColorTheme extends DefaultColorTheme {
  @Override
  public Color getCellColor(char cellSymbol) throws IllegalArgumentException {
    Color original = super.getCellColor(cellSymbol);

    float[] rgba = new float[4];
    rgba = original.getColorComponents(rgba);

    return new Color(rgba[0], rgba[1], rgba[2], 0.3f);
  }
}

package no.uib.inf101.tetris.view;

import no.uib.inf101.tetris.model.tetromino.Tetromino;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultColorThemeTest {
  @Test
  public void sanityDefaultColorThemeTest() {
    // Removed all tests checking if some attributes have a specific color
    // I want to be able to tweak these values and not have to change the test
    ColorTheme colors = new DefaultColorTheme();

    for (char symbol : Tetromino.VALID_SHAPES.toCharArray()) {
      assertNotNull(colors.getCellColor(symbol));
    }

    assertThrows(IllegalArgumentException.class, () -> colors.getCellColor('\n'));
  }
}

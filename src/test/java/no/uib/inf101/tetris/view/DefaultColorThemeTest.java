package no.uib.inf101.tetris.view;

import org.junit.jupiter.api.Test;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultColorThemeTest {
  @Test
  public void sanityDefaultColorThemeTest() {
    ColorTheme colors = new DefaultColorTheme();
    assertNull(colors.getBackgroundColor());
    assertEquals(new Color(0, 0, 0, 0), colors.getFrameColor());
    assertEquals(Color.DARK_GRAY, colors.getCellColor('-'));
    assertEquals(Color.RED, colors.getCellColor('r'));
    assertThrows(IllegalArgumentException.class, () -> colors.getCellColor('\n'));
  }
}

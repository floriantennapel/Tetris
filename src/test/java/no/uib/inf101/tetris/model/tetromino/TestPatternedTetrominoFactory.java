package no.uib.inf101.tetris.model.tetromino;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPatternedTetrominoFactory {
  @Test
  public void sanityTestPatternedTetrominoFactory() {
    TetrominoFactory factory = new PatternedTetrominoFactory("TSZ");

    assertEquals(Tetromino.newTetromino('T'), factory.getNext());
    assertEquals(Tetromino.newTetromino('S'), factory.getNext());
    assertEquals(Tetromino.newTetromino('Z'), factory.getNext());
    assertEquals(Tetromino.newTetromino('T'), factory.getNext());
    assertEquals(Tetromino.newTetromino('S'), factory.getNext());
  }
}

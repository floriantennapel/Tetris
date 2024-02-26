package no.uib.inf101.tetris.model.tetromino;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestBagTetrominoFactory {

  @Test
  public void testUniformlyDistributed() {
    TetrominoFactory factory = new BagTetrominoFactory();
    Map<Tetromino, Integer> counter = new HashMap<>();
    int numberOfShapes = Tetromino.VALID_SHAPES.length();
    double target = 1.0 / numberOfShapes;

    // testing for every bag index
    for (int bagIndex = 0; bagIndex < numberOfShapes; bagIndex++) {

      // move to index that is getting tested
      for (int i = 0; i < bagIndex; i++) {
        factory.getNext();
      }

      int iterations = 100000;
      for (int i = 0; i < iterations; i++) {
        Tetromino first = factory.getNext();
        counter.put(first, counter.getOrDefault(first, 0) + 1);

        // skipping rest of bag
        for (int j = 0; j < numberOfShapes - 1; j++) {
          factory.getNext();
        }
      }

      for (int freq : counter.values()) {
        double relativeFreq = freq / (double) iterations;

        assertTrue(Math.abs(relativeFreq - target) < 0.01);
      }

      counter.clear();
    }
  }


  @Test
  public void testValidBag() {
    TetrominoFactory factory = new BagTetrominoFactory();
    Set<Tetromino> checked = new HashSet<>();

    for (int i = 0; i < 10000; i++) {
      for (int j = 0; j < Tetromino.VALID_SHAPES.length(); j++) {
        Tetromino current = factory.getNext();
        assertFalse(checked.contains(current));
        checked.add(current);
      }
      checked.clear();
    }
  }
}

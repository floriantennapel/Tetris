package no.uib.inf101.tetris.model.tetromino;

import java.util.ArrayList;
import java.util.List;

// implementation of official tetromino generating algorithm
// https://tetris.fandom.com/wiki/Random_Generator
public class BagTetrominoFactory implements TetrominoFactory {
  private List<Character> bag;

  public BagTetrominoFactory() {
    bag = makeNewBag();
  }

  private List<Character> makeNewBag() {
    String validShapes = Tetromino.VALID_SHAPES;
    List<Character> bag = new ArrayList<>();
    List<Character> shapes = new ArrayList<>(
        validShapes.chars().mapToObj(c -> (char) c).toList()
    );

    for (int i = 0; i < validShapes.length(); i++) {
      int randIndex = (int) (Math.random() * shapes.size());
      bag.add(shapes.remove(randIndex));
    }

    return bag;
  }


  @Override
  public Tetromino getNext() {
    if (bag.isEmpty()) {
      bag = makeNewBag();
    }

    char shape = bag.remove(0);
    return Tetromino.newTetromino(shape);
  }
}

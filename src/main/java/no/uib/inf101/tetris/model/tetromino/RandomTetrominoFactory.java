package no.uib.inf101.tetris.model.tetromino;

public class RandomTetrominoFactory implements TetrominoFactory {
  @Override
  public Tetromino getNext() {
    String shapes = Tetromino.VALID_SHAPES;

    int shapeIndex = (int) (Math.random() * shapes.length());
    char shape = shapes.charAt(shapeIndex);

    return Tetromino.newTetromino(shape);
  }
}

package no.uib.inf101.tetris.model.tetromino;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.tetris.model.TetrisBoard;
import no.uib.inf101.tetris.model.TetrisModel;
import no.uib.inf101.tetris.view.ViewableTetrisModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTetrisModel {
  @Test
  public void initialPositionOfO() {
    TetrisBoard board = new TetrisBoard(20, 10);
    TetrominoFactory factory = new PatternedTetrominoFactory("O");
    ViewableTetrisModel model = new TetrisModel(board, factory);

    List<GridCell<Character>> tetroCells = new ArrayList<>();
    for (GridCell<Character> gc : model.getMovingTetrominoTiles()) {
      tetroCells.add(gc);
    }

    assertEquals(4, tetroCells.size());
    assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 4), 'O')));
    assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 5), 'O')));
    assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 4), 'O')));
    assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 5), 'O')));
  }

  @Test
  public void initialPositionOfI() {
    TetrisBoard board = new TetrisBoard(20, 10);
    TetrominoFactory factory = new PatternedTetrominoFactory("I");
    ViewableTetrisModel model = new TetrisModel(board, factory);

    List<GridCell<Character>> tetroCells = new ArrayList<>();
    for (GridCell<Character> gc : model.getMovingTetrominoTiles()) {
      tetroCells.add(gc);
    }

    assertEquals(4, tetroCells.size());
    assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 3), 'I')));
    assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 4), 'I')));
    assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 5), 'I')));
    assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(0, 6), 'I')));
  }
}

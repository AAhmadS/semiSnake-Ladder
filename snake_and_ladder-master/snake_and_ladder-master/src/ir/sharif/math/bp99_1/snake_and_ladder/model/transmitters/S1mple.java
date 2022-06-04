package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;

public class S1mple extends Transmitter{
    public S1mple(Cell firstCell, Cell lastCell) {
        super(firstCell, lastCell);
        color= Color.BLUE;
        Char='S';
    }
}

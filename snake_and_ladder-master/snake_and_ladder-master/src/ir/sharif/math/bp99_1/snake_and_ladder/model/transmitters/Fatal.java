package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

public class Fatal extends Transmitter{
    public Fatal(Cell firstCell, Cell lastCell) {
        super(firstCell, lastCell);
        color= Color.YELLOW;
        Char='F';
    }

    @Override
    public void transmit(Piece piece) {
        super.transmit(piece);
        piece.setLivingState(false);
    }
}

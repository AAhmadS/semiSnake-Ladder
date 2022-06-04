package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Thief;

public class Transmitter {
    protected Cell firstCell, lastCell;
    protected Color color=Color.BLUE;
    protected char Char='n';

    public Transmitter(Cell firstCell, Cell lastCell) {
        this.firstCell = firstCell;
        this.lastCell = lastCell;
    }

    public Color getColor(){
        return color;
    }

    public Cell getFirstCell() {
        return firstCell;
    }

    public Cell getLastCell() {
        return lastCell;
    }

    public char getChar(){ return Char; }

    /**
     * transmit piece to lastCell
     */
    public void transmit(Piece piece) {
        if (lastCell.canEnter(piece)){
            piece.moveTo(lastCell);
        }
        else {
            piece.getCurrentCell().setPiece(null);
            piece.setCurrentCell(firstCell);
            firstCell.setPiece(piece);
        }
    }
}

package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

import java.util.List;
import java.util.Random;

public class Worm extends Transmitter{
    private Random destinationX;
    private Random destinationY;
    private List<Cell> Cells;
    public Worm(Cell firstCell, Cell lastCell, List<Cell> Cells) {
        super(firstCell, lastCell);
        color=Color.GREEN;
        destinationX=new Random();
        destinationY=new Random();
        this.Cells=Cells;
        Char='W';
    }

    @Override
    public void transmit(Piece piece) {
        int X=destinationX.nextInt(7)+1;
        int Y=destinationY.nextInt(16)+1;
        Cell destination= Cells.get((X-1)*16+Y-1);
        if (destination.canEnter(piece)){
            piece.moveTo(destination);
        }
        else {
            piece.getCurrentCell().setPiece(null);
            piece.setCurrentCell(firstCell);
            firstCell.setPiece(piece);
        }
    }
}

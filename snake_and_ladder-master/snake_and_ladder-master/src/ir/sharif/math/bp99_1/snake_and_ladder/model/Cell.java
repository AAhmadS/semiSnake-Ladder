package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cell {
    private Color color;
    private final int x, y;
    private Transmitter transmitter;
    private Prize prize;
    private Piece piece;
    private final List<Cell> adjacentOpenCells;
    private final List<Cell> adjacentCells;

    public Cell(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.transmitter = null;
        this.prize = null;
        this.piece = null;
        this.adjacentOpenCells = new ArrayList<>();
        this.adjacentCells = new ArrayList<>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public List<Cell> getAdjacentCells() {
        return adjacentCells;
    }

    public List<Cell> getAdjacentOpenCells() {
        return adjacentOpenCells;
    }

    public Piece getPiece() {
        return piece;
    }

    public Prize getPrize() {
        return prize;
    }

    public Transmitter getTransmitter() {
        return transmitter;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void setPrize(Prize prize) {
        this.prize = prize;
    }

    public void setTransmitter(Transmitter transmitter) {
        this.transmitter = transmitter;
    }

    public void setColor(Color color){this.color=color;}

    /**
     * @return true if piece can enter this cell, else return false
     */

    public boolean canEnter(Piece piece) {

        /*** this part is a not-perfected one */

        if (piece.getColor().equals(Color.BLUE)){
            return canEnterThief(piece);
        }

        if (this.getPiece()==null||this.getPiece()==piece){
            if (this.getColor().equals(piece.getColor())||this.getColor().equals(Color.WHITE)){
                return true;
            }
        }
        return false;
    }

    public boolean canEnterThief(Piece piece){
        if (this.color.equals(Color.BLACK)){
            return false;
        }
        if (this.piece!=null&&!this.piece.equals(piece)){
            return false;
        }
        return true;
    }

    public boolean isValidMoveXSeries(Cell destination, int diceNum){
        FOR:for (Cell j:this.adjacentOpenCells) {
            if (j==null)continue FOR;
            if (j.getX()==this.x+1){
                if (diceNum==1&&j.getX()== destination.getX()){
                    return true;
                }
                else{
                   return this.adjacentOpenCells.get(adjacentOpenCells.indexOf(j)).isValidMoveXSeries(destination,diceNum-1);
                }
            }
        }
        return false;
    }

    public boolean isValidMoveYSeries(Cell destination, int diceNum){
        FOR:for (Cell j:this.adjacentOpenCells) {
            if (j==null)continue FOR;
            if (j.getY()==this.y+1){
                if (diceNum==1&&j.getY()==destination.getY()){
                    return true;
                }
                else{
                    return this.adjacentOpenCells.get(adjacentOpenCells.indexOf(j)).isValidMoveYSeries(destination,diceNum-1);
                }
            }
        }
        return false;
    }

    /**
     * DO NOT CHANGE FOLLOWING METHODS.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

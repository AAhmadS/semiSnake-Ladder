package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.logic.ModelLoader;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;

public class Sniper extends Piece{

    public Sniper(Player player, Color color) {
        super(player, color);
    }

    @Override
    public boolean isValidMove(Cell destination, int diceNumber) {
        if (destination==null)return false;
        if (isAbilityOn){
            if (destination.getPiece()!=null){
                if (!destination.getPiece().player.equals(this.player)&&destination.getPiece().getLivingState()&&!destination.getPiece().getColor().equals(Color.GREEN)){
                    if (currentCell.getX()==destination.getX()){
                        if (Math.abs(currentCell.getY()- destination.getY())<=diceNumber){
                            return true;
                        }
                    }
                    if (currentCell.getY()==destination.getY()){
                        if (Math.abs(currentCell.getX()- destination.getX())<=diceNumber){
                            return true;
                        }
                    }
                }
            }
        }
        return super.isValidMove(destination, diceNumber);
    }

    @Override
    public void moveTo(Cell destination) {
        if (destination.getPiece()==null){
            super.moveTo(destination);
        }
        else{
            destination.getPiece().setLivingState(false);
            this.setAbilityState(false);
        }
    }

    @Override
    public void moveTo(Cell destination, ModelLoader modelLoader, Player player1, Player player2) {
    }
}

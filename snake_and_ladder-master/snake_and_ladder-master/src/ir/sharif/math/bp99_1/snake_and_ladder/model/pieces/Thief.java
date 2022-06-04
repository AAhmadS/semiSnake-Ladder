package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.logic.ModelLoader;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;

public class Thief extends Piece{
    private Prize prize;
    public Thief(Player player, Color color) {
        super(player, color);
        prize=null;
        isAbilityOn=true;
    }
    public Prize getPrize(){
        return prize;
    }
    public void setPrize(Prize prize){
        this.prize= prize;
    }
    @Override
    public boolean isValidMove(Cell destination, int diceNumber) {
        if (diceNumber==0)return false;
        if (destination==null)return false;
        if (destination.equals(currentCell)){
            if (destination.getPrize()!=null)return true;
            else{
                if (prize!=null){
                    return true;
                }
                return false;
            }
        }
        else{
            if (destination.getColor()!=Color.BLACK&&destination.getPiece()==null){
                if (currentCell.getX()==destination.getX()){
                    if (Math.abs(destination.getY()- currentCell.getY())==diceNumber)return true;
                    else return false;
                }
                if (currentCell.getY()==destination.getY()){
                    if (Math.abs(destination.getX()- currentCell.getX())==diceNumber)return true;
                }
            }
        }
        return false;
    }

    @Override
    public void moveTo(Cell destination) {
        if (destination.equals(currentCell)&&destination.getPrize()!=null){
            if (prize==null){
                prize= destination.getPrize();
                destination.setPrize(null);
            }
            else{
                destination.setPrize(prize);
                prize=null;
            }
        }
        else{
            if (color.equals(destination.getColor()))player.applyOnScore(4);
            if (destination.getTransmitter()==null){
                if (!(destination.getPrize()==null)){
                    this.getCurrentCell().setPiece(null);
                    this.setCurrentCell(destination);
                    destination.setPiece(this);
                    player.usePrize(destination.getPrize());
                }
                else {
                    this.getCurrentCell().setPiece(null);
                    this.setCurrentCell(destination);
                    destination.setPiece(this);
                }
            }
            else{
                player.setScore(player.getScore()-3);
                this.prize=null;
                if (!(destination.getPrize()==null)){
                    player.usePrize(destination.getPrize());
                }
                destination.getTransmitter().transmit(this);
                setAbilityState(true);
            }
        }
    }

    @Override
    public void moveTo(Cell destination, ModelLoader modelLoader, Player player1, Player player2) {
        if (prize==null&&destination.getPrize()!=null){
            //System.out.println("getting");
            destination.getPrize().setCell(null);
            this.setPrize(destination.getPrize());
            destination.setPrize(null);
            modelLoader.archivePrizes(player1,player2,destination,null);
        }
        else{
            if (prize!=null&&destination.getPrize()==null){
               // System.out.println("putting");
                this.prize.setCell(destination);
                destination.setPrize(prize);
                modelLoader.archivePrizes(player1,player2,destination,prize);
                prize=null;

            }
            else{
                if (prize!=null&&destination.getPrize()!=null){
                   // System.out.println("omitting and putting");
                    this.prize.setCell(destination);
                    destination.setPrize(prize);
                    modelLoader.archivePrizes(player1,player2,destination,prize);
                    prize=null;
                    System.out.println(destination.getPrize().getPoint()+" "+this.prize);
                }
            }
        }
    }
}

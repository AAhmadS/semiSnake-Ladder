package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.logic.ModelLoader;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;

public class Bomber extends Piece{
    public Bomber(Player player, Color color) {
        super(player, color);
    }
    @Override
    public boolean isValidMove(Cell destination, int diceNumber) {
        if (destination==null)return false;
        if (destination==this.currentCell&&isAbilityOn)return true;
        return super.isValidMove(destination, diceNumber);
    }

    @Override
    public void moveTo(Cell destination) {
        if (destination.getPiece()==null){
            super.moveTo(destination);
        }
//        else{
//            this.setAbilityState(false);
//            destination.setColor(Color.BLACK);
//            for (Cell cell:currentCell.getAdjacentCells()){
//                if (cell.getPrize()!=null){
//                    cell.setPrize(null);
//                }
//                if (cell.getPiece()!=null&&!cell.getPiece().getColor().equals(Color.GREEN)){
//                    cell.getPiece().setLivingState(false);
//                }
//                for (Cell adjCell:cell.getAdjacentCells()){
//                    if (adjCell.getX()> destination.getX()-2&&adjCell.getX()< destination.getX()+2){
//                        if (adjCell.getY()> destination.getY()-2&&adjCell.getY()< destination.getY()+2){
//                            adjCell.setPrize(null);
//                            if (adjCell.getPiece()!=null&&!adjCell.getPiece().getColor().equals(Color.GREEN)){
//                                adjCell.getPiece().setLivingState(false);
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }

    @Override
    public void moveTo(Cell destination, ModelLoader modelLoader, Player player1, Player player2) {
        this.setAbilityState(false);
        this.setLivingState(false);
        destination.setColor(Color.BLACK);
        modelLoader.archiveCells(player1,player2,destination);
        for (Cell cell:currentCell.getAdjacentCells()){
            if (cell.getPrize()!=null){
                cell.getPrize().setCell(null);
                cell.setPrize(null);
                modelLoader.archivePrizes(player1,player2,cell,null);
            }
            if (cell.getPiece()!=null&&!cell.getPiece().getColor().equals(Color.GREEN)){
                cell.getPiece().setLivingState(false);
                if (!cell.getPiece().getColor().equals(Color.BLUE)){
                    cell.getPiece().setAbilityState(false);
                }
            }
            for (Cell adjCell:cell.getAdjacentCells()){
                if (adjCell.getX()> destination.getX()-2&&adjCell.getX()< destination.getX()+2){
                    if (adjCell.getY()> destination.getY()-2&&adjCell.getY()< destination.getY()+2){
                        if (adjCell.getPrize()!=null){
                            adjCell.getPrize().setCell(null);
                            adjCell.setPrize(null);
                            modelLoader.archivePrizes(player1,player2,adjCell,null);
                        }
                        if (adjCell.getPiece()!=null&&!adjCell.getPiece().getColor().equals(Color.GREEN)){
                            adjCell.getPiece().setLivingState(false);
                            if (!adjCell.getPiece().getColor().equals(Color.BLUE)){
                                adjCell.getPiece().setAbilityState(false);
                            }
                        }
                    }
                }
            }
        }
    }
}

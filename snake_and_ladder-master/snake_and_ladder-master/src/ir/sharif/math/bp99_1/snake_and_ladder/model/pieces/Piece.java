package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.logic.ModelLoader;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;


public abstract class Piece {
    protected Cell currentCell;
    protected Color color;
    protected Player player;
    protected boolean isSelected;
    protected boolean isAbilityOn;
    protected boolean isAlive;

    public Piece(Player player, Color color) {
        this.color = color;
        this.player = player;
        this.isAbilityOn=false;
        this.isAlive=true;
    }

    public Player getPlayer() {
        return player;
    }

    public Color getColor() {
        return color;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public boolean getLivingState(){
        return isAlive;
    }

    public void setLivingState(boolean livingState){
        isAlive=livingState;
    }

    public boolean getAbilityState(){
        return isAbilityOn;
    }

    public void setAbilityState(boolean abilityState){
        isAbilityOn=abilityState;
    }
    /**
     * @return "true" if your movement is valid  , else return " false"
     * <p>
     * In this method, you should check if the movement is valid or not.
     * <p>
     * You can use some methods ( they are recommended )
     * <p>
     * 1) "canEnter" method in class "Cell"
     * <p>
     * if your movement is valid, return "true" , else return " false"
     */
    public boolean isValidMove(Cell destination, int diceNumber) {

        /*** this part is a not-perfected one */
        if (diceNumber==0)return false;
        if (!destination.canEnter(this))return false;
        if (currentCell.getX()==destination.getX()){
            if (currentCell.getY()- destination.getY()==diceNumber){
                return destination.isValidMoveYSeries(currentCell,diceNumber);
            }
            if (destination.getY()- currentCell.getY()==diceNumber){
                return currentCell.isValidMoveYSeries(destination,diceNumber);
            }
        }
        if (currentCell.getY()==destination.getY()){
            if (currentCell.getX()- destination.getX()==diceNumber){
                return destination.isValidMoveXSeries(currentCell,diceNumber);
            }
            if (destination.getX()- currentCell.getX()==diceNumber){
                return currentCell.isValidMoveXSeries(destination,diceNumber);
            }
        }
        return false;
    }


    /**
     * @param destination move selected piece from "currentCell" to "destination"
     */
    public void moveTo(Cell destination) {

        /*** this part is a not-perfected one */
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
            player.applyOnScore(-3);
            if (!(destination.getPrize()==null)){
                player.usePrize(destination.getPrize());
            }
            destination.getTransmitter().transmit(this);
        }
    }
    public void moveTo(Cell destination, ModelLoader modelLoader,Player player1,Player player2){};
}

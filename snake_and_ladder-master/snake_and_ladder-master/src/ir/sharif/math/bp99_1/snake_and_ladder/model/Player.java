package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.*;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    private final String name;
    private int score;
    private final List<Piece> pieces;
    private final Dice dice;
    private Player rival;
    private final int id;
    private int playerNumber;
    private boolean isReady;
    private boolean dicePlayedThisTurn;
    private int moveLeft;
    private Piece selectedPiece;
    private Thief thief;
    public Player(String name, int score, int id, int playerNumber) {
        this.name = name;
        this.score = score;
        this.id = id;
        this.playerNumber = playerNumber;
        this.dice = new Dice(this);
        this.pieces = new ArrayList<>();
        this.pieces.add(new Sniper(this, Color.RED));
        this.thief=new Thief(this,Color.BLUE);
        this.pieces.add(thief);
        this.pieces.add(new Healer(this, Color.GREEN));
        this.pieces.add(new Bomber(this, Color.YELLOW));
        this.moveLeft = 0;
        this.selectedPiece = null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Dice getDice() {
        return dice;
    }

    public int getScore() {
        return score;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public Player getRival() {
        return rival;
    }

    public int getMoveLeft() {
        return moveLeft;
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public boolean isDicePlayedThisTurn() {
        return dicePlayedThisTurn;
    }

    public void setDicePlayedThisTurn(boolean dicePlayedThisTurn) {
        this.dicePlayedThisTurn = dicePlayedThisTurn;
    }

    public void setSelectedPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
    }

    public void setMoveLeft(int moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void setRival(Player rival) {
        this.rival = rival;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void applyOnScore(int score) {
        this.score += score;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public Thief getThief(){return thief;}

    /**
     * @param prize according to input prize , apply necessary changes to score and dice chance
     *              <p>
     *              you can use method "addChance" in class "Dice"(not necessary, but recommended)
     */

    public void usePrize(Prize prize) {

        /*** this part is a not-perfected one */

        this.applyOnScore(prize.getPoint());
        this.dice.addChance(prize.getDiceNumber(), prize.getChance());
    }


    /**
     * check if any of player pieces can move to another cell.
     *
     * @return true if at least 1 piece has a move , else return false
     * <p>
     * you can use method "isValidMove" in class "Piece"(not necessary, but recommended)
     */

    public boolean hasMove(Board board, int diceNumber) {

        /*** this part is a not-perfected one */

        dice.checkForDouble(diceNumber);
        switch (diceNumber){
            case 2:
                dice.reset();
                break;
            case 1:
                pieces.get(2).setAbilityState(true);
                break;
            case 5:
                pieces.get(0).setAbilityState(true);
                break;
            case 3:
                pieces.get(3).setAbilityState(true);
                break;
            default:
                break;
        }
        FOR:for (Piece iteratedPeice:pieces){
            if (!iteratedPeice.getLivingState())continue FOR;
            int x=iteratedPeice.getCurrentCell().getX();int y=iteratedPeice.getCurrentCell().getY();
            for (int i=1;i<=diceNumber;i++){
                if (iteratedPeice.isValidMove(board.getCell(x+i,y),diceNumber))return true;
                if (iteratedPeice.isValidMove(board.getCell(x-i,y),diceNumber))return true;
                if (iteratedPeice.isValidMove(board.getCell(x,y+i),diceNumber))return true;
                if (iteratedPeice.isValidMove(board.getCell(x,y-i),diceNumber))return true;
            }
            if (iteratedPeice.isValidMove(board.getCell(x,y),diceNumber))return true;
        }
        score-=3;
        return false;
    }


    /**
     * Deselect selectedPiece and make some changes in this class fields.
     */

    public void endTurn() {
        if (selectedPiece!=null){
            this.selectedPiece.setSelected(false);
            this.setSelectedPiece(null);
        }
        dicePlayedThisTurn=false;
        moveLeft=0;
    }


    /**
     * DO NOT CHANGE FOLLOWING METHODS.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}


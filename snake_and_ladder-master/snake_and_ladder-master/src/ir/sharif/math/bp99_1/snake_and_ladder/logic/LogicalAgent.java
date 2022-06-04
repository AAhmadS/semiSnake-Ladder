package ir.sharif.math.bp99_1.snake_and_ladder.logic;


import ir.sharif.math.bp99_1.snake_and_ladder.graphic.GraphicalAgent;
import ir.sharif.math.bp99_1.snake_and_ladder.model.*;

import java.io.File;


/**
 * This class is an interface between logic and graphic.
 * some methods of this class, is called from graphic.
 * DO NOT CHANGE ANY PART WHICH WE MENTION.
 */
public class LogicalAgent {
    private final ModelLoader modelLoader;
    private final GraphicalAgent graphicalAgent;
    private final GameState gameState;

    /**
     * DO NOT CHANGE CONSTRUCTOR.
     */
    public LogicalAgent()  {
        this.graphicalAgent = new GraphicalAgent(this);
        this.modelLoader = new ModelLoader();
        this.gameState = loadGameState();
    }


    /**
     * NO CHANGES NEEDED.
     */
    private GameState loadGameState()  {
        Board board = modelLoader.loadBord();
        Player player1 = modelLoader.loadPlayer(graphicalAgent.getPlayerNames(1), 1);
        Player player2;
        do {
            player2 = modelLoader.loadPlayer(graphicalAgent.getPlayerNames(2), 2);
        } while (player1.equals(player2));
        player1.setRival(player2);
        player2.setRival(player1);
        int[] turn=new int[1];
        turn[0]=0;
        modelLoader.checkForUpdate(board,player1,player2,turn);
        if (turn[0]!=0){
            System.out.println(turn[0]);
            return new GameState(board, player1, player2,turn[0]);
        }
        System.out.println(0);
        return new GameState(board, player1, player2);
    }

    /**
     * NO CHANGES NEEDED.
     */
    public void initialize() {
        graphicalAgent.initialize(gameState);
    }

    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who clicks "ReadyButton".) you should somehow change that player state.
     * if both players are ready. then start the game.
     */
    public void readyPlayer(int playerNumber) {
        gameState.getPlayer(playerNumber).setReady(!gameState.getPlayer(playerNumber).isReady());
        if (gameState.getPlayer1().isReady()&&gameState.getPlayer2().isReady()){
            gameState.startTheGame();
        }
        // dont touch this line
        graphicalAgent.update(gameState);
    }

    /**
     * give x,y (coordinates of a cell) :
     * you should handle if user want to select a piece
     * or already selected a piece and now want to move it to a new cell
     */
    // ***
    public void selectCell(int x, int y) {
        if (gameState.isStarted()){
            Cell mentionedCell=gameState.getBoard().getCell(x,y);
            if (mentionedCell!=null){
                Player currentPlayer=gameState.getCurrentPlayer();
                if (currentPlayer.getSelectedPiece()==null){
                    if (mentionedCell.getPiece()!=null){
                        if (mentionedCell.getPiece().getPlayer().equals(currentPlayer)&&mentionedCell.getPiece().getLivingState()){
                            gameState.getCurrentPlayer().setSelectedPiece(mentionedCell.getPiece());
                            gameState.getCurrentPlayer().getSelectedPiece().setSelected(true);
                        }
                    }
                }
                else{
                    if (currentPlayer.getSelectedPiece().isValidMove(gameState.getBoard().getCell(x,y),currentPlayer.getMoveLeft())){
                        if (x==currentPlayer.getSelectedPiece().getCurrentCell().getX()&&y==currentPlayer.getSelectedPiece().getCurrentCell().getY()){
                            gameState.getCurrentPlayer().getSelectedPiece().moveTo(gameState.getBoard().getCell(x,y),modelLoader, gameState.getPlayer1(), gameState.getPlayer2());
                        }
                        else{
                            gameState.getCurrentPlayer().getSelectedPiece().moveTo(gameState.getBoard().getCell(x,y));
                        }
                        modelLoader.archiveGame(gameState);
                        gameState.nextTurn();
                    }
                    else{
                        gameState.getCurrentPlayer().getSelectedPiece().setSelected(false);
                        gameState.getCurrentPlayer().setSelectedPiece(null);
                    }
                }
            }
        }
        // dont touch this line
        graphicalAgent.update(gameState);
        checkForEndGame();
    }

    public String getCellDetails(int x,int y){
        return "cell at "+x+","+y;
    }


    /**
     * check for endgame and specify winner
     * if player one is winner set winner variable to 1
     * if player two is winner set winner variable to 2
     * If the game is a draw set winner variable to 3
     */
    private void checkForEndGame()  {
        if (gameState.getTurn()==41) {
            // game ends
            modelLoader.archiveGame(gameState);
            int winner = 1;
            if (gameState.getPlayer2().getScore()>gameState.getPlayer1().getScore())winner=2;
            else{
                if (gameState.getPlayer2().getScore()==gameState.getPlayer1().getScore())winner=3;
            }


            // your code


            // dont touch it
            graphicalAgent.playerWin(winner);
            /* save players*/
            modelLoader.savePlayer(gameState.getPlayer1());
            modelLoader.savePlayer(gameState.getPlayer2());
            modelLoader.archive(gameState.getPlayer1(), gameState.getPlayer2());
            LogicalAgent logicalAgent = new LogicalAgent();
            logicalAgent.initialize();
        }
    }


    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who left clicks "dice button".) you should roll his/her dice
     * and update *****************
     */
    public void rollDice(int playerNumber) {
        if (gameState.getPlayer(playerNumber)== gameState.getCurrentPlayer()){
            if (gameState.getCurrentPlayer().getMoveLeft()==0){
                int diceNumber=gameState.getCurrentPlayer().getDice().roll();
                if (gameState.getCurrentPlayer().hasMove(gameState.getBoard(), diceNumber)){
                    gameState.getCurrentPlayer().setMoveLeft(diceNumber);
                    gameState.getCurrentPlayer().setDicePlayedThisTurn(true);
                }
                else{
                    gameState.nextTurn();
                }
            }
        }
        // dont touch this line
        graphicalAgent.update(gameState);
    }


    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who right clicks "dice button".) you should return the dice detail of that player.
     * you can use method "getDetails" in class "Dice"(not necessary, but recommended )
     */
    public String getDiceDetail(int playerNumber) {
        return gameState.getPlayer(playerNumber).getDice().getDetails();
    }
}

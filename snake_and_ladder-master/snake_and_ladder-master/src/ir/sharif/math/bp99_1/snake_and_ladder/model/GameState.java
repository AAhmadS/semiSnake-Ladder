package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

public class GameState {
    private final Board board;
    private final Player player1;
    private final Player player2;
    private int turn;

    public GameState(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        player1.setRival(player2);
        player2.setRival(player1);
        turn = 0;
    }

    public GameState(Board board, Player player1, Player player2,int turn) {
        this(board,player1,player2);
        this.turn = turn;
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getPlayer(int i) {
        if (i == 1) return player1;
        else if (i == 2) return player2;
        else return null;
    }

    public boolean isStarted() {
        return turn != 0;
    }

    public int getTurn() {
        return turn;
    }


    /**
     * return null if game is not started.
     * else return a player who's turn is now.
     */
    public Player getCurrentPlayer() {
        if (isStarted()){
            if (turn%2==1)return player1;
            else return player2;
        }
        return null;
    }

    public void startTheGame(){
        this.nextTurn();

        /*** putting pieces in their startingCells*/

        if (turn==1){
            for (Cell cell: board.getStartingCells().keySet()) {
                Color color=cell.getColor();
                int player=board.getStartingCells().get(cell);
                for (Piece piece:getPlayer(player).getPieces()) {
                    if (piece.getColor().equals(color)){
                        board.getCell(cell.getX(), cell.getY()).setPiece(piece);
                        getPlayer(player).getPieces().get(getPlayer(player).getPieces().indexOf(piece)).setCurrentCell(cell);
                    }
                }
            }
        }
    }


    /**
     * finish current player's turn and update some fields of this class;
     * you can use method "endTurn" in class "Player" (not necessary, but recommanded)
     */
    public void nextTurn() {
        if (isStarted())getCurrentPlayer().endTurn();
        turn++;
    }


    @Override
    public String toString() {
        return "GameState{" +
                "board=" + board +
                ", playerOne=" + player1 +
                ", playerTwo=" + player2 +
                ", turn=" + turn +
                '}';
    }
}

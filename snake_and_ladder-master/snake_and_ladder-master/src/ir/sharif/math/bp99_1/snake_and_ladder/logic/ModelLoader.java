package ir.sharif.math.bp99_1.snake_and_ladder.logic;

import ir.sharif.math.bp99_1.snake_and_ladder.model.*;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;
import ir.sharif.math.bp99_1.snake_and_ladder.util.Config;

import java.io.*;
import java.util.Scanner;

public class ModelLoader {
    private final File boardFile, playersDirectory, archiveFile;
    private String unfinished;


    /**
     * DO NOT CHANGE ANYTHING IN CONSTRUCTOR.
     */
    public ModelLoader() {
        boardFile = Config.getConfig("mainConfig").getProperty(File.class, "board");
        playersDirectory = Config.getConfig("mainConfig").getProperty(File.class, "playersDirectory");
        archiveFile = Config.getConfig("mainConfig").getProperty(File.class, "archive");
        if (!playersDirectory.exists()) playersDirectory.mkdirs();
        unfinished=playersDirectory.getPath()+"\\unfinishedGames";
    }


    /**
     * read file "boardFile" and craete a Board
     * <p>
     * you can use "BoardBuilder" class for this purpose.
     * <p>
     * pay attention add your codes in "try".
     *
     */
    public Board loadBord() {
        try {
            Scanner scanner = new Scanner(boardFile);
            BoardBuilder BB=new BoardBuilder(scanner);
            Board gameBoard=BB.build();
            // Code Here


            return gameBoard;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("could not find board file");
            System.exit(-2);
        }
        return null;
    }

    /**
     * load player.
     * if no such a player exist, create an account(file) for him/her.
     * <p>
     * you can use "savePlayer" method of this class for that purpose.
     * <p>
     * add your codes in "try" block .
     */
    public Player loadPlayer(String name, int playerNumber)  {
        try {
            File playerFile = getPlayerFile(name);
            if (playerFile==null){
                Player player=new Player(name,0,playersDirectory.listFiles().length,playerNumber);
                savePlayer(player);
                return player;
            }
            Scanner scanner = new Scanner(playerFile);
            scanner.next();int id= scanner.nextInt();
            scanner.close();
            return new Player(name,0, id,playerNumber);

            // Code in this part


        } catch (FileNotFoundException | IllegalArgumentException e) {
            e.printStackTrace();
            System.err.println("could not find player file");
            System.exit(-2);
        }
        return null;
    }

    /**
     * if player does not have a file, create one.
     * <p>
     * else update his/her file.
     * <p>
     * add your codes in "try" block .
     */
    public void savePlayer(Player player)  {
        try {
            // add your codes in this part
//            File file = getPlayerFile(player.getName());
            String path=playersDirectory.getPath()+"\\"+player.getName()+".txt";
            File playerFile=new File(path);
            if (!playerFile.exists()){
                playerFile.createNewFile();
                PrintStream printStream = new PrintStream(new FileOutputStream(playerFile));
                printStream.println(player.getName()+"'s_Id_is: "+player.getId());
                printStream.println();
                printStream.flush();printStream.close();
            }
            else{
                PrintStream printStream = new PrintStream(new FileOutputStream(playerFile,true));
                printStream.println("This game's rival: "+player.getRival().getName());
                printStream.println("This game's score: "+player.getScore());
                printStream.println("Rival's score: "+player.getRival().getScore());
                if (player.getRival().getScore()>player.getScore())printStream.println("Unfortunately "+player.getName()+" lost the game.");
                if (player.getRival().getScore()<player.getScore())printStream.println("!!!Congrats!!! "+player.getName()+" Won the game.");
                if (player.getRival().getScore()==player.getScore())printStream.println("Players tied.");
                printStream.println();
                printStream.flush();printStream.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("could not find player file");
            System.exit(-2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * give you a name (player name), search for its file.
     * return the file if exist.
     * return null if not.
     */
    private File getPlayerFile(String name) {
        String playerPath= playersDirectory.getPath()+"\\"+name+".txt";
        File playerFile=new File(playerPath);
        if (playerFile.exists())return playerFile;
        return null;
    }

    /**
     * at the end of the game save game details
     */
    public void archive(Player player1, Player player2){
        try {
            // add your codes in this part
            PrintStream printStream = new PrintStream(new FileOutputStream(archiveFile, true));
            printStream.println("In this game: ");
            printStream.println(player1.getName()+"'s_score= "+player1.getScore());
            printStream.println(player2.getName()+"'s_score= "+player2.getScore());
            if (player1.getScore()==player2.getScore()){
                printStream.println("And we have a tie here,Both players did well");
            }
            else {
                String winner= player1.getName();
                if (player2.getScore()>player1.getScore())winner= player2.getName();
                printStream.println("And the WINNER is "+winner);
            }
            printStream.println();
            printStream.flush();
            printStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * saving the game
     * */
    public void archiveGame(GameState gameState){
        if (gameState.getTurn()==41){
            String path=unfinished+"\\"+gameState.getPlayer1().getName()+"_vs_"+gameState.getPlayer2().getName();
            File file=new File(path);
            if (file.exists()){
                for (File subFile: file.listFiles()) {
                    subFile.delete();
                }
                file.delete();
            }
        }
        else{
            String path=unfinished+"\\"+gameState.getPlayer1().getName()+"_vs_"+gameState.getPlayer2().getName();
            File file =new File(path);
            if (!file.exists())file.mkdirs();
            archiveScores(gameState);
            archiveStates(gameState);
            archiveThief(gameState);
            archiveTurn(gameState);
            archiveStartingCells(gameState.getPlayer1(), gameState.getPlayer2());
        }
    }

    public void archiveScores(GameState gameState){
        String path=unfinished+"\\"+gameState.getPlayer1().getName()+"_vs_"+gameState.getPlayer2().getName()+"\\"+"scores"+".txt";
        File file=new File(path);
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            PrintStream printStream=new PrintStream(new FileOutputStream(file,false));
            printStream.println(gameState.getPlayer1().getScore()+" "+gameState.getPlayer2().getScore());
            printStream.flush();printStream.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            System.err.println("could not find board file");
            System.exit(-2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void archiveStates(GameState gameState){
        String path=unfinished+"\\"+gameState.getPlayer1().getName()+"_vs_"+gameState.getPlayer2().getName()+"\\"+"states"+".txt";
        File file=new File(path);
        try {
            if (!file.exists())file.createNewFile();
            PrintStream printStream=new PrintStream(new FileOutputStream(file,false));
            for (Piece piece: gameState.getPlayer1().getPieces()) {
                printStream.println(piece.getLivingState()+" "+piece.getAbilityState());
            }
            for (Piece piece: gameState.getPlayer2().getPieces()) {
                printStream.println(piece.getLivingState()+" "+piece.getAbilityState());
            }
            printStream.flush();printStream.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            System.err.println("could not find board file");
            System.exit(-2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void archiveThief(GameState gameState){
        String path=unfinished+"\\"+gameState.getPlayer1().getName()+"_vs_"+gameState.getPlayer2().getName()+"\\"+"thief"+".txt";
        File file=new File(path);
        try {
            if (!file.exists())file.createNewFile();
            PrintStream printStream=new PrintStream(new FileOutputStream(file,false));
            Prize prize1=gameState.getPlayer1().getThief().getPrize();
            Prize prize2=gameState.getPlayer2().getThief().getPrize();
            if (prize1==null){
                printStream.println("0 0 0");
            }
            else{
                printStream.println(prize1.getPoint()+" "+ prize1.getChance()+" "+ prize1.getDiceNumber());
            }
            if (prize2==null){
                printStream.println("0 0 0");
            }
            else{
                printStream.println(prize2.getPoint()+" "+ prize2.getChance()+" "+ prize2.getDiceNumber());
            }
            printStream.flush();printStream.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            System.err.println("could not find board file");
            System.exit(-2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void archiveTurn(GameState gameState){
        String path=unfinished+"\\"+gameState.getPlayer1().getName()+"_vs_"+gameState.getPlayer2().getName()+"\\"+"turn"+".txt";
        File file=new File(path);
        try {
            if (!file.exists())file.createNewFile();
            PrintStream printStream=new PrintStream(new FileOutputStream(file,false));
            printStream.println(gameState.getTurn()-1);
            printStream.flush();printStream.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            System.err.println("could not find board file");
            System.exit(-2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void archiveCells(Player player1,Player player2,Cell cell){
        try {
            String path=unfinished+"\\"+player1.getName()+"_vs_"+player2.getName()+"\\"+"cells"+".txt";
            File file=new File(path);
            if (file.exists()){
                PrintStream printStream=new PrintStream(new FileOutputStream(file,true));
                printStream.println(cell.getX()+" "+cell.getY()+" "+"BLACK");
                printStream.flush();printStream.close();
            }
            else{
                file.createNewFile();
                PrintStream printStream=new PrintStream(new FileOutputStream(file));
                printStream.println(cell.getX()+" "+cell.getY()+" "+"BLACK");
                printStream.flush();printStream.close();
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            System.err.println("could not find board file");
            System.exit(-2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void archivePrizes(Player player1,Player player2,Cell cell,Prize prize){
        try {
            String path=unfinished+"\\"+player1.getName()+"_vs_"+player2.getName()+"\\"+"prizes"+".txt";
            File file=new File(path);
            if (file.exists()){
                PrintStream printStream=new PrintStream(new FileOutputStream(file,true));
                if (prize==null)printStream.println(cell.getX()+" "+cell.getY()+" "+"0 0 0");
                else printStream.println(cell.getX()+" "+cell.getY()+" "+ prize.getPoint()+" "+ prize.getChance()+" "+ prize.getDiceNumber());
                printStream.flush();printStream.close();
            }
            else{
                file.createNewFile();
                PrintStream printStream=new PrintStream(new FileOutputStream(file));
                if (prize==null)printStream.println(cell.getX()+" "+cell.getY()+" "+"0 0 0");
                else printStream.println(cell.getX()+" "+cell.getY()+" "+ prize.getPoint()+" "+ prize.getChance()+" "+ prize.getDiceNumber());
                printStream.flush();printStream.close();
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            System.err.println("could not find board file");
            System.exit(-2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void archiveStartingCells(Player player1,Player player2){
        String path=unfinished+"\\"+player1.getName()+"_vs_"+player2.getName()+"\\"+"starting"+".txt";
        File file=new File(path);
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            PrintStream printStream=new PrintStream(new FileOutputStream(file,false));
            for (Piece piece: player1.getPieces()) {
                printStream.println(piece.getCurrentCell().getX()+" "+piece.getCurrentCell().getY()+" "+1);
            }
            for (Piece piece: player2.getPieces()) {
                printStream.println(piece.getCurrentCell().getX()+" "+piece.getCurrentCell().getY()+" "+2);
            }
            printStream.flush();printStream.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            System.err.println("could not find board file");
            System.exit(-2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUnfinished(){
        return unfinished;
    }

    /**here we try the loading process*/

    public void checkForUpdate(Board board, Player player1,Player player2,int[] turnGame){
        try {
            String path=unfinished+"\\"+player1.getName()+"_vs_"+player2.getName();
            File file=new File(path);
            if (file.exists()){

                /**applying cell changes*/

                String cellPath=path+"\\"+"cells"+".txt";
                File cellFile=new File(cellPath);
                if (cellFile.exists()){
                    Scanner scannerCell=new Scanner(cellFile);
                    applyOnCellChange(board,scannerCell);
                }

                /**applying prize changes*/

                String prizePath=path+"\\"+"prizes"+".txt";
                File prizeFile=new File(prizePath);
                if (prizeFile.exists()){
                    Scanner prizeScanner=new Scanner(prizeFile);
                    applyOnPrizeChange(board,prizeScanner);
                }

                /**applying scores*/

                String scorePath=path+"\\"+"scores"+".txt";
                File scoreFile=new File(scorePath);
                if (scoreFile.exists()){
                    Scanner scoreScanner=new Scanner(scoreFile);
                    applyOnScore(scoreScanner,player1,player2);
                }

                /**applying states*/

                String statePath=path+"\\"+"states"+".txt";
                File stateFile=new File(statePath);
                if (stateFile.exists()){
                    Scanner stateScanner=new Scanner(stateFile);
                    applyOnStates(stateScanner,player1,player2);
                }

                /**applying thief piece prizes*/

                String thiefPath=path+"\\"+"thief"+".txt";
                File thiefFile=new File(thiefPath);
                if (thiefFile.exists()){
                    Scanner thiefScanner=new Scanner(thiefFile);
                    applyOnThief(thiefScanner,player1,player2,board);
                }

                /**applying starting*/

                String startingPath=path+"\\"+"starting"+".txt";
                File startingFile=new File(startingPath);
                if (startingFile.exists()){
                    Scanner startingScanner=new Scanner(startingFile);
                    applyOnStarting(startingScanner,player1,player2,board);
                }

                /**applying turn*/

                String turnPath=path+"\\"+"turn"+".txt";
                File turnFile=new File(turnPath);
                if (turnFile.exists()){
                    Scanner turnScanner=new Scanner(turnFile);
                    turnGame[0]=turnScanner.nextInt();
                    turnScanner.close();
                }
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            System.err.println("could not find player file");
            System.exit(-2);
        }
    }

    public void applyOnCellChange(Board board,Scanner scanner){

        do {
            int x= scanner.nextInt();int y= scanner.nextInt();Color color=Color.valueOf(scanner.next());
            board.getCells().get((x-1)*16+y-1).setColor(color);
        }while (scanner.hasNext());
        scanner.close();
    }

    public void applyOnPrizeChange(Board board,Scanner scanner){
        do {
            int x= scanner.nextInt();int y= scanner.nextInt();int point= scanner.nextInt();int chance= scanner.nextInt();int diceNumber= scanner.nextInt();
            if (point==0){
                board.getCell(x,y).setPrize(null);
            }
            else{
                    board.getCell(x,y).setPrize(new Prize(board.getCell(x,y),point, chance,diceNumber));
            }
        } while (scanner.hasNext());

                scanner.close();
        }

    public void applyOnScore(Scanner scanner,Player player1,Player player2){
        int score1= scanner.nextInt();int score2= scanner.nextInt();
        player1.setScore(score1);player2.setScore(score2);
        scanner.close();
    }

    public void applyOnStates(Scanner scanner,Player player1,Player player2){
        for (Piece piece: player1.getPieces()) {
            piece.setLivingState(scanner.nextBoolean());
            piece.setAbilityState(scanner.nextBoolean());
        }
        for (Piece piece: player2.getPieces()) {
            piece.setLivingState(scanner.nextBoolean());
            piece.setAbilityState(scanner.nextBoolean());
        }
        scanner.close();
    }

    public void applyOnThief(Scanner scanner,Player player1,Player player2,Board board){
        int point= scanner.nextInt();int chance= scanner.nextInt();int diceNumber= scanner.nextInt();
        int point2= scanner.nextInt();int chance2= scanner.nextInt();int diceNumber2= scanner.nextInt();
        scanner.close();
        if (point!=0)player1.getThief().setPrize(new Prize(null,point,chance,diceNumber));
        else player1.getThief().setPrize(null);
        if (point2!=0)player2.getThief().setPrize(new Prize(null,point2,chance2,diceNumber2));
        else player2.getThief().setPrize(null);
    }

    public void applyOnStarting(Scanner scanner,Player player1,Player player2,Board board){
        int count=0;
        do {
            int x= scanner.nextInt();int y= scanner.nextInt();int playerNumber= scanner.nextInt();
            if (count<4){
                board.getCells().get((x-1)*16+y-1).setPiece(player1.getPieces().get(count));
                player1.getPieces().get(count).setCurrentCell(board.getCell(x,y));
            }
            else{
                board.getCells().get((x-1)*16+y-1).setPiece(player2.getPieces().get(count-4));
                player2.getPieces().get(count-4).setCurrentCell(board.getCell(x,y));
            }
            count++;
        }while (scanner.hasNext());
        scanner.close();
    }
}

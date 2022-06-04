package ir.sharif.math.bp99_1.snake_and_ladder.logic;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Board;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Wall;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class BoardBuilder {

    /*** we changed string to scanner lets see how it goes through*/

    private final Scanner boardscanner;
    public BoardBuilder(Scanner scan) {
        boardscanner=scan;
    }

    /**
     * give you a string in constructor.
     * <p>
     * you should read the string and create a board according to it.
     */

    public Board build() {

        /*** reading And Making cells*/

        boardscanner.next();
        int row= boardscanner.nextInt();
        int column =boardscanner.nextInt();
        boardscanner.next();
        Board mainB=new Board(row,column);
        for (int i=1;i<=row;i++){
            for (int j=1;j<=column;j++){
                Color cellcolor=Color.valueOf(boardscanner.next());
                mainB.addCells(new Cell(cellcolor,i,j));
            }
        }

        /*** entering startingCells*/

        boardscanner.next();
        int startingCellsCount=boardscanner.nextInt();
        boardscanner.next();
        startingCellsAdder (mainB,startingCellsCount,boardscanner);

        /*** entering adjacentCells and adjacentOpenCells*/

        for (int i=1;i<=row;i++){
            for (int j=1;j<=column;j++){
                List <Cell> adj=new ArrayList<>();
                if (mainB.getCell(i,j+1)!=null)adj.add(mainB.getCell(i,j+1));
                if (mainB.getCell(i,j-1)!=null)adj.add(mainB.getCell(i,j-1));
                if (mainB.getCell(i+1,j)!=null)adj.add(mainB.getCell(i+1,j));
                if (mainB.getCell(i-1,j)!=null)adj.add(mainB.getCell(i-1,j));
                mainB.getCell(i,j).getAdjacentCells().addAll(adj);
                mainB.getCell(i,j).getAdjacentOpenCells().addAll(adj);
            }
        }

        /*** entering walls*/

        boardscanner.next();
        int wallCounter=boardscanner.nextInt();
        boardscanner.next();
        wallAdder(mainB,wallCounter,boardscanner);

        /*** entering transmitters*/

        boardscanner.next();
        int transCounter= boardscanner.nextInt();
        boardscanner.next();
        transmitterAdder(mainB,transCounter,boardscanner);

        /*** entering prizes*/

        boardscanner.next();
        int prizeCounter= boardscanner.nextInt();
        boardscanner.next();
        prizeAdder(mainB,prizeCounter,boardscanner);
        boardscanner.nextLine();
        boardscanner.close();

        return mainB;
    }

    /*** adding voids for the main function*/

    public void startingCellsAdder(Board mainBoard,int count,Scanner scanner){
        for (int i=0;i<count;i++){
            int x=scanner.nextInt();int y=scanner.nextInt();int player= scanner.nextInt();
            mainBoard.addSarting(mainBoard.getCell(x,y),player);
        }
    }

    public void wallAdder(Board mainBoard,int count,Scanner scanner){
        for (int i=0;i<count;i++){
            int x1=scanner.nextInt();int y1=scanner.nextInt();int x2= scanner.nextInt();int y2=scanner.nextInt();
            mainBoard.addWalls(new Wall(mainBoard.getCell(x1,y1),mainBoard.getCell(x2,y2)));
            mainBoard.getCell(x1,y1).getAdjacentOpenCells().remove(mainBoard.getCell(x2,y2));
            mainBoard.getCell(x2,y2).getAdjacentOpenCells().remove(mainBoard.getCell(x1,y1));
        }
    }

    public void transmitterAdder(Board mainBoard,int count,Scanner scanner){
        for (int i=0;i<count;i++){
            int x1=scanner.nextInt();int y1=scanner.nextInt();int x2= scanner.nextInt();int y2=scanner.nextInt();String type=scanner.next();
            switch (type){
                case "S":
                    S1mple snakeS=new S1mple(mainBoard.getCell(x1,y1), mainBoard.getCell(x2,y2));
                    mainBoard.addTransmitters(snakeS);
                    snakeS.getFirstCell().setTransmitter(snakeS);
                    break;
                case "F":
                    Fatal snakeF=new Fatal(mainBoard.getCell(x1,y1), mainBoard.getCell(x2,y2));
                    mainBoard.addTransmitters(snakeF);
                    snakeF.getFirstCell().setTransmitter(snakeF);
                    break;
                case "M":
                    Magical snakeM=new Magical(mainBoard.getCell(x1,y1), mainBoard.getCell(x2,y2));
                    mainBoard.addTransmitters(snakeM);
                    snakeM.getFirstCell().setTransmitter(snakeM);
                    break;
                case "W":
                    Worm snakeW=new Worm(mainBoard.getCell(x1,y1), mainBoard.getCell(x2,y2), mainBoard.getCells());
                    mainBoard.addTransmitters(snakeW);
                    snakeW.getFirstCell().setTransmitter(snakeW);
            }

        }
    }

    public void prizeAdder(Board mainBoard,int count,Scanner scanner){
        for (int i=0;i<count;i++){
            int x=scanner.nextInt();int y=scanner.nextInt();int point= scanner.nextInt();int chance= scanner.nextInt();int diceNumber= scanner.nextInt();
            Prize prize=new Prize(mainBoard.getCell(x,y),point,chance,diceNumber);
            mainBoard.getCell(x,y).setPrize(prize);
            prize.getCell().setPrize(prize);
        }
    }

}

package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;

import java.util.*;

public class Board {
    private final List<Cell> cells;
    private final List<Transmitter> transmitters;
    private final List<Wall> walls;
    private final Map<Cell, Integer> startingCells;

    /*** adding rows and columns*/

    private final int rows;
    private final int columns;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        cells = new LinkedList<>();
        transmitters = new LinkedList<>();
        walls = new LinkedList<>();
        startingCells = new HashMap<>();
    }

    public List<Cell> getCells() {
        return cells;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public Map<Cell, Integer> getStartingCells() {
        return startingCells;
    }

    public List<Transmitter> getTransmitters() {
        return transmitters;
    }

    /*** adding some voids*/

    public void addCells(Cell newcell){
        this.cells.add(newcell);
    }

    public void addTransmitters(Transmitter newtrans){
        this.transmitters.add(newtrans);
    }

    public void addWalls(Wall newwall){
        this.walls.add(newwall);
    }

    public void addSarting(Cell SCell,Integer player){
        this.startingCells.put(SCell,player);
    }

    /**
     * give x,y , return a cell with that coordinates
     * return null if not exist.
     */

    public Cell getCell(int x, int y) {

        /*** this part is a not-perfected one */

        if (!(x<1||y<1||x>this.rows||y>this.columns)){
            return this.getCells().get((x-1)*columns+y-1);
        }
        return null;
    }
}

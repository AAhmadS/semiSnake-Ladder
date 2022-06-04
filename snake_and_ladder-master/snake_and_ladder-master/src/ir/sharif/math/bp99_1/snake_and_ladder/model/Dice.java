package ir.sharif.math.bp99_1.snake_and_ladder.model;


import java.util.Random;

public class Dice {

    /**
     * add some fields to store :
     * 1) chance of each dice number ( primary chance of each number, should be 1 )
     * currently our dice has 1 to 6.
     * 2) generate a random number
     * <p>
     * initialize these fields in constructor.
     */
    private Random dicerandom;
    private int totalChances;
    private int[] numberChances;
    private final Player dicePlayer;
    private int lastDiceRolled;

    public Dice(Player player) {
        this.dicePlayer=player;
        this.totalChances=6;
        numberChances=new int[7];
        for (int i=1;i<7;i++){
            numberChances[i]=1;
        }
        dicerandom=new Random();
        lastDiceRolled=0;
    }

    /**
     * create an algorithm generate a random number(between 1 to 6) according to the
     * chance of each dice number( you store them somewhere)
     * return the generated number
     */
    public int roll() {
        int dicenumber=dicerandom.nextInt(totalChances)+1;
        for(int i=1;i<7;i++){
            if (dicenumber>numberChances[i]){
                dicenumber-=numberChances[i];
            }
            else{
                return i;
            }
        }
        return 0;
    }

    /**
     * give a dice number and a chance, you should UPDATE chance
     * of that number.
     * pay attention chance of none of the numbers must not be negetive(it can be zero)
     */
    public void addChance(int number, int chance) {
        if (numberChances[number]+chance<0){
            totalChances-=numberChances[number];
            numberChances[number]=0;
        }
        else{
            if (numberChances[number]+chance>8){
                totalChances+=8-numberChances[number];
                numberChances[number]=8;
            }
            else{
                totalChances+=chance;
                numberChances[number]+=chance;
            }
        }
    }

    /**
     * for if the dice rolls on 2 ,resetting is a must do
     * */

    public void reset(){
        totalChances=6;
        for (int i=1;i<7;i++){
            numberChances[i]=1;
        }
    }

    /**
     * if 2 consecutive rolls , give the same number , adding chances is a must do
     * */

    public void checkForDouble(int diceNumber){
        if (lastDiceRolled==diceNumber){
            addChance(diceNumber,1);
            lastDiceRolled=0;
        }
        else{
            lastDiceRolled=diceNumber;
        }
    }

    /**
     * you should return the details of the dice number.
     * sth like:
     * "1 with #1 chance.
     * 2 with #2 chance.
     * 3 with #3 chance
     * .
     * .
     * . "
     * where #i is the chance of number i.
     */
    public String getDetails() {
        return "1 with "+numberChances[1]+" chance.\n" +
                "2 with "+numberChances[2]+" chance.\n" +
                "3 with "+numberChances[3]+" chance.\n" +
                "4 with "+numberChances[4]+" chance.\n" +
                "5 with "+numberChances[5]+" chance.\n" +
                "6 with "+numberChances[6]+" chance.\n";
    }
}

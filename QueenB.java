import java.util.ArrayList;

public class QueenB extends Bee {

    private int eggLayingRate; // Number of eggs laid per time unit
    private int timeElapsed;

    public QueenB(String name, int nectar, int honey, Boolean isAlive, int eggLayingRate, int timeElapsed) {
        super(name,nectar,honey,isAlive);
        this.eggLayingRate = eggLayingRate;
        this.timeElapsed = 0;
    }

    public ArrayList<Bee> layEggs() {
        ArrayList<Bee> bees = new ArrayList<>();

        if (eggLayingRate > 0) {
            int numEggs = timeElapsed / eggLayingRate;
            if (numEggs > 0) {
                System.out.println("Queen " + getName() + " is laying " + numEggs + " eggs.");
                for (int i = 0; i < numEggs; i++) {
                    bees.add(new Bee("NewBee" + i, 0, 0, true));
                }
                timeElapsed = 0; //time is set back to zero after laying the eggs
            }else{
                System.out.println("Not enough time has Passedvfor the Queen to lay eggs.");
            }
        } else {
            System.out.println("Error: Egg-laying rate cannot be zero.");
        }
    
        return bees;
    }

    public void passTime(int timeUnits) {
        timeElapsed += timeUnits;

    }

    public void issueCommand(String command) {
        System.out.println("Queen " + getName() + " issued the command: " + command);
    }

    public String toString(){
         return "Queen Bee: " + "\nname: " + getName() + "\nnectar reserves: " + getNectarReserves() + "\nhoney made: " + getHoney() +
          "\nNumber of eggs the queen can lay at a given time: " + eggLayingRate 
         +"\nTime passed since the Queen has given birth: " +timeElapsed +
         "\nEggs Laid by the queen: " + layEggs();
    }

    public int getEggLayingRate(){
        return eggLayingRate;
    }

    public int getTimeElapsed(){
        return timeElapsed;
    }
}
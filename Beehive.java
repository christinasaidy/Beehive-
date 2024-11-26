import java.util.ArrayList;
import java.util.Date;

public class Beehive {
    private QueenB queen; //hon
    private ArrayList<Bee> bees = new ArrayList<>();


    public Beehive(QueenB queen) {
       this.queen = queen;
       bees = new ArrayList<>();
    }

    public void removeBee(Bee beeToRemove) {
        bees.remove(beeToRemove);
    }

    public void addBee(Bee...B) {
        for(Bee bee: B){
            if(bee.getAliveStatus() == true){
                bees.add(bee);
            }else{
                System.out.println(bee.getName() +"is not alive and could not be added to the hive");
            }

        }
    }

    public ArrayList<Bee> getBees() {
        return bees;
    }


    public void sendBeesToCollectNectar( Plant plant) {
        for (Bee bee : bees) {
            bee.collectNectar(plant);
        }
        System.out.println("Bees sent to collect nectar from the plant.");
    }

    public void harvestHoney() {
        int totalHoney = 0;
        for (Bee bee : bees) {
            totalHoney += bee.produceHoney();
        }
        System.out.println("Honey harvested from the hive: " + totalHoney);
    }

    public void checkHiveStatus() {
        System.out.println("Queen " + queen.getName() + " is checking the status of the hive:");
        System.out.println("Number of bees: " + bees.size());

    }

    public void inspectHive() { // remove arraylist men fawee and add lal methods the variable list parameter.

        System.out.println("Queen " + queen.getName() + " is inspecting the hive:");
        for (Bee bee : bees) {
            System.out.println("Bee: " + bee.getName());
        }
    }

    public String BeehiveNewsStation() {
        StringBuilder news = new StringBuilder();
        Date currentDate = new Date();
        if (!queen.getAliveStatus()) {
            news.append("On Today's Unfortunate News:\n");
            news.append("The Queen Bee ").append(queen.getName()).append(" has died, the bees are panicking! Quickly replace her\n");
            news.append("Date: ").append(currentDate).append("\n");
        } else {
            news.append("On Today's Good News:\n");
            news.append("The Queen Bee ").append(queen.getName()).append(" is alive and well\n");
            news.append("New eggs were laid and honey was made. The economy is blooming!\n");
            news.append("Everyone is productive and Happy. Horray\n");
            news.append("Date: ").append(currentDate).append("\n");
        }
        return news.toString();
    }
    
    
    

        public void setnewQueenB(QueenB newqueen){
            if(queen.getAliveStatus()== false && newqueen.getAliveStatus()== true){
                queen = newqueen;
                System.out.println("The beehive has a new Queen Bee: " + newqueen.getName() +"!");

            }else{

            System.out.println("Unable to replace the Queen at this time.");

            }
        
    }

        public QueenB getQueen(){
            return queen;
        }

        public void setQueen(QueenB bee){
            this.queen = bee;
        }

        public String toString() {
            String hiveString = "Beehive Information:\n";
            hiveString += "Queen: " + (queen != null ? queen.toString() : "No Queen") + "\n";
            hiveString += "Bees:\n";
            for (Bee bee : bees) {
                hiveString += bee.toString() + "\n";
            }
            return hiveString;
        }


}
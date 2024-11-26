public class Plant {
    private int pollen;
    private int nectar;

    public Plant(int initialPollen) {
        this.pollen = initialPollen;
    }

    public int getPollen() {
        return pollen;
    }

    public void gatherPollen() {
        pollen -= 10;
    }

    public int NectarTaken() {
        
        int collectedNectar = Math.min(nectar, 10); 
        nectar -= collectedNectar; 
        return collectedNectar;
    }

    public String toString(){
        return "PLant: "  + "\npollen: " + pollen + "\nectar: " + nectar;
    }


}
class Bee {
    private String name;
    private int nectarReserves;
    private int honey;
    private Boolean isAlive;
    private int totalDamageTaken;


    public Bee(String name, int nectar, int honey, Boolean isAlive) {
        this.name = name;
        nectarReserves = nectar;
        this.honey = honey;
        this.isAlive = isAlive;
        totalDamageTaken =0;
    }

    public void gatherPollen(Plant plant) {
 
        if(getAliveStatus()==true){
        plant.gatherPollen();
        System.out.println(name + " gathered pollen from the plant. Remaining pollen: " + plant.getPollen());
        }
    }

    public int produceHoney() {

        if(getAliveStatus()== true){
        honey +=10;
        int honeyproduced = honey;
        System.out.println(name + " produced " + honey + " units of honey.");
        return honeyproduced;
        }else{
            System.out.println("Dead bees dont produce honey");
            return 0;
        }
    }


    public String getName() {
        return name;
    }

    public void mate(Bee partner) {
       
        System.out.println(name + " mated with " + partner.name);
    }

    public void flyTo(String location) {
        System.out.println(getName() + " is flying to " + location + ".");
       
    }

    public void collectNectar(Plant plant) {
    
        int collectedNectar = plant.NectarTaken();
        System.out.println(name + " collected " + collectedNectar + " nectar from the plant.");
        nectarReserves += collectedNectar;
    }

    public void takeDamage(int damage) {
        totalDamageTaken += damage;
        if (totalDamageTaken >= 100) {
            System.out.println(name + " has taken critical damage and is removed from the hive.");
            setAliveStatus(false);
        }
    }

    public int getTotalDamageTaken(){
        return totalDamageTaken;
    }

    public Boolean getAliveStatus(){
        return isAlive;
    }
    public void setAliveStatus(Boolean isAlive){
        this.isAlive = isAlive;
        
    }

    public int getNectarReserves() {
        return nectarReserves;
    }

    public void setNectarReserves(int nectar) {
        nectarReserves = nectar;
    }

    public int getHoney(){
        return honey;
    }

    public String toString(){
        return  "\nName: " + getName() + "\nNectar reserves: " + getNectarReserves() + "\nHoney made: " + getHoney()  +
        "\nDamage Done : " + getTotalDamageTaken() ;
   };
   }



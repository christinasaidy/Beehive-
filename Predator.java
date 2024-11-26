public class Predator {
    private String name;
    private int dangerLevel; 

    public Predator(String name, int dangerLevel) {
        this.name = name;
        this.dangerLevel = dangerLevel;
    }

    public void attack(Bee bee) {
        System.out.println(name + " attacked " + bee.getName() + "!");
        int damage = calculateDamage();
        bee.takeDamage(damage);
    }

    public int calculateDamage() {
    
        return dangerLevel * 10;
    }

    public String getName() {
        return name;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }

    public String toString(){
        return "Predator: " + "\nName: " + getName() + "\nDanger Level: " + dangerLevel;
}

}

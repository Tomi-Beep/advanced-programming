package exercises.object_oriented_paradigm.aliens;

public abstract class Alien {
    private int health;
    private String name;

    public Alien(String name, int health) {
        this.name = name;
        this.health = health;
    }

    public abstract int getDamage();
}

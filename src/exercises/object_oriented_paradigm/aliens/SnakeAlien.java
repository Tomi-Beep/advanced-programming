package exercises.object_oriented_paradigm.aliens;

public class SnakeAlien extends Alien {

    public SnakeAlien(String name, int health) {
        super(name, health);
    }

    @Override
    public int getDamage() {
        return 10;
    }
}

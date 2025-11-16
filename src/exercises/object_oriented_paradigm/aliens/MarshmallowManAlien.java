package exercises.object_oriented_paradigm.aliens;

public class MarshmallowManAlien extends Alien{
    public MarshmallowManAlien(String name, int health) {
        super(name, health);
    }

    @Override
    public int getDamage() {
        return 1;
    }
}

package exercises.object_oriented_paradigm.aliens;

public class OgreAlien extends Alien {
    public OgreAlien(String name, int health) {
        super(name, health);
    }

    @Override
    public int getDamage() {
        return 6;
    }
}

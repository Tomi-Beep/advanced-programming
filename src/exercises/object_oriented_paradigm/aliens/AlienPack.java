package exercises.object_oriented_paradigm.aliens;

public class AlienPack {
    private Alien[] aliens;

    public AlienPack(int numAliens) {
        this.aliens = new Alien[numAliens];
    }

    public Alien[] getAliens() {
        return aliens;
    }

    public int calculateDamage() {
        int damage = 0;
        for (Alien alien : aliens) {
            damage += alien.getDamage();
        }
        return damage;
    }
}

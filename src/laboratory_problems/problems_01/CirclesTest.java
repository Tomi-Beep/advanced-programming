package laboratory_problems.problems_01;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

enum TYPE {
    POINT,
    CIRCLE
}

enum DIRECTION {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

interface Movable {
    void moveUp() throws ObjectCanNotBeMovedException;
    void moveDown() throws ObjectCanNotBeMovedException;
    void moveRight() throws ObjectCanNotBeMovedException;
    void moveLeft() throws ObjectCanNotBeMovedException;
    int getCurrentXPosition();
    int getCurrentYPosition();
}

class ObjectCanNotBeMovedException extends Exception {
    public ObjectCanNotBeMovedException(String message) {
        super(message);
    }
}

class MovableObjectNotFittableException extends Exception {
    public MovableObjectNotFittableException(String message) {
        super(message);
    }
}

class MovablePoint implements Movable {
    private int x, y;
    private int xSpeed, ySpeed;

    public MovablePoint(int x, int y, int xSpeed, int ySpeed) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        if (y + ySpeed > MovablesCollection.getyMax()) {
            throw new ObjectCanNotBeMovedException("Point (" + x + "," + (y + ySpeed) + ") is out of bounds");
        }
        y += ySpeed;
    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException {
        if (y - ySpeed < 0) {
            throw new ObjectCanNotBeMovedException("Point (" + x + "," + (y - ySpeed) + ") is out of bounds");
        }
        y -= ySpeed;
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        if (x + xSpeed > MovablesCollection.getxMax()) {
            throw new ObjectCanNotBeMovedException("Point (" + (x + xSpeed) + "," + y + ") is out of bounds");
        }
        x += xSpeed;
    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException {
        if (x - xSpeed < 0) {
            throw new ObjectCanNotBeMovedException("Point (" + (x - xSpeed) + "," + y + ") is out of bounds");
        }
        x -= xSpeed;
    }

    @Override
    public int getCurrentXPosition() { return x; }

    @Override
    public int getCurrentYPosition() { return y; }

    @Override
    public String toString() {
        return "Movable point with coordinates (" + x + "," + y + ")";
    }
}

class MovableCircle implements Movable {
    private int radius;
    private MovablePoint center;

    public MovableCircle(int radius, MovablePoint center) {
        this.radius = radius;
        this.center = center;
    }

    @Override
    public void moveUp() throws ObjectCanNotBeMovedException { center.moveUp(); }
    @Override
    public void moveDown() throws ObjectCanNotBeMovedException { center.moveDown(); }
    @Override
    public void moveRight() throws ObjectCanNotBeMovedException { center.moveRight(); }
    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException { center.moveLeft(); }

    @Override
    public int getCurrentXPosition() { return center.getCurrentXPosition(); }
    @Override
    public int getCurrentYPosition() { return center.getCurrentYPosition(); }
    public int getRadius() { return radius; }

    // Normal toString for collection listing
    @Override
    public String toString() {
        return "Movable circle with center coordinates (" + center.getCurrentXPosition() + "," +
                center.getCurrentYPosition() + ") and radius " + radius;
    }

    // Exception string (when cannot be fitted)
    public String toFittingExceptionString() {
        return "Movable circle with center (" + center.getCurrentXPosition() + "," +
                center.getCurrentYPosition() + ") and radius " + radius + " can not be fitted into the collection";
    }
}

class MovablesCollection {
    private List<Movable> movable = new ArrayList<>();
    private static int X_MAX;
    private static int Y_MAX;

    public MovablesCollection(int x_MAX, int y_MAX) {
        X_MAX = x_MAX;
        Y_MAX = y_MAX;
    }

    public static int getxMax() { return X_MAX; }
    public static void setxMax(int xMax) { X_MAX = xMax; }
    public static int getyMax() { return Y_MAX; }
    public static void setyMax(int yMax) { Y_MAX = yMax; }

    public void addMovableObject(Movable m) {
        try {
            if (m instanceof MovablePoint) {
                if (m.getCurrentXPosition() < 0 || m.getCurrentXPosition() > X_MAX ||
                        m.getCurrentYPosition() < 0 || m.getCurrentYPosition() > Y_MAX) {
                    throw new MovableObjectNotFittableException(m.toString() + " can not be fitted into the collection");
                }
            } else if (m instanceof MovableCircle) {
                MovableCircle c = (MovableCircle) m;
                int cx = c.getCurrentXPosition();
                int cy = c.getCurrentYPosition();
                int r = c.getRadius();
                if (cx - r < 0 || cx + r > X_MAX || cy - r < 0 || cy + r > Y_MAX) {
                    throw new MovableObjectNotFittableException(c.toFittingExceptionString());
                }
            }
            movable.add(m);
        } catch (MovableObjectNotFittableException e) {
            System.out.println(e.getMessage());
        }
    }

    public void moveObjectsFromTypeWithDirection(TYPE type, DIRECTION direction) {
        for (Movable m : movable) {
            boolean matchType = (type == TYPE.POINT && m instanceof MovablePoint) ||
                    (type == TYPE.CIRCLE && m instanceof MovableCircle);
            if (matchType) {
                try {
                    switch (direction) {
                        case UP: m.moveUp(); break;
                        case DOWN: m.moveDown(); break;
                        case LEFT: m.moveLeft(); break;
                        case RIGHT: m.moveRight(); break;
                    }
                } catch (ObjectCanNotBeMovedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Collection of movable objects with size ").append(movable.size()).append(":\n");
        for (Movable m : movable) {
            sb.append(m.toString()).append("\n");
        }
        return sb.toString();
    }
}

public class CirclesTest {
    public static void main(String[] args) {
        System.out.println("===COLLECTION CONSTRUCTOR AND ADD METHOD TEST===");
        MovablesCollection collection = new MovablesCollection(100, 100);
        Scanner sc = new Scanner(System.in);
        int samples = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < samples; i++) {
            String[] parts = sc.nextLine().split(" ");
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int xSpeed = Integer.parseInt(parts[3]);
            int ySpeed = Integer.parseInt(parts[4]);
            if (Integer.parseInt(parts[0]) == 0) {
                collection.addMovableObject(new MovablePoint(x, y, xSpeed, ySpeed));
            } else {
                int radius = Integer.parseInt(parts[5]);
                collection.addMovableObject(new MovableCircle(radius, new MovablePoint(x, y, xSpeed, ySpeed)));
            }
        }
        System.out.println(collection.toString());

        System.out.println("MOVE POINTS TO THE LEFT");
        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.LEFT);
        System.out.println(collection.toString());

        System.out.println("MOVE CIRCLES DOWN");
        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.DOWN);
        System.out.println(collection.toString());

        System.out.println("CHANGE X_MAX AND Y_MAX");
        MovablesCollection.setxMax(90);
        MovablesCollection.setyMax(90);

        System.out.println("MOVE POINTS TO THE RIGHT");
        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.RIGHT);
        System.out.println(collection.toString());

        System.out.println("MOVE CIRCLES UP");
        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.UP);
        System.out.println(collection.toString());
    }
}

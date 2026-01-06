package exam_problems.p06_streams_polymorphism;

import java.util.*;
import java.util.stream.Collectors;

public class ShapesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Canvas canvas = new Canvas();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            int type = Integer.parseInt(parts[0]);
            String id = parts[1];
            if (type == 1) {
                Color color = Color.valueOf(parts[2]);
                float radius = Float.parseFloat(parts[3]);
                canvas.add(id, color, radius);
            } else if (type == 2) {
                Color color = Color.valueOf(parts[2]);
                float width = Float.parseFloat(parts[3]);
                float height = Float.parseFloat(parts[4]);
                canvas.add(id, color, width, height);
            } else if (type == 3) {
                float scaleFactor = Float.parseFloat(parts[2]);
                System.out.println("ORIGNAL:");
                System.out.print(canvas);
                canvas.scale(id, scaleFactor);
                System.out.printf("AFTER SCALING: %s %.2f\n", id, scaleFactor);
                System.out.print(canvas);
            }

        }
    }
}

enum Color {
    RED, GREEN, BLUE
}

interface Scalable {
    void scale(float scaleFactor);
}

interface Stackable {
    float weight();
}

abstract class Shape implements Scalable, Stackable {
    private final String id;
    private final Color color;

    public Shape(String id, Color color) {
        this.id = id;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return String.format("%s: %-5s %-10s %10.2f", getType(), id, color, weight());
    }
}

class Circle extends Shape {
    float radius;

    public Circle(String id, Color color, float radius) {
        super(id, color);
        this.radius = radius;
    }

    @Override
    public void scale(float scaleFactor) {
        radius *= scaleFactor;
    }

    @Override
    public float weight() {
        return (float) (radius * radius * Math.PI);
    }

    @Override
    public String getType() {
        return "C";
    }
}

class Rectangle extends Shape {
    float width;
    float height;

    public Rectangle(String id, Color color, float width, float height) {
        super(id, color);
        this.width = width;
        this.height = height;
    }

    @Override
    public void scale(float scaleFactor) {
        width *= scaleFactor;
        height *= scaleFactor;
    }

    @Override
    public float weight() {
        return width * height;
    }

    @Override
    public String getType() {
        return "R";
    }
}

class Canvas {
    private final Set<Shape> shapes;

    public Canvas() {
        shapes = new TreeSet<>(Comparator.comparingDouble(Shape::weight)
                .reversed()
                .thenComparing(Shape::getId));
    }

    void add(String id, Color color, float radius) {
        shapes.add(new Circle(id, color, radius));
    }

    void add(String id, Color color, float width, float height) {
        shapes.add(new Rectangle(id, color, width, height));
    }

    void scale(String id, float scaleFactor) {
        shapes.stream()
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .ifPresent(s -> {
                    shapes.remove(s);
                    s.scale(scaleFactor);
                    shapes.add(s);
                });
    }

    @Override
    public String toString() {
        return shapes.stream()
                .map(Shape::toString)
                .collect(Collectors.joining("\n", "", "\n"));
    }
}

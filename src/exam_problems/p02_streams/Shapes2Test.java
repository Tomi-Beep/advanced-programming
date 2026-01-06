package exam_problems.p02_streams;

import java.io.*;
import java.util.*;

class InvalidCanvasException extends Exception {
    public InvalidCanvasException(String message) {
        super(message);
    }
}

abstract class Shape {
    protected int size;

    public Shape(int size) {
        this.size = size;
    }

    public abstract double calculateArea();

    public static Shape createShape(String type, int size) {
        return type.equals("S") ? new Square(size) : new Circle(size);
    }
}

class Circle extends Shape {
    public Circle(int size) {
        super(size);
    }

    @Override
    public double calculateArea() {
        return size * size * Math.PI;
    }
}

class Square extends Shape {
    public Square(int size) {
        super(size);
    }

    @Override
    public double calculateArea() {
        return size * size;
    }
}

class Canvas {
    private final String id;
    private final List<Shape> shapes;
    private final int totalCircles;
    private final int totalSquares;
    private final double totalArea;
    private final double minArea;
    private final double maxArea;
    private final double averageArea;

    public Canvas(String canvasId, List<String> information, double maxAllowedArea) throws InvalidCanvasException {
        this.id = canvasId;
        this.shapes = parseShapes(information, maxAllowedArea, canvasId);

        DoubleSummaryStatistics stats = shapes.stream()
                .mapToDouble(Shape::calculateArea)
                .summaryStatistics();

        this.totalCircles = (int) shapes.stream().filter(s -> s instanceof Circle).count();
        this.totalSquares = (int) shapes.stream().filter(s -> s instanceof Square).count();
        this.totalArea = stats.getSum();
        this.minArea = shapes.isEmpty() ? 0.0 : stats.getMin();
        this.maxArea = shapes.isEmpty() ? 0.0 : stats.getMax();
        this.averageArea = stats.getAverage();
    }

    private List<Shape> parseShapes(List<String> info, double maxArea, String canvasId) throws InvalidCanvasException {
        List<Shape> result = new ArrayList<>();

        for (int i = 0; i < info.size(); i += 2) {
            Shape shape = Shape.createShape(info.get(i), Integer.parseInt(info.get(i + 1)));

            if (shape.calculateArea() > maxArea) {
                throw new InvalidCanvasException(
                        String.format("Canvas %s has a shape with area larger than %.2f", canvasId, maxArea)
                );
            }
            result.add(shape);
        }
        return result;
    }

    public double getTotalArea() {
        return totalArea;
    }

    @Override
    public String toString() {
        return String.format("%s %d %d %d %.2f %.2f %.2f",
                id, shapes.size(), totalCircles, totalSquares,
                minArea, maxArea, averageArea);
    }
}

class ShapesApplication {
    private final double maxArea;
    private final List<Canvas> canvases;

    public ShapesApplication(double maxArea) {
        this.maxArea = maxArea;
        this.canvases = new ArrayList<>();
    }

    void readCanvases(InputStream inputStream) {
        new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .forEach(this::processLine);
    }

    private void processLine(String line) {
        try {
            canvases.add(createCanvas(line));
        } catch (InvalidCanvasException e) {
            System.out.println(e.getMessage());
        }
    }

    private Canvas createCanvas(String line) throws InvalidCanvasException {
        String[] tokens = line.split("\\s+");
        List<String> shapeInfo = Arrays.asList(tokens).subList(1, tokens.length);
        return new Canvas(tokens[0], shapeInfo, maxArea);
    }

    void printCanvases(OutputStream os) {
        PrintWriter writer = new PrintWriter(os);

        canvases.stream()
                .sorted(Comparator.comparingDouble(Canvas::getTotalArea).reversed())
                .forEach(writer::println);

        writer.flush();
    }
}

public class Shapes2Test {
    public static void main(String[] args) {
        ShapesApplication shapesApplication = new ShapesApplication(10000);

        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
        shapesApplication.readCanvases(System.in);

        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
        shapesApplication.printCanvases(System.out);
    }
}
package exam_problems.p01_streams;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Canvas {
    private final String id;
    private final List<Integer> squares;
    private final int totalSquares;
    private final int totalPerimeter;

    public Canvas(String id, List<Integer> squares) {
        this.id = id;
        this.squares = Collections.unmodifiableList(new ArrayList<>(squares));
        this.totalSquares = squares.size();
        this.totalPerimeter = squares.stream().mapToInt(size -> 4 * size).sum();
    }

    public String getId() { return id; }
    public int getTotalSquares() { return totalSquares; }
    public int getTotalPerimeter() { return totalPerimeter; }

    @Override
    public String toString() {
        return String.format("%s %d %d", id, totalSquares, totalPerimeter);
    }
}

class ShapesApplication {
    private final List<Canvas> canvases;

    public ShapesApplication() {
        canvases = new ArrayList<>();
    }

    public int readCanvases(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .map(this::parseCanvas)
                    .filter(Objects::nonNull)
                    .mapToInt(canvas -> {
                        canvases.add(canvas);
                        return canvas.getTotalSquares();
                    })
                    .sum();
        }
    }

    private Canvas parseCanvas(String line) {
        String[] tokens = line.split("\\s+");
        if (tokens.length < 2) return null;

        String id = tokens[0];
        List<Integer> squares = Arrays.stream(tokens, 1, tokens.length)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        return new Canvas(id, squares);
    }

    public void printLargestCanvasTo(OutputStream outputStream) {
        canvases.stream()
                .max(Comparator.comparingInt(Canvas::getTotalPerimeter))
                .ifPresent(canvas -> {
                    try (PrintWriter writer = new PrintWriter(outputStream, true)) {
                        writer.println(canvas);
                    }
                });
    }
}

public class Shapes1Test {

    public static void main(String[] args) throws IOException {
        ShapesApplication shapesApplication = new ShapesApplication();

        System.out.println("===READING SQUARES FROM INPUT STREAM===");
        System.out.println(shapesApplication.readCanvases(System.in));
        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
        shapesApplication.printLargestCanvasTo(System.out);

    }
}
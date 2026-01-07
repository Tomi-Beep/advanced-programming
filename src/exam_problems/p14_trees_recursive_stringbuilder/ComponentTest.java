package exam_problems.p14_trees_recursive_stringbuilder;

import java.util.*;

public class ComponentTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Window window = new Window(name);
        Component prev = null;
        while (true) {
            try {
                int what = scanner.nextInt();
                scanner.nextLine();
                if (what == 0) {
                    int position = scanner.nextInt();
                    window.addComponent(position, prev);
                } else if (what == 1) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev = component;
                } else if (what == 2) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                    prev = component;
                } else if (what == 3) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                } else if (what == 4) {
                    break;
                }

            } catch (InvalidPositionException e) {
                System.out.println(e.getMessage());
            }
            scanner.nextLine();
        }

        System.out.println("=== ORIGINAL WINDOW ===");
        System.out.println(window);
        int weight = scanner.nextInt();
        scanner.nextLine();
        String color = scanner.nextLine();
        window.changeColor(weight, color);
        System.out.println(String.format("=== CHANGED COLOR (%d, %s) ===", weight, color));
        System.out.println(window);
        int pos1 = scanner.nextInt();
        int pos2 = scanner.nextInt();
        System.out.println(String.format("=== SWITCHED COMPONENTS %d <-> %d ===", pos1, pos2));
        window.swichComponents(pos1, pos2);
        System.out.println(window);
    }
}

class ComponentComparator implements Comparator<Component> {

    @Override
    public int compare(Component o1, Component o2) {
        if (o1.getWeight() == o2.getWeight()) {
            return o1.getColor().compareTo(o2.getColor());
        }
        return Integer.compare(o1.getWeight(), o2.getWeight());
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}

class Component {
    private String color;
    private final int weight;
    private final Set<Component> insideComponents;

    public String getColor() {
        return color;
    }

    public int getWeight() {
        return weight;
    }

    public Set<Component> getInsideComponents() {
        return insideComponents;
    }

    Component(String color, int weight) {
        this.color = color;
        this.weight = weight;
        insideComponents = new TreeSet<>(new ComponentComparator());
    }

    void addComponent(Component component) {
        insideComponents.add(component);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("---%d:%s", weight, color));
        for (Component c : insideComponents) {
            String childString = c.toString();
            String[] tokens = childString.split("\n");
            for (String token : tokens) {
                sb.append("\n---").append(token);
            }
        }
        return sb.toString();
    }

    void changeColor(int weight, String color) {
        if (this.weight < weight)
            this.color = color;
        for (Component c : insideComponents) {
            c.changeColor(weight, color);
        }
    }
}

class InvalidPositionException extends RuntimeException {
    public InvalidPositionException(int pos) {
        super(String.format("Invalid position %d, alredy taken!", pos));
    }
}

class Window {
    private final String name;
    private final Map<Integer, Component> components;

    public Window(String name) {
        this.name = name;
        components = new TreeMap<>();
    }

    void addComponent(int position, Component component) {
        if (components.containsKey(position))
            throw new InvalidPositionException(position);
        else components.put(position, component);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("WINDOW %s", name));
        for (Integer i : components.keySet()) {
            Component c = components.get(i);
            sb.append(String.format("\n%d:%d:%s", i, c.getWeight(), c.getColor()));
            for (Component iC : c.getInsideComponents()) {
                sb.append("\n").append(iC);
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    void changeColor(int weight, String color) {
        for (Component c : components.values()) {
            c.changeColor(weight, color);
        }
    }

    void swichComponents(int pos1, int pos2) {
        Component temp = components.get(pos1);
        components.put(pos1, components.get(pos2));
        components.put(pos2, temp);
    }

}
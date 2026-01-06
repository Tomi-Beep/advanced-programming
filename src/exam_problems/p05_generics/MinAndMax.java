package exam_problems.p05_generics;

import java.util.Scanner;

class MinMax<T extends Comparable<T>> {
    private T min;
    private T max;
    private int countMin;
    private int countMax;
    private int totalUpdates;

    public MinMax() {
        this.min = null;
        this.max = null;
        this.countMin = 0;
        this.countMax = 0;
        this.totalUpdates = 0;
    }

    public void update(T element) {
        if (totalUpdates == 0) {
            min = element;
            max = element;
            countMin = 1;
            countMax = 1;
        } else {
            if (element.compareTo(min) < 0) {
                min = element;
                countMin = 1;
            } else if (element.compareTo(min) == 0) {
                countMin++;
            }

            if (element.compareTo(max) > 0) {
                max = element;
                countMax = 1;
            } else if (element.compareTo(max) == 0) {
                countMax++;
            }
        }
        totalUpdates++;
    }

    public T max() {
        return max;
    }

    public T min() {
        return min;
    }

    @Override
    public String toString() {

        return min + " " + max + " " + (totalUpdates - (countMin + countMax)) + "\n\n";
    }
}

public class MinAndMax {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;

        int n = scanner.nextInt();
        MinMax<String> strings = new MinMax<>();
        for (int i = 0; i < n; ++i) {
            String s = scanner.next();
            strings.update(s);
        }
        System.out.print(strings);

        MinMax<Integer> ints = new MinMax<>();
        for (int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            ints.update(x);
        }
        System.out.print(ints);
        scanner.close();
    }
}
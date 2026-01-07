package exam_problems.p10_generics;

import java.util.*;

public class TripleTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf("%.2f\n", tInt.max());
        System.out.printf("%.2f\n", tInt.avarage());
        tInt.sort();
        System.out.println(tInt);
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf("%.2f\n", tFloat.max());
        System.out.printf("%.2f\n", tFloat.avarage());
        tFloat.sort();
        System.out.println(tFloat);
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf("%.2f\n", tDouble.max());
        System.out.printf("%.2f\n", tDouble.avarage());
        tDouble.sort();
        System.out.println(tDouble);
    }
}

class Triple<T extends Number> {
    private final T[] numbers;

    @SuppressWarnings("unchecked")
    public Triple(T a, T b, T c) {
        numbers = (T[]) new Number[3];
        numbers[0] = a;
        numbers[1] = b;
        numbers[2] = c;
    }

    double max() {
        return Math.max(numbers[0].doubleValue(), Math.max(numbers[1].doubleValue(), numbers[2].doubleValue()));
    }

    double avarage() {
        return (numbers[0].doubleValue() + numbers[1].doubleValue() + numbers[2].doubleValue()) / 3;
    }

    void sort() {
        Arrays.sort(numbers);
    }

    @Override
    public String toString() {
        return String.format("%.2f %.2f %.2f",
                numbers[0].doubleValue(),
                numbers[1].doubleValue(),
                numbers[2].doubleValue());
    }
}



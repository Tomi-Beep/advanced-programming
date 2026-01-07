package exam_problems.p12_generics;

import java.util.*;

public class GenericFractionTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double n1 = scanner.nextDouble();
        double d1 = scanner.nextDouble();
        float n2 = scanner.nextFloat();
        float d2 = scanner.nextFloat();
        int n3 = scanner.nextInt();
        int d3 = scanner.nextInt();
        try {
            GenericFraction<Double, Double> gfDouble = new GenericFraction<Double, Double>(n1, d1);
            GenericFraction<Float, Float> gfFloat = new GenericFraction<Float, Float>(n2, d2);
            GenericFraction<Integer, Integer> gfInt = new GenericFraction<Integer, Integer>(n3, d3);
            System.out.printf("%.2f\n", gfDouble.toDouble());
            System.out.println(gfDouble.add(gfFloat));
            System.out.println(gfInt.add(gfFloat));
            System.out.println(gfDouble.add(gfInt));
            gfInt = new GenericFraction<Integer, Integer>(n3, 0);
        } catch (ZeroDenominatorException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }
}

class GenericFraction<T extends Number, U extends Number> {
    private Integer numerator;
    private Integer denominator;

    public Integer getNumerator() {
        return numerator;
    }

    public Integer getDenominator() {
        return denominator;
    }

    public GenericFraction(T numerator, U denominator) {
        if (denominator.equals(0))
            throw (new ZeroDenominatorException("Denominator cannot be zero"));
        this.numerator = numerator.intValue();
        this.denominator = denominator.intValue();
    }

    GenericFraction<Double, Double> add(GenericFraction<? extends Number, ? extends Number> gf) {
        Double n1 = numerator.doubleValue();
        Double d1 = denominator.doubleValue();
        Double n2 = gf.getNumerator().doubleValue();
        Double d2 = gf.getDenominator().doubleValue();
        if (d1.equals(d2))
            return new GenericFraction<>(n1 + n2, d1);
        else {
            Double d3 = d1 * d2;
            Double n31 = n1 * d2;
            Double n32 = n2 * d1;
            return new GenericFraction<>(n31 + n32, d3);
        }
    }

    double toDouble() {
        return numerator.doubleValue() / denominator.doubleValue();
    }

    void normalise() {
        for (int i = 2; i < Math.max(numerator, denominator); i++) {
            if (numerator % i == 0 && denominator % i == 0) {
                numerator /= i;
                denominator /= i;
                i--;
            }
        }
    }

    @Override
    public String toString() {
        normalise();
        return String.format("%.2f / %.2f", numerator.doubleValue(), denominator.doubleValue());
    }
}

class ZeroDenominatorException extends RuntimeException {
    ZeroDenominatorException(String msg) {
        super(msg);
    }
}

package exercises.lambda_and_functional_interfaces;

import java.util.Comparator;
import java.util.function.*;

public class FunctionalInterfaces {


    public static void main(String[] args) {
        Function<String, Integer> length = (s) -> s.length();

        BiFunction<Integer, Integer, Integer> add = (s1, s2) -> s1 + s2;

        Predicate<Integer> isEven = (s) -> s % 2 == 0;

        Consumer <String> print = (s) -> System.out.println(s);

        Supplier<Long> time = () -> System.currentTimeMillis();


    }
}

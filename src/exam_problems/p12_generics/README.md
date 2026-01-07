# Generic Fraction

## Problem Description

Develop a generic class for working with fractions. The `GenericFraction` class has two generic parameters `T` and `U` which must be from a class that extends the `Number` class. `GenericFraction` has two variables:
* `numerator` - numerator
* `denominator` - denominator

The following methods need to be implemented:
* `GenericFraction(T numerator, U denominator)` - constructor that initializes the numerator and denominator of the fraction. If we try to initialize a fraction with a 0 value for the denominator, an exception of type `ZeroDenominatorException` should be thrown
* `GenericFraction<Double, Double> add(GenericFraction<? extends Number, ? extends Number> gf)` - addition of two fractions
* `double toDouble()` - returns the value of the fraction as a real number
* `toString():String` - prints the fraction in the following format `[numerator] / [denominator]`, reduced (normalized) and each with two decimal places

## Custom Exception

Create a custom exception class:
* `ZeroDenominatorException` - thrown when attempting to create a fraction with denominator equal to zero

## Example 1

### Input
```
2 5
1 7
4 9
```

### Output
```
0.40
19.00 / 35.00
37.00 / 63.00
38.00 / 45.00
Denominator cannot be zero
```

## Example 2

### Input
```
2 8
4 8
1 5
```

### Output
```
0.25
3.00 / 4.00
7.00 / 10.00
9.00 / 20.00
Denominator cannot be zero
```

## Implementation Notes

* The fraction should be automatically reduced (simplified) to its lowest terms using the greatest common divisor (GCD)
* When adding fractions, find a common denominator and add the numerators accordingly
* Format all output numbers with exactly two decimal places
* The `add` method should return a new `GenericFraction<Double, Double>` object
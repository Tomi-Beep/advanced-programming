# Generic Evaluator

## Problem Description

Define a generic interface `IEvaluator` with a single method:
- `boolean evaluate(T a, T b)` - a method that returns true/false for some type of comparison between two objects of the same comparable class.

Create a class `EvaluatorBuilder` with a single generic static method:
- `static IEvaluator<T> build(String operator)` - a method that returns an object implementing the `IEvaluator` interface. The implementation of these objects should be based on the operator given as an argument to the function. Valid operators are:
  - `>`
  - `==`
  - `!=`
  - `<`

**Note:** The implementations of the interface must be defined using lambda expressions!

Define a class `Evaluator` with a single generic static method:
- `static <T> boolean evaluateExpression(T left, T right, String operator)` - a method that takes three arguments: the first two are the values for evaluation, while the third is the operator used for evaluation. In this method, create the appropriate evaluator for the operator and evaluate the two values `left` and `right`.

## Example

### Input
```
1 2 > 3
1 2 == 3
1 2 < 3
1 1 > 2
1 1 == 1
1 1 < 2
1 1 != 2
2 1.2 > 1.3
2 1.0 == 1.1
2 1.0 == 1
2 5.6667 > 5.6
2 7.8989 != 7
3 A == B
3 A == A
3 A == a
3 b > A
3 b > A
4 NP > VP
4 AA > BB
4 BB > AA
```

### Result
```
false
false
true
false
true
true
true
false
false
true
true
true
false
true
false
false
true
false
true
false
```
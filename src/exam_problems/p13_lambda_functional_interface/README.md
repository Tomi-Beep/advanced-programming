# Applicant Evaluator

## Problem Description

Given a class `Applicant` (candidate for evaluation) with name, credit score, experience (work experience in years), and whether they have a criminal record. Also given is an interface for evaluating candidates `Evaluator` with one method `boolean evaluate(Applicant applicant)` and an enumeration with the following types of evaluators:

The candidate passes the evaluation (the method returns `true`) if:
* `NO_CRIMINAL_RECORD` - has no criminal record
* `MORE_EXPERIENCE` - has at least 10 years of work experience
* `MORE_CREDIT_SCORE` - has a credit score of at least 500
* `NO_CRIMINAL_RECORD_AND_MORE_EXPERIENCE` - has no criminal record AND has at least 10 years of work experience
* `MORE_EXPERIENCE_AND_MORE_CREDIT_SCORE` - has at least 10 years of work experience AND has a credit score of at least 500
* `NO_CRIMINAL_RECORD_AND_MORE_CREDIT_SCORE` - has no criminal record AND has a credit score of at least 500

## Task

Your task is to implement the method `build(Evaluator.TYPE type)` in the `EvaluatorBuilder` class, which will return an object of the appropriate implementation of the `Evaluator` interface for the corresponding evaluation type. If the evaluation type is not one of those listed, the method should throw an exception of type `InvalidEvaluation`.

**Note:** A well-designed solution (will be published after the exam) will receive a bonus of 10 points.

## Classes and Interfaces

### Applicant
* `name` - applicant's name
* `creditScore` - credit score
* `experience` - work experience in years
* `hasCriminalRecord` - whether the applicant has a criminal record

### Evaluator Interface
* `boolean evaluate(Applicant applicant)` - evaluates the applicant based on specific criteria

### Evaluator.TYPE Enumeration
* `NO_CRIMINAL_RECORD`
* `MORE_EXPERIENCE`
* `MORE_CREDIT_SCORE`
* `NO_CRIMINAL_RECORD_AND_MORE_EXPERIENCE`
* `MORE_EXPERIENCE_AND_MORE_CREDIT_SCORE`
* `NO_CRIMINAL_RECORD_AND_MORE_CREDIT_SCORE`

### EvaluatorBuilder
* `static Evaluator build(Evaluator.TYPE type)` - returns an evaluator implementation based on the type

### InvalidEvaluation Exception
Custom exception thrown when an invalid evaluation type is provided.

## Example 1

### Input
```
Славе
600
12
true
1
```

### Output
```
Applicant
Name: Славе
Credit score: 600
Experience: 12
Criminal record: Yes

Evaluation type: MORE_EXPERIENCE
Applicant is ACCEPTED
```

## Example 2

### Input
```
Катарина
300
12
false
4
```

### Output
```
Applicant
Name: Катарина
Credit score: 300
Experience: 12
Criminal record: No

Evaluation type: MORE_EXPERIENCE_AND_MORE_CREDIT_SCORE
Applicant is REJECTED
```

## Evaluation Criteria

* Credit score threshold: **500**
* Experience threshold: **10 years**
* Criminal record: **must be false (no record)** for relevant evaluations

## Implementation Notes

* Use lambda expressions or method references for clean implementation
* The `build` method should return different `Evaluator` implementations based on the type
* Throw `InvalidEvaluation` exception for unsupported evaluation types
* Applicant is ACCEPTED if evaluation returns `true`, REJECTED if `false`
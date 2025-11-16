package exercises.lambda_and_functional_interfaces.course;

import java.util.Arrays;
import java.util.function.*;

public class Course {
    private String name;
    private Student[] students;
    private int numStudents = 0;

    public Course(String name, int capacity) {
        this.name = name;
        students = new Student[capacity];
    }

    public boolean enroll(Supplier<Student> supplier) {
        if (students.length < numStudents) {
            students[numStudents++] = supplier.get();
            return true;
        }
        return false;
    }

    public void forEach(Consumer<Student> action) {
        for (Student student : students) {
            action.accept(student);
        }
    }

    public int count(Predicate<Student> condition) {
        int sum = 0;
        for (Student student : students) {
            if (condition.test(student))
                sum++;
        }
        return sum;
    }

    public Student findFirst(Predicate<Student> condition) {
        for (Student student : students) {
            if (condition.test(student))
                return student;
        }
        return null;
    }

    public Student[] filter(Predicate<Student> condition) {
        Student[] filtered = new Student[students.length];
        int counter = 0;
        for (Student student : students) {
            if (condition.test(student))
                filtered[counter++] = student;
        }
        return filtered;
    }

    public String[] mapToLabels(Function<Student, String> mapper) {
        String[] mapped = new String[students.length];
        int counter = 0;
        for (Student s : students) {
            mapped[counter++] = mapper.apply(s);
        }
        return mapped;
    }

    public void mutate(Consumer<Student> mutator) {
        for (Student student : students) {
            mutator.accept(student);
        }
    }

    public void conditionalMutate(Predicate<Student> condition, Consumer<Student> mutator) {
        for (Student student : students) {
            if (condition.test(student))
                mutator.accept(student);
        }
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", students=" + Arrays.toString(students) +
                ", numStudents=" + numStudents +
                '}';
    }
}

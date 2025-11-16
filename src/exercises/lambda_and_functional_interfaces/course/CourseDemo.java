package exercises.lambda_and_functional_interfaces.course;

import java.util.Scanner;
import java.util.function.*;

public class CourseDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numStudents = sc.nextInt();

        Supplier<Student> supplier = () -> new Student(sc.nextInt(), sc.next(), sc.nextInt(), sc.nextInt());

        Course course = new Course("Introduction to Tomi", numStudents);
        for (int i = 0; i < numStudents; i++) {
            course.enroll(supplier);
        }

        Consumer<Student> action = (s) -> s.toString();
        course.forEach(action);

        Predicate<Student> isPassing = s -> s.getGrade() >= 6;
        Predicate<Student> goodAttendance = s -> s.getAttendance() >= 70;
        Predicate<Student> passingAndPresent = isPassing.and(goodAttendance);

        Predicate<Student> aboveNine = s -> s.getGrade() >= 9;
        System.out.println(course.findFirst(aboveNine));

        Consumer<Student> incrementer = (s) -> s.setGrade(s.getGrade() + 1);
        course.mutate(incrementer);
        Predicate<Student> greatAttendance = s ->s.getAttendance()>= 90;
        course.conditionalMutate(greatAttendance, incrementer);


    }
}

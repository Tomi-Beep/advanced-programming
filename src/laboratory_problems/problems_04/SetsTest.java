package laboratory_problems.problems_04;

import java.util.*;

class StudentAlreadyExists extends Exception {
    public StudentAlreadyExists(String message) {
        super(message);
    }
}

class AverageGradeComparator implements Comparator<Student> {

    @Override
    public int compare(Student o1, Student o2) {
        if (o1.getAverage() == o2.getAverage()) {
            if (o1.getPassedGrades() == o2.getPassedGrades()) {
                return o2.getId().compareTo(o1.getId());
            } else {
                return Integer.compare(o2.getPassedGrades(), o1.getPassedGrades());
            }
        } else {
            return Double.compare(o2.getAverage(), o1.getAverage());
        }
    }
}

class CoursesPassedComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        if (o1.getPassedGrades() == o2.getPassedGrades()) {
            if (o1.getAverage() == o2.getAverage()) {
                return o2.getId().compareTo(o1.getId());
            } else {
                return Double.compare(o2.getAverage(), o1.getAverage());
            }
        } else {
            return Integer.compare(o2.getPassedGrades(), o1.getPassedGrades());
        }
    }
}

class Student implements Comparable<Student>{
    private String id;
    private List<Integer> grades;

    Student(String id, List<Integer> grades) {
        this.id = id;
        this.grades = grades;
    }

    @Override
    public String toString() {
        return String.format("Student{id='%s', grades=%s}", id, grades);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id);
    }

    public double getAverage() {
        int sum = 0;
        for (Integer grade : grades) {
            sum += grade;
        }
        return (double) sum / grades.size();
    }

    public int getPassedGrades() {
        int counter = 0;
        for (Integer grade : grades) {
            if (grade >= 6)
                counter++;
        }
        return counter;
    }

    public String getId() {
        return id;
    }

    public List<Integer> getGrades() {
        return grades;
    }

    @Override
    public int compareTo(Student o) {
        return id.compareTo(o.getId());
    }
}

class Faculty {
    Set<Student> students;

    public Faculty() {
        students = new TreeSet<>();
    }

    void addStudent(String id, List<Integer> grades) throws StudentAlreadyExists {
        Student student = new Student(id, grades);
        if (!students.contains(student)) {
            students.add(student);
        } else {
            throw new StudentAlreadyExists(String.format("Student with ID %s already exists", id));
        }
    }

    void addGrade(String id, int grade) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                student.getGrades().add(grade);
            }
        }
    }

    Set<Student> getStudentsSortedByAverageGrade() {
        Set<Student> sorted = new TreeSet<>(new AverageGradeComparator());
        sorted.addAll(students);
        return sorted;
    }

    Set<Student> getStudentsSortedByCoursesPassed() {
        Set<Student> sorted = new TreeSet<>(new CoursesPassedComparator());
        sorted.addAll(students);
        return sorted;
    }
}

public class SetsTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Faculty faculty = new Faculty();

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            String[] tokens = input.split("\\s+");
            String command = tokens[0];

            switch (command) {
                case "addStudent":
                    String id = tokens[1];
                    List<Integer> grades = new ArrayList<>();
                    for (int i = 2; i < tokens.length; i++) {
                        grades.add(Integer.parseInt(tokens[i]));
                    }
                    try {
                        faculty.addStudent(id, grades);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "addGrade":
                    String studentId = tokens[1];
                    int grade = Integer.parseInt(tokens[2]);
                    faculty.addGrade(studentId, grade);
                    break;

                case "getStudentsSortedByAverageGrade":
                    System.out.println("Sorting students by average grade");
                    Set<Student> sortedByAverage = faculty.getStudentsSortedByAverageGrade();
                    for (Student student : sortedByAverage) {
                        System.out.println(student);
                    }
                    break;

                case "getStudentsSortedByCoursesPassed":
                    System.out.println("Sorting students by courses passed");
                    Set<Student> sortedByCourses = faculty.getStudentsSortedByCoursesPassed();
                    for (Student student : sortedByCourses) {
                        System.out.println(student);
                    }
                    break;

                default:
                    break;
            }
        }

        scanner.close();
    }
}

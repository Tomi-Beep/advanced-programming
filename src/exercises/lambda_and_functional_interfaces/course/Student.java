package exercises.lambda_and_functional_interfaces.course;

public class Student {
    private final int index;
    private String name;
    private int grade;
    private int attendance;

    public Student (int index, String name, int grade, int attendance){
        this.index = index;
        this.name = name;
        this.grade = grade;
        this.attendance = attendance;
    }

    @Override
    public String toString() {
        return "Student{" +
                "index=" + index +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                ", attendance=" + attendance +
                '}';
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        if (5<=grade && grade<=10)
            this.grade = grade;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }


}

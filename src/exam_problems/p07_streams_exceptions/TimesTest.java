package exam_problems.p07_streams_exceptions;

import java.io.*;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class TimesTest {

    public static void main(String[] args) {
        TimeTable timeTable = new TimeTable();
        try {
            timeTable.readTimes(System.in);
        } catch (UnsupportedFormatException e) {
            System.out.println("UnsupportedFormatException: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("InvalidTimeException: " + e.getMessage());
        }
        System.out.println("24 HOUR FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_24);
        System.out.println("AM/PM FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_AMPM);
    }

}

enum TimeFormat {
    FORMAT_24, FORMAT_AMPM
}

class Time implements Comparable<Time> {
    private final int hours;
    private final int minutes;

    public Time(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getMinutes() {
        return hours * 60 + minutes;
    }

    @Override
    public int compareTo(Time o) {
        return Integer.compare(getMinutes(), o.getMinutes());
    }

    public String toString(TimeFormat format) {
        if (format.equals(TimeFormat.FORMAT_24))
            return String.format("%2d:%02d", hours, minutes);
        else {
            if (hours == 0) {
                return String.format("%2d:%02d AM", hours + 12, minutes);
            } else if (hours >= 1 && hours <= 11) {
                return String.format("%2d:%02d AM", hours, minutes);
            } else if (hours == 12) {
                return String.format("%2d:%02d PM", hours, minutes);
            }
            return String.format("%2d:%02d PM", hours - 12, minutes);
        }
    }
}

class TimeTable {
    private final Set<Time> times = new TreeSet<>();

    void readTimes(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        reader.lines()
                .flatMap(s -> Arrays.stream(s.split("\\s+")))
                .forEach(token -> times.add(createTime(token)));
    }

    Time createTime(String time) throws UnsupportedFormatException, InvalidTimeException {
        if (!(time.contains(":") || time.contains(".")))
            throw (new UnsupportedFormatException(time));

        String[] tokens = time.split("[:.]");

        if (Integer.parseInt(tokens[0]) < 0 || Integer.parseInt(tokens[0]) > 23 || Integer.parseInt(tokens[1]) < 0 || Integer.parseInt(tokens[1]) > 59) {
            throw (new InvalidTimeException(time));
        }

        return new Time(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
    }

    void writeTimes(OutputStream outputStream, TimeFormat format) {
        PrintWriter pw = new PrintWriter(outputStream);
        times.forEach(t -> pw.println(t.toString(format)));
        pw.flush();
    }

}

class InvalidTimeException extends RuntimeException {
    public InvalidTimeException(String msg) {
        super(msg);
    }
}


class UnsupportedFormatException extends RuntimeException {
    public UnsupportedFormatException(String msg) {
        super(msg);
    }
}

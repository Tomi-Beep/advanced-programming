package exam_problems.p15_navigable_tree_set;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class WeatherStationTest {

    public static void main(String[] args) throws ParseException {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        Scanner scanner = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
        WeatherStation ws = new WeatherStation(n);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("=====")) {
                break;
            }
            String[] parts = line.split(" ");
            float temp = Float.parseFloat(parts[0]);
            float wind = Float.parseFloat(parts[1]);
            float hum = Float.parseFloat(parts[2]);
            float vis = Float.parseFloat(parts[3]);
            line = scanner.nextLine();
            Date date = df.parse(line);
            ws.addMeasurment(temp, wind, hum, vis, date);
        }
        String line = scanner.nextLine();
        Date from = df.parse(line);
        line = scanner.nextLine();
        Date to = df.parse(line);
        scanner.close();
        System.out.println(ws.total());
        try {
            ws.status(from, to);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}

class Measurement {
    private final float temperature;
    private final float wind;
    private final float humidity;
    private final float visibility;
    private final Date date;

    public Measurement(float temperature, float wind, float humidity, float visibility, Date date) {
        this.temperature = temperature;
        this.wind = wind;
        this.humidity = humidity;
        this.visibility = visibility;
        this.date = date;
    }

    public Measurement(Date date) {
        this.temperature = 0;
        this.wind = 0;
        this.humidity = 0;
        this.visibility = 0;
        this.date = date;
    }

    public float getTemperature() {
        return temperature;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("%.1f %.1f km/h %.1f%% %.1f km %s",
                temperature, wind, humidity, visibility, date);
    }
}

class MeasurementComparator implements Comparator<Measurement> {

    @Override
    public int compare(Measurement o1, Measurement o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}

class WeatherStation {
    private final int days;
    private final NavigableSet<Measurement> measurements;

    private static final double MS_PER_SEC = 1000;
    private static final double MS_PER_MIN = 60 * MS_PER_SEC;
    private static final double MS_PER_DAY = 24 * 60 * MS_PER_MIN;

    public WeatherStation(int days) {
        this.days = days;
        measurements = new TreeSet<>(new MeasurementComparator());
    }

    public void addMeasurment(float temperature, float wind, float humidity, float visibility, Date date) {

        Measurement measurement = new Measurement(temperature, wind, humidity, visibility, date);

        Measurement temp = measurements.floor(measurement);
        if (temp != null && isClose(temp, measurement)) {
            return;
        }

        temp = measurements.ceiling(measurement);
        if (temp != null && isClose(temp, measurement)) {
            return;
        }

        measurements.add(measurement);

        while (!measurements.isEmpty()) {
            temp = measurements.first();

            double timeDiff = (measurement.getDate().getTime() - temp.getDate().getTime()) / (MS_PER_DAY);
            if (timeDiff > days)
                measurements.pollFirst();
            else
                break;

        }
    }

    private static boolean isClose(Measurement temp, Measurement measurement) {
        double timeDiff = Math.abs(temp.getDate().getTime() - measurement.getDate().getTime()) / (MS_PER_MIN);
        return timeDiff < 2.5;
    }

    public int total() {
        return measurements.size();
    }

    public void status(Date from, Date to) {
        Collection<Measurement> filtered = measurements.subSet(
                new Measurement(from), true,
                new Measurement(to), true
        );

        if (filtered.isEmpty()) {
            throw new RuntimeException();
        }

        filtered.forEach(System.out::println);

        double average = filtered.stream()
                .mapToDouble(Measurement::getTemperature)
                .average()
                .orElse(0.0);
        System.out.printf("Average temperature: %.2f%n", average);
    }
}
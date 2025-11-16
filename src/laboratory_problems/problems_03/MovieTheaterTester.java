package laboratory_problems.problems_03;

import java.io.*;
import java.util.*;

class MovieComparatorGenreAndTitle implements Comparator<Movie> {
    @Override
    public int compare(Movie o1, Movie o2) {
        if (o1.getGenre().equals(o2.getGenre()))
            return o1.getTitle().compareTo(o2.getTitle());
        return o1.getGenre().compareTo(o2.getGenre());
    }
}

class MovieComparatorYearAndTitle implements Comparator<Movie> {
    @Override
    public int compare(Movie o1, Movie o2) {
        if (o1.getYear() == o2.getYear())
            return o1.getTitle().compareTo(o2.getTitle());
        return Integer.compare(o1.getYear(), o2.getYear());
    }
}

class MovieComparatorRatingAndTitle implements Comparator<Movie> {
    @Override
    public int compare(Movie o1, Movie o2) {
        if (o1.getAvgRating() == o2.getAvgRating())
            return o1.getTitle().compareTo(o2.getTitle());
        return Double.compare(o2.getAvgRating(), o1.getAvgRating());
    }
}

class Movie {
    private String title;
    private String genre;
    private int year;
    private double avgRating;

    public Movie(String title, String genre, int year, double avgRating) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.avgRating = avgRating;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public double getAvgRating() {
        return avgRating;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %d, %.2f", title, genre, year, avgRating);
    }
}

class MovieTheater {
    private ArrayList<Movie> movies;

    public MovieTheater() {
        this.movies = new ArrayList<>();
    }

    public void readMovies(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        int n = Integer.parseInt(reader.readLine());
        for (int i = 0; i < n; i++) {
            String title = reader.readLine();
            String genre = reader.readLine();
            int year = Integer.parseInt(reader.readLine());
            String[] ratings = reader.readLine().split("\\s+");
            double sum = 0;
            double counter = 0;
            for (String r : ratings) {
                sum += Double.parseDouble(r);
                counter++;
            }
            double rating = sum / counter;
            movies.add(new Movie(title, genre, year, rating));
        }
    }

    public void printByGenreAndTitle() {
        movies.sort(new MovieComparatorGenreAndTitle());

        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }

    public void printByYearAndTitle() {
        movies.sort(new MovieComparatorYearAndTitle());

        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }

    public void printByRatingAndTitle() {
        movies.sort(new MovieComparatorRatingAndTitle());

        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}

public class MovieTheaterTester {
    public static void main(String[] args) {
        MovieTheater mt = new MovieTheater();
        try {
            mt.readMovies(System.in);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("SORTING BY RATING");
        mt.printByRatingAndTitle();
        System.out.println("\nSORTING BY GENRE");
        mt.printByGenreAndTitle();
        System.out.println("\nSORTING BY YEAR");
        mt.printByYearAndTitle();
    }
}
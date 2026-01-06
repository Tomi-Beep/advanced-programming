package laboratory_problems.problems_06;

import java.util.ArrayList;
import java.util.List;

class Song {
    String title;
    String artist;

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Song{title=" + title + ", artist=" + artist + "}";
    }
}

interface State {
    void pressPlay();
    void pressStop();
    void pressFWD();
    void pressREW();
}

class MP3Player {
    List<Song> songs;
    int currentSongIndex;
    State playingState;
    State pausedState;
    State stoppedState;
    State currentState;

    public MP3Player(List<Song> songs) {
        this.songs = songs;
        this.currentSongIndex = 0;
        this.playingState = new PlayingState(this);
        this.pausedState = new PausedState(this);
        this.stoppedState = new StoppedState(this);
        this.currentState = stoppedState;
    }

    public void pressPlay() {
        currentState.pressPlay();
    }

    public void pressStop() {
        currentState.pressStop();
    }

    public void pressFWD() {
        currentState.pressFWD();
    }

    public void pressREW() {
        currentState.pressREW();
    }

    public void printCurrentSong() {
        System.out.println(songs.get(currentSongIndex));
    }

    public void setCurrentState(State state) {
        this.currentState = state;
    }

    @Override
    public String toString() {
        return "MP3Player{currentSong = " + currentSongIndex + ", songList = " + songs + "}";
    }
}

class PlayingState implements State {
    MP3Player player;

    public PlayingState(MP3Player player) {
        this.player = player;
    }

    @Override
    public void pressPlay() {
        System.out.println("Song is already playing");
    }

    @Override
    public void pressStop() {
        System.out.println("Song " + player.currentSongIndex + " is paused");
        player.setCurrentState(player.pausedState);
    }

    @Override
    public void pressFWD() {
        System.out.println("Forward...");
        player.currentSongIndex = (player.currentSongIndex + 1) % player.songs.size();
        player.setCurrentState(player.pausedState);
    }

    @Override
    public void pressREW() {
        System.out.println("Reward...");
        player.currentSongIndex = (player.currentSongIndex - 1 + player.songs.size()) % player.songs.size();
        player.setCurrentState(player.pausedState);
    }
}

class PausedState implements State {
    MP3Player player;

    public PausedState(MP3Player player) {
        this.player = player;
    }

    @Override
    public void pressPlay() {
        System.out.println("Song " + player.currentSongIndex + " is playing");
        player.setCurrentState(player.playingState);
    }

    @Override
    public void pressStop() {
        System.out.println("Songs are stopped");
        player.currentSongIndex = 0;
        player.setCurrentState(player.stoppedState);
    }

    @Override
    public void pressFWD() {
        System.out.println("Forward...");
        player.currentSongIndex = (player.currentSongIndex + 1) % player.songs.size();
        player.setCurrentState(player.pausedState);
    }

    @Override
    public void pressREW() {
        System.out.println("Reward...");
        player.currentSongIndex = (player.currentSongIndex - 1 + player.songs.size()) % player.songs.size();
        player.setCurrentState(player.pausedState);
    }
}

class StoppedState implements State {
    MP3Player player;

    public StoppedState(MP3Player player) {
        this.player = player;
    }

    @Override
    public void pressPlay() {
        System.out.println("Song " + player.currentSongIndex + " is playing");
        player.setCurrentState(player.playingState);
    }

    @Override
    public void pressStop() {
        System.out.println("Songs are already stopped");
    }

    @Override
    public void pressFWD() {
        System.out.println("Forward...");
        player.currentSongIndex = (player.currentSongIndex + 1) % player.songs.size();
        player.setCurrentState(player.pausedState);
    }

    @Override
    public void pressREW() {
        System.out.println("Reward...");
        player.currentSongIndex = (player.currentSongIndex - 1 + player.songs.size()) % player.songs.size();
        player.setCurrentState(player.pausedState);
    }
}

public class PatternTest {
    public static void main(String args[]) {
        List<Song> listSongs = new ArrayList<Song>();
        listSongs.add(new Song("first-title", "first-artist"));
        listSongs.add(new Song("second-title", "second-artist"));
        listSongs.add(new Song("third-title", "third-artist"));
        listSongs.add(new Song("fourth-title", "fourth-artist"));
        listSongs.add(new Song("fifth-title", "fifth-artist"));
        MP3Player player = new MP3Player(listSongs);

        System.out.println(player.toString());
        System.out.println("First test");

        player.pressPlay();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();

        System.out.println(player.toString());
        System.out.println("Second test");

        player.pressStop();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();

        System.out.println(player.toString());
        System.out.println("Third test");

        player.pressFWD();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();

        System.out.println(player.toString());
    }
}

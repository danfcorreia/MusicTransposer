import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Server {

    private boolean running = true;

    public void start() throws IOException {
        while (running) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nWelcome to Music Transposer! What are you willing to do?\n" +
                    "Write -t- to Translate or -q- to quit the program?");
            String answer = scanner.next();

            if (answer.equals("q")) {
                running = false;
            } else if (answer.equals("t")) {
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

                System.out.println("Write your music with _spaces_ between chords. Example: A F#7 Bm E7\nThe Program will only transpose valid chords. Fm is valid chord, but FADG will only transpose the first char!");
                String music = in.readLine();

                System.out.println("Specify the original key. Example: A, B, C...?");
                Scanner scanner2 = new Scanner(System.in);
                String originalKey = scanner2.next();

                System.out.println("To which key would you like to transpose the music? Example: A, B, C...?");
                Scanner scanner3 = new Scanner(System.in);
                String newKey = scanner3.next();

                String transposedMusic = changeKey(music, originalKey, newKey);
                System.out.println("Here is your transposed Music:\n"+ transposedMusic);
            } else {
                System.out.println("Not a valid answer!");
            }

        }
        System.out.println("Closing Music Transposer...");
    }

    private String changeKey(String music, String originalKey, String newKey) {

        String[] notes = {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};

        int semitones = (noteIndex(notes, newKey) - noteIndex(notes, originalKey) + 12) % 12;

        String[] inputChords = music.split(" ");

        for (int i = 0; i < inputChords.length; i++) {
            String localChord = inputChords[i];
            if (localChord.length() > 1 && localChord.charAt(1) == '#') {
                int localIndex = noteIndex(notes, localChord.substring(0, 2));
                localIndex = (localIndex + semitones) % 12;
                inputChords[i] = notes[localIndex] + localChord.substring(2);
            } else {
                int localIndex = noteIndex(notes, localChord.substring(0, 1));
                localIndex = (localIndex + semitones) % 12;
                inputChords[i] = notes[localIndex] + localChord.substring(1);
            }
        }

        return String.join(" ", inputChords);
    }

    private static int noteIndex(String[] array, String localChord) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(localChord)) {
                return i;
            }
        }
        return -1;
    }
}
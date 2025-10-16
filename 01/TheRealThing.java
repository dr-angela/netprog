import java.io.*;

// javac TheRealThing.java
// java TheRealThing

/**
 * Aufgabe 1.4 – Java Threads
 * ---------------------------
 * Dieses Programm zeigt, wie man mehrere Threads verwendet,
 * um dabei ein Float-Array parallel zu berechnen.
 *
 * Jeder Thread bekommt einen bestimmten Abschnitt (Startindex bis Endindex)
 * f = erfinden Sie etwas!
 * Thread 1 → arbeitet auf sharedArray[0..9]
 * Thread 2 → arbeitet auf sharedArray[10..19]
 * Thread 3 → arbeitet auf sharedArray[20..29]
 * Danach werden alle Teilergebnisse zu einem gemeinsamen Gesamtergebnis addiert.
 *
 * volatile schützt nicht vor gleichzeitigen Änderungen im Array-Inhalt,
 * sondern nur davor, dass Threads verschiedene Kopien der Variablen sehen
 */

public class TheRealThing extends Thread {

    private static float result = 0f;
    private String filename;
    private int start;
    private int end;

    // one variable for all
    private static volatile float[] sharedArray;

    /**
    * Creates a new TheRealThing thread which operates
    * on the indexes start to end.
    public TheRealThing(String filename, int start, int end) {
    */
    public TheRealThing(String filename, int start, int end) {
        this.filename = filename;
        this.start = start;
        this.end = end;
    }

    /**
    * Performs "eine komplizierte Berechnung" on array and
    * returns the result:
    */
    public float eine_komplizierte_Berechnung(float[] array) {
        float localSum = 0f;
        for (int i = start; i < end; i++) {
            int rounded = Math.round(array[i]);
            int mod = rounded % 50;
            localSum += mod * 1.5f;
        }
        return localSum;
    }

    @Override
    public void run() {
        float local = eine_komplizierte_Berechnung(sharedArray);

        // Zugriff auf gemeinsame Variable (synchronized)
        synchronized (TheRealThing.class) {
            // sum up
            result += local;
        }
    }

    public static float[] readArrayFromFile(String filename) throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filename))) {
            String name = dis.readUTF();
            int id = dis.readInt();
            int length = dis.readInt();

            float[] arr = new float[length];
            for (int i = 0; i < length; i++) {
                arr[i] = dis.readFloat();
            }
            return arr;
        }
    }



    public static void main(String[] args) {
        String pathToFile = "./myArrayData.dat";
        int numThreads = 12;
        int arraySize = 70;

        // example data
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(pathToFile))) {
            dos.writeUTF("MyArrayData");
            dos.writeInt(1);
            dos.writeInt(arraySize);
            for (int i = 0; i < arraySize; i++) {
                dos.writeFloat((float) (Math.random() * 1000 ));
            }
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Schreiben der Datei! " + e.getMessage());
        }
        try {
            sharedArray = readArrayFromFile(pathToFile);
            int arrayLength = sharedArray.length;

            TheRealThing[] threads = new TheRealThing[numThreads];

            int baseChunk = arrayLength / numThreads;
            int remainder = arrayLength % numThreads;

            int startIndex = 0;
            for (int i = 0; i < numThreads; i++) {
                int chunkSize = baseChunk + (i < remainder ? 1 : 0);
                int endIndex = startIndex + chunkSize;

                threads[i] = new TheRealThing(pathToFile, startIndex, endIndex);
                threads[i].start();

                startIndex = endIndex;
            }

            // Warten bis alle Threads fertig sind
            for (TheRealThing t : threads) {
                t.join();
            }
            System.out.println("Endergebnis: " + result);

        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Datei: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Thread wurde unterbrochen: " + e.getMessage());
        }

    }



}

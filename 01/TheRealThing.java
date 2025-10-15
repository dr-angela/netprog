/**
 * Aufgabe 1.4 – Java Threads
 * ---------------------------
 * Dieses Programm zeigt, wie man mehrere Threads verwendet,
 * um dabei ein Float-Array parallel zu berechnen.
 *
 * Jeder Thread bekommt einen bestimmten Abschnitt (Startindex bis Endindex)
 * f = erfinden Sie etwas!
 *
 * Danach werden alle Teilergebnisse zu einem gemeinsamen Gesamtergebnis addiert.
 */

public class TheRealThing extends Thread {

    private static float result = -1;
    private String filename;
    private int start;
    private int end;

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
    * returns the result
    */
    public float eine_komplizierte_Berechnung(float[] array) {
        // TODO ... erfinden Sie etwas, seien Sie kreativ!
        return result;
    }

    public void run() {
    // TODO ...
    }

    public static void main(String[] args) {
        String pathToFile = "./myArrayData.dat";
        int numThreads = 12;
        int arraySize = 70;
        // TODO ...
    }



}

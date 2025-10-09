public class Aufgabe1_2 {

    public static void main(String[] args) {

        // Block A: Parameter prüfen (2) und Eingabedatei validieren
        if (args.length != 2) {
            System.err.println("Fehler: Es sind genau zwei Parameter erforderlich!");
            System.err.println("Ausführungsbefehl: java Aufgabe1_2 <Eingabedatei> <Ausgabedatei>");
            System.err.println("Beispiel: java Aufgabe1_2 utf8files/file1.txt file1_converted.zip");
            System.exit(1);
        }

        // Eingabe und Ausgabepfade instanzieren
        File inputFile = new File(args[0]);
        File outputFile = new File(args[1]);

        // Error Handling

        if (!inputFile.exists() || !inputFile.isFile() || !inputFile.canRead()) {
            System.err.println("Fehler: Eingabedatei ungültig!");
            System.exit(1);
        }






    }

}
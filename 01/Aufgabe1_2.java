import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

        // Block B: Reader und Writer erstellen

        try (
                // Reader UTF8
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_8)
                );

                // Outputstream für die ZIP Datei
                ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(outputFile));

                // Writer für ISO-8859-1 innerhalb des ZipStreams
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(zipOut, StandardCharsets.ISO_8859_1)
                )
        ) {
            // Block C: Zip Eitrag öffnen und zeilenweise konvertierten

            zipOut.putNextEntry(new ZipEntry(inputFile.getName()));

            String line;

            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.write("\r\n"); // LF -> CR+LF
            }

            writer.flush();
            zipOut.closeEntry();    // Block D: Zip Eintrag schließen

            // Statusausgabe

            System.out.println("Konvertierung erfolgreich!");
            System.out.println("Eingabedatei:   " + inputFile.getName() + " (UTF-8, LF)");
            System.out.println("Ausgabedatei: " + outputFile.getName() + " (ISO-8859-1, CR+LF, ZIP)");

        } catch (IOException e) {

            // Block für Fehlerbehandlung
            System.err.println("Fehler beim Lesen/Schreiben: " + e.getMessage());
            System.exit(1);
        }


    }

}
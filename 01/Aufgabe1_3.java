public class Aufgabe1_3 {

    /**
     * Erstellt eine Nachricht im angegebenen Transportprotokoll.
     *
     * @param isData         D-Flag: true = Datennachricht
     * @param isUrgent       U-Flag: true = dringende Nachricht
     * @param sequenceNumber Sequenznummer (0-65535)
     * @param payload        Nutzdaten (byte array)
     * @return Byte[] mit Nachricht
     * @throws IllegalArgumentException wenn param falsch
     */

    public static byte[] createMsg (boolean isData,
                                    boolean isUrgent,
                                    int sequenceNumber,
                                    byte [] payload) throws IllegalArgumentException {

        // Parameter prüfen
        if (sequenceNumber < 0 || sequenceNumber > 0xFFFF) {
            throw new IllegalArgumentException("SequenceNumber must be between 0 and 65535!");
        }
        if (payload == null || payload.length == 0) {
            throw new IllegalArgumentException("Payload cannot be null!");
        }

            return buffer.array();
    }


    }





}

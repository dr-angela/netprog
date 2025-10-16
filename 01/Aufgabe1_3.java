import java.nio.ByteBuffer;
import java.nio.ByteOrder;

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

        // Byte Array anlegen: 4 Byte Header, 4 Byte Länge, payload length

        int totalLength = 4 + 4 + payload.length;
        ByteBuffer buffer = ByteBuffer.allocate(totalLength);
        buffer.order(ByteOrder.BIG_ENDIAN); // network byte order


        // Header bauen: 32 Bit
        int header = 0;
        header |= (2 & 0x1f) << 27;

        // D-Flag (isData) pos: Bit 17
        if (isData) {
            header |= (1 << 17);
        }

        // U-Flag (isUrgent) pos: Bit 16
        if (isUrgent) {
            header |= (1 << 16);
        }

        // unteren 16 Bit: sequenceNumber
        header |= (sequenceNumber & 0xffff);

        // schreibe header in den buffer
        buffer.putInt(header);

        // payload Länge ermitteln
        buffer.putInt(payload.length);

        // schreibe payload in den buffer
        buffer.put(payload);

        return buffer.array();
    }


    // --- Testmethode zum Ausführen ---
    public static void main(String[] args) {
        // Beispiel: Nachricht mit D=true, U=false, sequenceNumber=42, payload={1,2,3}
        byte[] payload = new byte[] { 0x01, 0x02, 0x03 };
        byte[] msg = createMsg(true, false, 42, payload);

        System.out.println("Gesamtlänge: " + msg.length + " Bytes");

        System.out.print("Nachricht (Hex): ");
        for (byte b : msg) {
            System.out.printf("%02X ", b);
        }
        System.out.println();
    }

}

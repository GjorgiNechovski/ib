package frames;

import java.nio.charset.StandardCharsets;

public class Header {
    private final String sourceMAC;
    private final String destinationMAC;

    public Header(String sourceMAC, String destinationMAC) {
        this.sourceMAC = sourceMAC;
        this.destinationMAC = destinationMAC;
    }

    public byte [] getSourceMAC() {
        return sourceMAC.getBytes(StandardCharsets.UTF_8);
    }

    public byte [] getDestinationMAC() {
        return destinationMAC.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String toString() {
        return "Source MAC Address: " + sourceMAC + "\n" +
                "Destination MAC Address: " + destinationMAC;
    }
}
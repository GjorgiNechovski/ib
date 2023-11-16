package frames;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class ClearTextFrame {
    private final byte[] nonce = new byte[16];
    private final Header header;
    private final byte[] payloadData;

    public ClearTextFrame(String payloadData, String SourceAddress, String DestinationAddress) {
        generateInitialVector();
        header = new Header(SourceAddress,DestinationAddress);
        this.payloadData = payloadData.getBytes(StandardCharsets.UTF_8);
    }

    public byte[] getNonce() {
        return nonce;
    }

    public Header getFrameHeader() {
        return header;
    }

    public byte[] getPayloadData() {
        return payloadData;
    }


    public String toString(){
        return "Decrypted frame: " + header.toString() +
                "\nPayload Data: " + new String(payloadData, StandardCharsets.UTF_8);
    }

    public void generateInitialVector(){
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(nonce);
    }

}
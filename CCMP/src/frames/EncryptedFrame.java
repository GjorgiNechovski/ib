package frames;

import java.util.Base64;

public class EncryptedFrame {
    private final Header header;
    private final byte [] nonce;
    private final byte [] payloadData;
    private final byte [] MIC;

    public EncryptedFrame(Header header, byte[] nonce, byte[] payloadData, byte[] MIC) {
        this.header = header;
        this.nonce = nonce;
        this.payloadData = payloadData;
        this.MIC = MIC;
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

    public byte[] getMIC() {
        return MIC;
    }

    @Override
    public String toString() {
        return "Encrypted frame: " + header.toString() + "\nPayloadData: " + Base64.getEncoder().encodeToString(payloadData);
    }
}
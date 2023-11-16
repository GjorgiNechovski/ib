package staticClasses;

import exceptions.IntegrityCompromised;
import frames.ClearTextFrame;
import frames.EncryptedFrame;
import frames.Header;

import java.math.BigInteger;
import java.util.Arrays;

public class CCMPLogic {
    public static EncryptedFrame encryptFrame(ClearTextFrame frame, String keySecret) {
        byte[][] payloadBlocks = splitDataInBlocks(frame.getPayloadData());

        byte[] IV = AES.encrypt(frame.getNonce(), keySecret);

        byte[] MIC = calculateMIC(frame.getFrameHeader(), IV, frame.getPayloadData(), keySecret);
        BigInteger counter = BigInteger.valueOf(0);

        byte[] encryptedPayloadData = new byte[frame.getPayloadData().length];

        int currentIndex = 0;

        for (byte[] payloadBlock : payloadBlocks) {
            byte[] counterBytes = bigIntegerToBytes(counter);
            byte[] xorResult = xorBytesArray(counterBytes, payloadBlock);

            int xorResultLength = Math.min(16, encryptedPayloadData.length - currentIndex);

            System.arraycopy(xorResult, 0, encryptedPayloadData, currentIndex, xorResultLength);
            currentIndex += xorResultLength;
            counter = counter.add(BigInteger.ONE);
        }

        return new EncryptedFrame(frame.getFrameHeader(), frame.getNonce(), encryptedPayloadData, MIC);
    }

    public static ClearTextFrame decryptFrame(EncryptedFrame frame, String keySecret) throws IntegrityCompromised {
        byte[] clearTextPayloadData = new byte[frame.getPayloadData().length];
        BigInteger counter = BigInteger.valueOf(0);
        int currentIndex = 0;

        for (int i = 0; i < clearTextPayloadData.length; i += 16) {
            byte[] counterBytes = bigIntegerToBytes(counter);
            byte[] xorResult = xorBytesArray(counterBytes, Arrays.copyOfRange(frame.getPayloadData(), i, i + 16));

            int xorResultLength = Math.min(16, clearTextPayloadData.length - currentIndex);

            System.arraycopy(xorResult, 0, clearTextPayloadData, currentIndex, xorResultLength);
            currentIndex += xorResultLength;
            counter = counter.add(BigInteger.ONE);
        }

        byte[] IV = AES.encrypt(frame.getNonce(), keySecret);

        byte[] calculatedMIC = calculateMIC(frame.getFrameHeader(), IV, clearTextPayloadData, keySecret);

        if (Arrays.equals(calculatedMIC, frame.getMIC())) {
            return new ClearTextFrame(
                    new String(clearTextPayloadData),
                    new String(frame.getFrameHeader().getSourceMAC()),
                    new String(frame.getFrameHeader().getDestinationMAC())
            );
        } else {
            throw new IntegrityCompromised();
        }
    }


    public static byte[] calculateMIC(Header header, byte[] nonce, byte[] payloadData, String keySecret) {
        byte[] IV = Arrays.copyOf(nonce, 16);

        byte[] sourceAddress = Arrays.copyOfRange(header.getSourceMAC(), 0, 16);
        byte[] destinationAddress = Arrays.copyOfRange(header.getDestinationMAC(), 0, 16);

        IV = xorBytesArray(sourceAddress, IV);
        IV = AES.encrypt(IV, keySecret);

        IV = xorBytesArray(destinationAddress, IV);
        IV = AES.encrypt(IV, keySecret);

        byte[][] payloadBlocks = splitDataInBlocks(payloadData);

        for (byte[] block : payloadBlocks) {
            IV = xorBytesArray(block, IV);
            IV = AES.encrypt(IV, keySecret);
        }

        return Arrays.copyOfRange(IV, 0, 8);
    }

    public static byte[][] splitDataInBlocks(byte[] payloadData) {
        int blockCount = (payloadData.length + 15) / 16;
        byte[][] blocks = new byte[blockCount][16];

        for (int i = 0; i < blockCount; i++) {
            for (int j = 0; j < 16; j++) {
                int index = i * 16 + j;
                if (index < payloadData.length) {
                    blocks[i][j] = payloadData[index];
                }
            }
        }

        return blocks;
    }

    public static byte[] xorBytesArray(byte[] data, byte[] IV) {
        if (data.length != IV.length) {
            throw new IllegalArgumentException("Input arrays must be of the same length");
        }

        byte[] result = new byte[data.length];

        for (int i = 0; i < data.length; i++) {
            result[i] = (byte) (data[i] ^ IV[i]);
        }

        return result;
    }

    public static byte[] bigIntegerToBytes(BigInteger value) {
        byte[] bytes = value.toByteArray();
        if (bytes.length == 16) {
            return bytes;
        }
        byte[] paddedBytes = new byte[16];
        System.arraycopy(bytes, 0, paddedBytes, 16 - bytes.length, bytes.length);
        return paddedBytes;

    }

}
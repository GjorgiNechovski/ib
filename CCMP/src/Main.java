import frames.ClearTextFrame;
import frames.EncryptedFrame;
import staticClasses.CCMPLogic;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        String sourceMAC = "1f:66:a0:8f:0d:13";
        String destinationMAC = "0c:d7:f2:a0:03:ba";

        String key = "KEY FOR CCMP";

        System.out.print("Enter your message: ");
        String message = scanner.nextLine();

        ClearTextFrame clearTextFrame = new ClearTextFrame(message,sourceMAC,destinationMAC);

        EncryptedFrame encryptedFrame = CCMPLogic.encryptFrame(clearTextFrame,key);
        System.out.println(encryptedFrame + "\n");

        ClearTextFrame decryptedFrame = CCMPLogic.decryptFrame(encryptedFrame,key);
        System.out.println(decryptedFrame);
    }
}
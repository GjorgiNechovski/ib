package exceptions;

public class IntegrityCompromised extends Exception {
    public IntegrityCompromised() {
        super("MICs aren't the same, Integrity compromised!");
    }
}

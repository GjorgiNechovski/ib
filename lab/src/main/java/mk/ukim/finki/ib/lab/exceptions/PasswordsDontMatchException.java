package mk.ukim.finki.ib.lab.exceptions;

public class PasswordsDontMatchException extends Exception {
    public PasswordsDontMatchException() {
        super("The passwords don't match please try again");
    }
}

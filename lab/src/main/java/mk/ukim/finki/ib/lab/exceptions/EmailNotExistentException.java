package mk.ukim.finki.ib.lab.exceptions;

public class EmailNotExistentException extends Exception{
    public EmailNotExistentException() {
        super("The email does not exist");
    }
}

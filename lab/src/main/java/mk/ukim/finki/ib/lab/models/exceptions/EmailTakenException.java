package mk.ukim.finki.ib.lab.models.exceptions;

public class EmailTakenException extends Exception{
    public EmailTakenException() {
        super("That email address is already in use!");
    }
}

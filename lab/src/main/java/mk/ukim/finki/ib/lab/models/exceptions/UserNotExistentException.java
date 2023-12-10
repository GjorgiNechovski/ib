package mk.ukim.finki.ib.lab.models.exceptions;

public class UserNotExistentException extends Exception{
    public UserNotExistentException() {
        super("User does not exist!");
    }
}

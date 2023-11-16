package mk.ukim.finki.ib.lab.exceptions;

public class UserNameExistsException extends Exception{
    public UserNameExistsException() {
        super("That username already exists, please pick another!");
    }
}

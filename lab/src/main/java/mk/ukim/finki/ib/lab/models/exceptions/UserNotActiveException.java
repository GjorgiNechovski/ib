package mk.ukim.finki.ib.lab.models.exceptions;

public class UserNotActiveException extends RuntimeException{
    public UserNotActiveException() {
        super("You haven't activated your account yet!");
    }
}

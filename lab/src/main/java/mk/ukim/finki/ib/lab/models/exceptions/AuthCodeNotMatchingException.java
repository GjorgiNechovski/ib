package mk.ukim.finki.ib.lab.models.exceptions;

public class AuthCodeNotMatchingException extends Exception{
    public AuthCodeNotMatchingException() {
        super("Your code is incorrect!");
    }
}

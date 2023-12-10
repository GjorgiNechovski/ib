package mk.ukim.finki.ib.lab.models.exceptions;

public class TokensDoNotMatchException extends Exception{
    public TokensDoNotMatchException() {
        super("Tokens do not match!");
    }
}

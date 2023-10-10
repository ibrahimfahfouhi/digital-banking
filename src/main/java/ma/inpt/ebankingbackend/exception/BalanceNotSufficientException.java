package ma.inpt.ebankingbackend.exception;

public class BalanceNotSufficientException extends Throwable{
    public BalanceNotSufficientException(String message) {
        super(message);
    }
}

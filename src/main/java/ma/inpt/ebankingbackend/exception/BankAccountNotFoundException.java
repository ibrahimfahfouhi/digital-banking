package ma.inpt.ebankingbackend.exception;

public class BankAccountNotFoundException extends Throwable{
    public BankAccountNotFoundException(String message) {
        super(message);
    }
}

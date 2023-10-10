package ma.inpt.ebankingbackend.exception;

public class CustomerNotFoundException extends Throwable{
    public CustomerNotFoundException(String message) {
        super(message);
    }
}

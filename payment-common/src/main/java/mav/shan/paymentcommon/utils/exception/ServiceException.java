package mav.shan.paymentcommon.utils.exception;

public class ServiceException extends RuntimeException{
    protected String message;

    public ServiceException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

package org.example.exceptions;

public class EntityNotFound extends BaseException{
    private static String exp = "ENTITY_NOT_FOUND";

    @Override
    public int getStatus() {
        return 404;
    }

    public EntityNotFound() {
        super();
    }

    public EntityNotFound(String message) {
        super(exp,message);
    }

    public EntityNotFound(String message, Throwable cause) {
        super(exp,cause,message);
    }

    public EntityNotFound(Throwable cause) {
        super(cause, exp);
    }

    public EntityNotFound(Throwable cause, String code) {
        super(cause, code);
    }

    public EntityNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(exp,cause, writableStackTrace, enableSuppression, message);
    }
}

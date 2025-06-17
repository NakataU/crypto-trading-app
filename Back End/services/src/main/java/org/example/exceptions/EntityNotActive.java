package org.example.exceptions;

public class EntityNotActive extends BaseException{
    private static String exp = "ENTITY_NOT_ACTIVE";

    @Override
    public int getStatus() {
        return 404;
    }

    public EntityNotActive() {
        super();
    }

    public EntityNotActive(String message) {
        super(exp,message);
    }

    public EntityNotActive(String message, Throwable cause) {
        super(exp,cause,message);
    }

    public EntityNotActive(Throwable cause) {
        super(cause, exp);
    }

    public EntityNotActive(Throwable cause, String code) {
        super(cause, code);
    }

    public EntityNotActive(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(exp,cause, writableStackTrace, enableSuppression, message);
    }
}

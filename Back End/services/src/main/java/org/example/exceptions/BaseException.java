package org.example.exceptions;

public class BaseException extends Exception{
    protected String code = "BASE_EXCEPTION_MESSAGE";
    private int status = 500;

    public int getStatus() {
        return status;
    }

    public BaseException(){}

    public BaseException(String code) {
        this.code = code;
    }

    public BaseException(String message, String code) {
        super(message);
        this.code = code;
    }

    public BaseException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
    }

    public BaseException(Throwable cause, String code) {
        super(cause);
        this.code = code;
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

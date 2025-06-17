package org.example.exceptions;

public class SomethingIsWrong extends BaseException{
    private static String exp = "SOMETHING_IS_WRONG";

    @Override
    public int getStatus() {
        return 500;
    }

    public SomethingIsWrong() {
        super();
    }

    public SomethingIsWrong(String message) {
        super(exp,message);
    }

    public SomethingIsWrong(String message, Throwable cause) {
        super(exp,cause,message);
    }

    public SomethingIsWrong(Throwable cause) {
        super(cause, exp);
    }

    public SomethingIsWrong(Throwable cause, String code) {
        super(cause, code);
    }

    public SomethingIsWrong(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(exp,cause, writableStackTrace, enableSuppression, message);
    }
}

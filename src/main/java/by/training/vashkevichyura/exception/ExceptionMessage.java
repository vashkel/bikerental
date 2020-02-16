package by.training.vashkevichyura.exception;

public enum ExceptionMessage {

    LOGIN_PASSWORD("loginOrPasswordWrong"),

    VALIDATION_ERROR("validationError");

    private String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}

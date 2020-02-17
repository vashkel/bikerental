package by.training.vashkevichyura.exception;

public enum ExceptionMessage {

    LOGIN_PASSWORD("loginOrPasswordWrong"),
    ORDER_CLOSE("orderClose"),
    ORDER_NOT_EXIST("orderNotExist"),
    VALIDATION_ERROR("validationError");

    private String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}

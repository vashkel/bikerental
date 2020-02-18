package by.training.vashkevichyura.exception;

public enum ExceptionMessage {

    LOGIN_PASSWORD("loginOrPasswordWrong"),
    NOT_ENOUGH_MONEY("notEnoughMoney"),
    ORDER_NOT_EXIST("orderNotExist"),
    VALIDATION_ERROR("validationError"),
    NOT_FREE_BIKE("notFreeBike");
    private String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}

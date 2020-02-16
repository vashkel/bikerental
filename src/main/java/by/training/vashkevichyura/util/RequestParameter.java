package by.training.vashkevichyura.util;

public enum RequestParameter {
    ADDRESS("address"),

    BIKE("bike"),
    BIKE_ID("bikeId"),
    ORDER("order"),
    BIKE_TYPE_LIST("bikeTypesList"),
    BIKE_TYPE_ID("bikeTypeId"),
    BIKE_TYPE("bikeType"),
    BIKE_LIST("bikeList"),
    BRAND("brand"),
    BRAND_ID("brandId"),
    BRAND_LIST("brandsList"),
    EMAIL("email"),
    ERROR("error"),
    LOGIN_MENU("loginMenu"),
    LOGIN("login"),
    MODEL("model"),
    NAME("name"),
    NEXT_PAGE("next"),
    PASSWORD("password"),
    PAGE_ACTION("pageAction"),
    PREVIOUS_PAGE("previous"),
    RENTAL_POINT_LIST("rentalPointList"),
    RENTAL_POINT_ID("rentalPointId"),
    SURNAME("surname"),
    USERS_LIST("usersList"),
    ORDER_LIST("orderList"),
    TOTAL_PRICE("totalPrice"),
    ORDER_LIST_ALL_USERS ("orderListAllUsers");

    private String parameter;

    RequestParameter(String parameter) {
        this.parameter = parameter;
    }

    /**
     * @return String representation of the parameter.
     */
    public String parameter() {
        return parameter;
    }
}

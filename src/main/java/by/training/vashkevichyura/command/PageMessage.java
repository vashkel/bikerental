package by.training.vashkevichyura.command;

public enum PageMessage {
    /**
     *  Parking was added, has a message - parkingAdded.
     */
    RENTAL_POINT_ADDED("rentalPointAdded"),

    /**
     * Parking was changed, has a message - parkingChanged.
     */
    PARKING_CHANGED("parkingChanged"),

    /**
     *  Bike was added, has a message - bikeAdded.
     */
    BIKE_ADDED("bikeAdded"),

    /**
     * Bike was changed, has a message - bikeChanged.
     */
    BIKE_CHANGED("bikeChanged"),

    /**
     * Profile was changed, has a message - profileChanged.
     */
    PROFILE_CHANGED("profileChanged"),

    /**
     * Password was changed, has a message - passwordChanged.
     */
    PASSWORD_CHANGED("passwordChanged"),

    /**
     * User was added, has a message - userAdded.
     */
    USER_ADDED("userAdded"),

    USER_DELETED("userDeleted"),

    USER_STATE_CHANGED("userStateChanged");

    private String message;

    PageMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }

}

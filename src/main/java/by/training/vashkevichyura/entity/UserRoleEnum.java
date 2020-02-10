package by.training.vashkevichyura.entity;

import by.training.vashkevichyura.command.AccessLevel;
import by.training.vashkevichyura.command.PageConstant;

public enum UserRoleEnum {
    ADMIN(PageConstant.ADMIN_PAGE, AccessLevel.ADMIN),
    USER(PageConstant.USER_PAGE, AccessLevel.USER);

    private String homePage;
    private AccessLevel accessLevel;

    UserRoleEnum(String homePage, AccessLevel accessLevel) {
        this.homePage = homePage;
        this.accessLevel = accessLevel;
    }

    public String getHomePage() {
        return homePage;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

}

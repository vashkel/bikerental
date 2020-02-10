package by.training.vashkevichyura.command;

import by.training.vashkevichyura.command.impl.aplication.ChangeLocalizationCommand;
import by.training.vashkevichyura.command.impl.aplication.GoToHomePageCommand;
import by.training.vashkevichyura.command.impl.aplication.PaginationCommand;
import by.training.vashkevichyura.command.impl.bike.AddBikeCommand;
import by.training.vashkevichyura.command.impl.bike.BikeCatalogCommand;
import by.training.vashkevichyura.command.impl.bike.GoToAddBikePageCommand;
import by.training.vashkevichyura.command.impl.bike.GoToBikeProductCatalogPageCommand;
import by.training.vashkevichyura.command.impl.order.FindUserOrdersCommand;
import by.training.vashkevichyura.command.impl.order.AllOrdersPageCommand;
import by.training.vashkevichyura.command.impl.rentalpoint.RentalPointCatalogCommand;
import by.training.vashkevichyura.command.impl.user.GetAllUsersCommand;
import by.training.vashkevichyura.command.impl.user.LoginCommand;
import by.training.vashkevichyura.command.impl.user.LogoutCommand;
import by.training.vashkevichyura.command.impl.user.RegisterCommand;

public enum CommandEnum {
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    REGISTER(new RegisterCommand()),
    CHANGE_LOCALIZATION(new ChangeLocalizationCommand()),
    GO_TO_BIKE_PRODUCT_CATALOG_PAGE(new GoToBikeProductCatalogPageCommand()),
    PAGINATION(new PaginationCommand()),
    BIKECATALOG(new BikeCatalogCommand()),
    GET_ALL_USER(new GetAllUsersCommand()),
    ADD_BIKE(new AddBikeCommand()),
    GO_TO_ADD_BIKE_PAGE(new GoToAddBikePageCommand()),
    GO_TO_HOME_PAGE(new GoToHomePageCommand()),
    RENTAL_POINTS_PAGE(new RentalPointCatalogCommand()),
    FIND_USER_ORDERS(new FindUserOrdersCommand()),
    ALL_ORDERS_PAGE(new AllOrdersPageCommand());

    /**
     * Command object
     */
    private ActionCommand command;

    CommandEnum(ActionCommand command) {
        this.command = command;
    }

    public ActionCommand getCommand() {
        return command;
    }

}

package by.training.vashkevichyura.command;

import by.training.vashkevichyura.command.impl.application.ChangeLocalizationCommand;
import by.training.vashkevichyura.command.impl.application.GoToHomePageCommand;
import by.training.vashkevichyura.command.impl.application.PaginationCommand;
import by.training.vashkevichyura.command.impl.bike.AddBikeCommand;
import by.training.vashkevichyura.command.impl.bike.BikeCatalogCommand;
import by.training.vashkevichyura.command.impl.order.CreateOrderCommand;
import by.training.vashkevichyura.command.impl.bike.GetPriceBikeCommand;
import by.training.vashkevichyura.command.impl.bike.GoToAddBikePageCommand;
import by.training.vashkevichyura.command.impl.order.OrderBikeCommand;
import by.training.vashkevichyura.command.impl.order.AllOrdersPageCommand;
import by.training.vashkevichyura.command.impl.order.CloseOrderCommand;
import by.training.vashkevichyura.command.impl.order.FindUserOrdersCommand;
import by.training.vashkevichyura.command.impl.rentalpoint.RentalPointCatalogCommand;
import by.training.vashkevichyura.command.impl.user.ChangeStateUserCommand;
import by.training.vashkevichyura.command.impl.user.DeleteUserCommand;
import by.training.vashkevichyura.command.impl.user.GetAllUsersCommand;
import by.training.vashkevichyura.command.impl.user.LoginCommand;
import by.training.vashkevichyura.command.impl.user.LogoutCommand;
import by.training.vashkevichyura.command.impl.user.RegisterCommand;

public enum CommandEnum {
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    REGISTER(new RegisterCommand()),
    CHANGE_LOCALIZATION(new ChangeLocalizationCommand()),
    GO_TO_BIKE_PRODUCT_CATALOG_PAGE(new CreateOrderCommand()),
    PAGINATION(new PaginationCommand()),
    BIKECATALOG(new BikeCatalogCommand()),
    GET_ALL_USER(new GetAllUsersCommand()),
    ADD_BIKE(new AddBikeCommand()),
    GO_TO_ADD_BIKE_PAGE(new GoToAddBikePageCommand()),
    GO_TO_HOME_PAGE(new GoToHomePageCommand()),
    RENTAL_POINTS_PAGE(new RentalPointCatalogCommand()),
    FIND_USER_ORDERS(new FindUserOrdersCommand()),
    DELETE_USER(new DeleteUserCommand()),
    CHANGE_STATE_USER(new ChangeStateUserCommand()),
    CREATE_ORDER(new CreateOrderCommand()),
    GET_PRICE_BIKE(new GetPriceBikeCommand()),
    ORDER_BIKE(new OrderBikeCommand()),
    CLOSE_ORDER(new CloseOrderCommand()),
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

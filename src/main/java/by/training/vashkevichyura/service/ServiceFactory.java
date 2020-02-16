package by.training.vashkevichyura.service;

import by.training.vashkevichyura.service.impl.BikeServiceImpl;
import by.training.vashkevichyura.service.impl.BikeTypeServiceImpl;
import by.training.vashkevichyura.service.impl.OrderServiceImpl;
import by.training.vashkevichyura.service.impl.RentalCostServiceImpl;
import by.training.vashkevichyura.service.impl.RentalPointServiceImpl;
import by.training.vashkevichyura.service.impl.UserServiceImpl;

public class ServiceFactory {

    private static final ServiceFactory instance = new ServiceFactory();

    private static final UserService userService = new UserServiceImpl();
    private static final OrderService orderService = new OrderServiceImpl();
    private static final BikeService bikeService = new BikeServiceImpl();
    private static final BikeTypeService bikeTypeService = new BikeTypeServiceImpl();
    private static final RentalPointService rentalPointService = new RentalPointServiceImpl();
    private static final RentalCostService rentalCostService = new RentalCostServiceImpl();

    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public BikeService getBikeService() {
        return bikeService;
    }

    public BikeTypeService getBikeTypeService() {
        return bikeTypeService;
    }

    public RentalPointService getRentalPointService() {
        return rentalPointService;
    }

    public  RentalCostService getRentalCostService() {
        return rentalCostService;
    }
}

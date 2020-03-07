package by.training.vashkevichyura.service;

import by.training.vashkevichyura.dao.DAOFactory;
import by.training.vashkevichyura.generator.HashGenerator;
import by.training.vashkevichyura.service.impl.BikeServiceImpl;
import by.training.vashkevichyura.service.impl.BikeTypeServiceImpl;
import by.training.vashkevichyura.service.impl.OrderServiceImpl;
import by.training.vashkevichyura.service.impl.RentalCostServiceImpl;
import by.training.vashkevichyura.service.impl.RentalPointServiceImpl;
import by.training.vashkevichyura.service.impl.UserServiceImpl;

public class ServiceFactory {


    private static UserService userService;
    private static OrderService orderService;
    private static BikeService bikeService;
    private static BikeTypeService bikeTypeService;
    private static RentalPointService rentalPointService;
    private static RentalCostService rentalCostService;


    public  static UserService getUserService() {
        if(userService==null){
            userService = new UserServiceImpl(DAOFactory.getUserDAO(), new HashGenerator());
        }
        return userService;
    }

    public static OrderService getOrderService() {
        if(orderService == null){
            orderService  = new OrderServiceImpl();
        }
        return orderService;
    }

    public static BikeService getBikeService() {
        if (bikeService == null){
            bikeService = new BikeServiceImpl();
        }
        return bikeService;
    }

    public static BikeTypeService getBikeTypeService() {
        if(bikeTypeService == null){
            bikeTypeService  = new BikeTypeServiceImpl();
        }
        return bikeTypeService;
    }

    public static RentalPointService getRentalPointService() {
        if (rentalPointService == null){
            rentalPointService = new RentalPointServiceImpl();
        }
        return rentalPointService;
    }

    public static RentalCostService getRentalCostService() {
        if(rentalCostService == null){
            rentalCostService = new RentalCostServiceImpl();
        }
        return rentalCostService;
    }
}

package by.training.vashkevichyura.dao;

import by.training.vashkevichyura.dao.Impl.*;

public class DAOFactory {

    private static BikeDAOImpl bikeDAO;
    private static BikeTypeDAOImpl bikeTypeDAO;
    private static OrderDAOImpl orderDAO;
    private static RentalCostDAOImpl rentalCostDAO;
    private static RentalPointDAOImpl rentalPointDAO;
    private static UserDAOImpl userDAO;

    public static BikeDAOImpl getBikeDAO() {
        if(bikeDAO == null){
            bikeDAO = new BikeDAOImpl();
        }
        return bikeDAO;
    }

    public static OrderDAOImpl getOrderDAO() {
        if(orderDAO == null){
           orderDAO = new OrderDAOImpl();
        }
        return orderDAO;
    }

    public static BikeTypeDAOImpl getBikeTypeDAO() {
        if(bikeTypeDAO == null){
            bikeTypeDAO  = new BikeTypeDAOImpl();
        }
        return bikeTypeDAO;
    }

    public static RentalCostDAOImpl getRentalCostDAO() {
        if(rentalCostDAO == null){
            rentalCostDAO = new RentalCostDAOImpl();
        }
        return rentalCostDAO;
    }

    public static RentalPointDAOImpl getRentalPointDAO() {
        if(rentalPointDAO == null){
            rentalPointDAO  = new RentalPointDAOImpl();
        }
        return rentalPointDAO;
    }

    public static UserDAOImpl getUserDAO(){
        if(userDAO == null){
            userDAO = new UserDAOImpl();
        }
        return userDAO;
    }
}

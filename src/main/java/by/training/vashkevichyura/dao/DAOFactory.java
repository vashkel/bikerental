package by.training.vashkevichyura.dao;

import by.training.vashkevichyura.dao.Impl.*;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();

    public static DAOFactory getInstance(){
        return instance;
    }
    private static final BikeDAOImpl bikeDAO = new BikeDAOImpl();
    private static final BikeTypeDAOImpl bikeTypeDAO = new BikeTypeDAOImpl();
    private static final OrderDAOImpl orderDAO = new OrderDAOImpl();
    private static final RentalCostDAOImpl rentalCostDAO = new RentalCostDAOImpl();
    private static final RentalPointDAOImpl rentalPointDAO = new RentalPointDAOImpl();
    private static final UserDAOImpl userDAO = new UserDAOImpl();

    public BikeDAOImpl getBikeDAO() {
        return bikeDAO;
    }

    public OrderDAOImpl getOrderDAO() {
        return orderDAO;
    }

    public BikeTypeDAOImpl getBikeTypeDAO() {
        return bikeTypeDAO;
    }

    public RentalCostDAOImpl getRentalCostDAO() {
        return rentalCostDAO;
    }

    public RentalPointDAOImpl getRentalPointDAO() {
        return rentalPointDAO;
    }

    public UserDAOImpl getUserDAO(){
        return userDAO;
    }
}

package by.training.vashkevichyura.dao;

import by.training.vashkevichyura.dao.Impl.*;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();

    public static DAOFactory getInstance(){
        return instance;
    }
   private final BikeDAO bikeDAO = new BikeDAOImpl();
   private final BikeTypeDAO bikeTypeDAO = new BikeTypeDAOImpl();
   private final OrderDAO orderDAO = new OrderDAOImpl();
    private final RentalCostDAO rentalCostDAO = new RentalCostDAOImpl();
    private final RentalPointDAO rentalPointDAO = new RentalPointDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();

    public BikeDAO getBikeDAO() { return bikeDAO; }

    public OrderDAO getOrderDAO() { return orderDAO; }

    public BikeTypeDAO getBikeTypeDAO() { return bikeTypeDAO; }

    public RentalCostDAO getRentalCostDAO() { return rentalCostDAO; }

    public RentalPointDAO getRentalPointDAO() { return rentalPointDAO; }

    public UserDAO getUserDAO(){return userDAO;}
}

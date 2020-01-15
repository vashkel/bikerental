package by.training.vashkevichyura.connection;

import by.training.vashkevichyura.dao.*;
import by.training.vashkevichyura.entity.*;
import by.training.vashkevichyura.exception.ConnectionPoolException;
import by.training.vashkevichyura.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

public class ConnectionImpr {
    public static void main(String[] args) throws DAOException {

        BikeType bikeType = new BikeType();
        BikeTypeDAO bikeTypeDAO = DAOFactory.getInstance().getBikeTypeDAO();
        bikeType = bikeTypeDAO.getById(1);


//        TODO проверил только метод добавления,проверить остальные!
//        RentalCost rentalCost = new RentalCost();
//        RentalCostDAO rentalCostDAO = DAOFactory.getInstance().getRentalCostDAO();
//        rentalCost = rentalCostDAO.getById(3);

        RentalPoint rentalPoint = new RentalPoint();
        RentalPointDAO rentalPointDAO = DAOFactory.getInstance().getRentalPointDAO();
       rentalPoint = rentalPointDAO.getById(3);

//

        Bike bike = new Bike();
        bike.setBrand("Stinger");
        bike.setModel("Defender");
        bike.setBikeType(bikeType);
        bike.setRentalPoint(rentalPoint);
        bike.setBikeStatus(BikeStatusEnum.FREE);

        BikeDAO bikeDAO = DAOFactory.getInstance().getBikeDAO();
        bikeDAO.add(bike);


        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT *FROM users WHERE name=?")){
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE users SET name=? WHERE id=?");

            //SELECT ALL MALES
            statement.setString(1,"petr");


            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                LOGGER.info(rs.getRow() + " " + rs.getString("surname"));

            }
            //changed name by number id
            updateStatement.setString(1,"Petr");
            updateStatement.setInt(2,1);
            updateStatement.executeUpdate();



        } catch (SQLException | ConnectionPoolException e) {
            e.printStackTrace();
        }
    }
}

package by.training.vashkevichyura.dao;

import by.training.vashkevichyura.entity.Bike;
import by.training.vashkevichyura.exception.DAOException;

import java.util.List;

public interface BikeDAO extends AbstractDAO<Bike> {

    List<Bike> getAllBikes()throws DAOException;
    void updateBike(Bike bike) throws DAOException;
    void deleteBike(Bike bike) throws DAOException;

}

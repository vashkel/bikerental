package by.training.vashkevichyura.dao;

import by.training.vashkevichyura.entity.RentalPoint;
import by.training.vashkevichyura.exception.DAOException;

import java.util.List;

public interface RentalPointDAO extends AbstractDAO<RentalPoint> {
    List<RentalPoint> getAllRentalPoints() throws DAOException;

    void updateRentalPoint(RentalPoint rentalPoint)throws DAOException;

    void deleteRentalPoint(RentalPoint rentalPoint) throws DAOException;
}

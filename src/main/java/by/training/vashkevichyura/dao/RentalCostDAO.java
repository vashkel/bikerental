package by.training.vashkevichyura.dao;

import by.training.vashkevichyura.entity.RentalCost;
import by.training.vashkevichyura.exception.DAOException;

public interface RentalCostDAO extends AbstractDAO<RentalCost>{


    double getPriceByBikeTypeId(long bikeTypeId) throws DAOException;
}

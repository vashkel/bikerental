package by.training.vashkevichyura.dao;

import by.training.vashkevichyura.entity.RentalCost;
import by.training.vashkevichyura.exception.DAOException;

import java.util.List;

public interface RentalCostDAO extends AbstractDAO<RentalCost>{

    List<RentalCost> getAllRentalCosts() throws DAOException;

    void updateRentalCost(RentalCost rentalCost)throws DAOException;

    void deleteRentalCost(RentalCost rentalCost) throws DAOException;
}

package by.training.vashkevichyura.dao;

import by.training.vashkevichyura.entity.BikeType;
import by.training.vashkevichyura.exception.DAOException;

import java.util.List;

public interface BikeTypeDAO extends AbstractDAO<BikeType>{

    List<BikeType> getAllBikeType() throws DAOException;

    void update(BikeType bikeType)throws DAOException;

    void delete(BikeType bikeType) throws DAOException;
}

package by.training.vashkevichyura.dao;

import by.training.vashkevichyura.entity.Bike;
import by.training.vashkevichyura.exception.DAOException;
import by.training.vashkevichyura.util.PageInfo;

import java.util.List;

public interface BikeDAO extends AbstractDAO<Bike> {

    /**
     * @param pageInfo of the PageInfo with parameters for the LIMIT operator
     * @return List of bikes
     * @throws DAOException
     */
    List<Bike> getAllBikeByLimit(PageInfo pageInfo) throws DAOException;


    /**
     * method gets all brands of bike
     * @return List of bikes brand
     * @throws DAOException
     */
    List<String> getAllBrandBike() throws DAOException;

    /**
     * method gets bike by type and rental pointId
     * @return appropriate bike
     * @throws DAOException
     */
    Bike getBikeByTypeAndRentalPointId(long bikeTypeId, long rentalPointId) throws DAOException;

    /**
     * method changes status of bike on busy for unavailable getting current bike with another user
     * @return void
     * @throws DAOException
     */
    void changeBikeStatusBusy(Bike bike) throws DAOException;
}

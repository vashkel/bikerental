package by.training.vashkevichyura.service;

import by.training.vashkevichyura.entity.Bike;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.util.PageInfo;

import java.util.List;

public interface BikeService {
    List<Bike> getAllBike(PageInfo pageInfo) throws ServiceException;

    List<String> getAllBikeBrand() throws ServiceException;

    Bike getBikeById(long id) throws ServiceException;

    void addBike(Bike bike) throws ServiceException;

    void addSomeBikes(Bike bike, int countBike) throws ServiceException;

    Bike getBikeByTypeAndRentalPointId(long bikeTypeId, long rentalPointId) throws ServiceException;

    void changeStatusById(long bikeId, String status) throws ServiceException;

    void deleteBikeById(long bikeId) throws ServiceException ;
}

package by.training.vashkevichyura.service;

import by.training.vashkevichyura.exception.ServiceException;

public interface RentalCostService {


    double getPriceByBikeTypeId(long bikeTypeId) throws ServiceException;
}

package by.training.vashkevichyura.service;

import by.training.vashkevichyura.entity.RentalPoint;
import by.training.vashkevichyura.exception.ServiceException;

import java.util.List;

public interface RentalPointService {

    List<RentalPoint> getRentalPoints() throws ServiceException;

    RentalPoint getById(long id) throws ServiceException;
}

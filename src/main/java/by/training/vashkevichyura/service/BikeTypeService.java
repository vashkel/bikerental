package by.training.vashkevichyura.service;

import by.training.vashkevichyura.entity.BikeType;
import by.training.vashkevichyura.exception.ServiceException;

import java.util.List;

public interface BikeTypeService {

    List<BikeType> getBikeTypes() throws ServiceException;
}

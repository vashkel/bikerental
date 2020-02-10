package by.training.vashkevichyura.service.impl;

import by.training.vashkevichyura.dao.BikeDAO;
import by.training.vashkevichyura.dao.DAOFactory;
import by.training.vashkevichyura.entity.Bike;
import by.training.vashkevichyura.exception.DAOException;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.BikeService;
import by.training.vashkevichyura.util.PageInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BikeServiceImpl implements BikeService {

    private static final Logger LOGGER = LogManager.getLogger(BikeServiceImpl.class);
    private BikeDAO bikeDAO = DAOFactory.getInstance().getBikeDAO();

    @Override
    public List<Bike> getAllBike(PageInfo pageInfo) throws ServiceException {
        List<Bike> bikes;
        try {
            bikes = bikeDAO.getAllBikeByLimit(pageInfo);
        } catch (DAOException e) {
            throw new ServiceException("get All bikes error ", e);
        }
        return bikes;
    }

    @Override
    public List<String> getAllBikeBrand() throws ServiceException {
        List<String> brands;
        try {
            brands = bikeDAO.getAllBrandBike();
        } catch (DAOException e) {
            throw new ServiceException("get brands of Bike error " + e);
        }

        return brands;
    }

    @Override
    public Bike getBikeById(long id) throws ServiceException {
        Bike bike;
        try {
            bike = bikeDAO.getById(id);
        } catch (DAOException e) {
            throw new ServiceException("getBikeById error " + e);
        }
        return bike;
    }


    @Override
    public void addBike(Bike bike) throws ServiceException {
        try {
            bikeDAO.add(bike);
        } catch (DAOException e) {
            LOGGER.error("Add bike Exception: " + e);
            throw new ServiceException("Exception occurred while adding bike: " + e.getMessage());
        }
    }


}

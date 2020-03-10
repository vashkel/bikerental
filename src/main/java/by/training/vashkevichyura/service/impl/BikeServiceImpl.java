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
    private BikeDAO bikeDAO = DAOFactory.getBikeDAO();


    @Override
    public List<Bike> getAllBike(PageInfo pageInfo) throws ServiceException {
        List<Bike> bikes;
        try {
            bikes = bikeDAO.getAllBikeByLimit(pageInfo);
        } catch (DAOException e) {
            LOGGER.error("get All bikes error " + e);
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
            LOGGER.error("get all brands of Bike error " + e);
            throw new ServiceException("get all brands of Bike error " + e.getMessage());
        }

        return brands;
    }


    @Override
    public Bike getBikeById(long id) throws ServiceException {
        Bike bike;
        try {
            bike = bikeDAO.getById(id);
        } catch (DAOException e) {
            LOGGER.error("getBikeById error " + e);
            throw new ServiceException("getBikeById error " + e.getMessage());
        }
        return bike;
    }


    @Override
    public void addBike(Bike bike) throws ServiceException {
        try {
            bikeDAO.add(bike);
        } catch (DAOException e) {
            LOGGER.error("Exception occurred while adding bike: " + e);
            throw new ServiceException("Exception occurred while adding bike: " + e.getMessage());
        }
    }

    @Override
    public void addSomeBikes(Bike bike, int countBike) throws ServiceException {
        try {
            bikeDAO.addSomeBikes(bike, countBike);
        } catch (DAOException e) {
            LOGGER.error("Add some bikes Exception: " + e);
            throw new ServiceException("Add some bikes Exception: " + e.getMessage());
        }
    }

    @Override
    public Bike getBikeByTypeAndRentalPointId(long bikeTypeId, long rentalPointId) throws ServiceException {
        Bike bike;
        try {
            bike = bikeDAO.getBikeByTypeAndRentalPointId(bikeTypeId, rentalPointId);
            if (bike != null) {
                bikeDAO.changeBikeStatusOnBusy(bike);
            }
        } catch (DAOException e) {
            LOGGER.error("getBikeByTypeAndRentalPointId " + e);
            throw new ServiceException("getBikeById error " + e.getMessage());
        }
        return bike;
    }

    @Override
    public void changeStatusById(long bikeId, String status) throws ServiceException {
        try {
            bikeDAO.changeStatusById(bikeId, status);
        } catch (DAOException e) {
            LOGGER.error("Exception occurred while change bike status by id: " + e);
            throw new ServiceException("Exception occurred while change bike status by id: " + e.getMessage());
        }
    }

    @Override
    public void deleteBikeById(long bikeId) throws ServiceException {
        Bike bike;
        try {
            bike = bikeDAO.getById(bikeId);
            bikeDAO.delete(bike);
        } catch (DAOException e) {
            LOGGER.error("Exception occurred while delete bike by id: " + e);
            throw new ServiceException("Exception occurred while delete bike by id: " + e.getMessage());
        }
    }


}

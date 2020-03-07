package by.training.vashkevichyura.service.impl;

import by.training.vashkevichyura.dao.DAOFactory;
import by.training.vashkevichyura.dao.RentalPointDAO;
import by.training.vashkevichyura.entity.RentalPoint;
import by.training.vashkevichyura.exception.DAOException;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.RentalPointService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class RentalPointServiceImpl implements RentalPointService {

    private static final Logger LOGGER = LogManager.getLogger(RentalPointServiceImpl.class);
    private RentalPointDAO rentalPointDAO = DAOFactory.getRentalPointDAO();

    @Override
    public List<RentalPoint> getRentalPoints() throws ServiceException {
        List<RentalPoint> rentalPoints;
        try {
            rentalPoints = rentalPointDAO.getAll();
        } catch (DAOException e) {
            LOGGER.error("Exception occurred while retrieving rental points", e);
            throw new ServiceException("Exception occurred while retrieving rental points: " + e.getMessage());
        }
        return rentalPoints;
    }

    @Override
    public RentalPoint getById(long id) throws ServiceException {
        RentalPoint rentalPoint;
        try {
            rentalPoint = rentalPointDAO.getById(id);
        } catch (DAOException e) {
            LOGGER.error("Exception occurred while retrieving rental point by id ", e);
            throw new ServiceException("Exception occurred while retrieving rental point by id : " + e.getMessage());
        }
        return rentalPoint;
    }
}

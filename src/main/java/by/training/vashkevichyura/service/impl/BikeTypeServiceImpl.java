package by.training.vashkevichyura.service.impl;

import by.training.vashkevichyura.dao.DAOFactory;
import by.training.vashkevichyura.dao.Impl.BikeTypeDAOImpl;
import by.training.vashkevichyura.entity.BikeType;
import by.training.vashkevichyura.exception.DAOException;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.BikeTypeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BikeTypeServiceImpl implements BikeTypeService {

    private BikeTypeDAOImpl bikeTypeDAO = DAOFactory.getInstance().getBikeTypeDAO();
    private static final Logger LOGGER = LogManager.getLogger(BikeTypeDAOImpl.class);

    @Override
    public List<BikeType> getBikeTypes() throws ServiceException {
        List<BikeType> bikeTypes;
        try {
            bikeTypes = bikeTypeDAO.getAll();
        } catch (DAOException e) {
            LOGGER.error("Get BikeTypes error:" + e);
            throw new ServiceException("Get BikeTypes error:" + e.getMessage());
        }
        return bikeTypes;
    }
}

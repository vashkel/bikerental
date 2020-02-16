package by.training.vashkevichyura.service.impl;

import by.training.vashkevichyura.dao.DAOFactory;
import by.training.vashkevichyura.dao.RentalCostDAO;
import by.training.vashkevichyura.exception.DAOException;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.RentalCostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RentalCostServiceImpl implements RentalCostService {
    private static final Logger LOGGER = LogManager.getLogger(RentalCostServiceImpl.class);
    private static final RentalCostDAO rentalCostDao = DAOFactory.getInstance().getRentalCostDAO();

    @Override
    public double getPriceByBikeTypeId(long bikeTypeId) throws ServiceException {
        double price = 0;
        try {
            price = rentalCostDao.getPriceByBikeTypeId(bikeTypeId);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return price;
    }
}

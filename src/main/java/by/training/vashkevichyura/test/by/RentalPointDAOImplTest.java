package by.training.vashkevichyura.test.by;

import by.training.vashkevichyura.dao.DAOFactory;
import by.training.vashkevichyura.dao.RentalPointDAO;
import by.training.vashkevichyura.entity.RentalPoint;
import by.training.vashkevichyura.exception.DAOException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class RentalPointDAOImplTest {
    private RentalPointDAO rentalPointDAO = DAOFactory.getInstance().getRentalPointDAO();
    private List<RentalPoint> rentalPoints;

    @BeforeClass
    public void SetUp() {
        rentalPointDAO = DAOFactory.getInstance().getRentalPointDAO();
        rentalPoints = new ArrayList<>();
    }

    @Test
    public void testGetAllRentalPoints() throws DAOException {
        rentalPoints = rentalPointDAO.getAllRentalPoints();

        Assert.assertNotNull(rentalPoints);
        Assert.assertTrue(rentalPoints.size() > 0);
        Assert.assertEquals(rentalPoints.size(), 3);
    }
}
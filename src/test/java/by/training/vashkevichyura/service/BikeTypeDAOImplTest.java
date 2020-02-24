package by.training.vashkevichyura.service;

import by.training.vashkevichyura.dao.BikeTypeDAO;
import by.training.vashkevichyura.dao.DAOFactory;
import by.training.vashkevichyura.entity.BikeType;
import by.training.vashkevichyura.exception.DAOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class BikeTypeDAOImplTest {

    private BikeTypeDAO daoTypeDAO;
    private List<BikeType> bikeTypeList;


    @BeforeClass
    public void SetUp() {
        daoTypeDAO = DAOFactory.getInstance().getBikeTypeDAO();
        bikeTypeList = new ArrayList<>();
    }


    @Test
    public void testGetAll() throws DAOException {
//        bikeTypeList = daoTypeDAO.getAll();
//
//        Assert.assertNotNull(bikeTypeList);
//        Assert.assertTrue(bikeTypeList.size() > 0);
//        Assert.assertEquals(bikeTypeList.size(), 3);
    }

    @Test
    public void testFindBYId() throws DAOException {
//        bikeTypeList.add(daoTypeDAO.getById(1));
//
//        Assert.assertNotNull(bikeTypeList);
//        Assert.assertTrue(bikeTypeList.size() > 0);
//        Assert.assertEquals(bikeTypeList.size(), 1);

    }


    @Test
    public void testUpdate() throws DAOException {
    }

    @Test
    public void testDelete() {
    }
}
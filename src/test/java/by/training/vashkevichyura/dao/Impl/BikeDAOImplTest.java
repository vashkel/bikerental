package by.training.vashkevichyura.dao.Impl;

import by.training.vashkevichyura.connection.ConnectionPool;
import by.training.vashkevichyura.connection.ProxyConnection;
import by.training.vashkevichyura.dao.BikeDAO;
import by.training.vashkevichyura.entity.Bike;
import by.training.vashkevichyura.entity.BikeStatusEnum;
import by.training.vashkevichyura.entity.BikeType;
import by.training.vashkevichyura.entity.BikeTypeEnum;
import by.training.vashkevichyura.entity.RentalPoint;
import by.training.vashkevichyura.exception.ConnectionPoolException;
import by.training.vashkevichyura.exception.DAOException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertTrue;

public class BikeDAOImplTest {
    @Mock
    private ProxyConnection connection;
    @Mock
    private PreparedStatement statement;
    @Mock
    private ResultSet resultSet;

    private BikeDAO bikeDAO;
    private Bike bike1;
    private Bike bike2;
    private ArrayList<Bike> bikes = new ArrayList<>();

    @BeforeClass
    public  void SetUp() throws SQLException, ConnectionPoolException {
        bikeDAO = new BikeDAOImpl();
        MockitoAnnotations.initMocks(this);
        when(connection.prepareStatement(any(String.class))).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(statement.executeUpdate()).thenReturn(1);

        bike1 = new Bike();
        bike1.setId(1L);
        bike1.setBrand("STELS");
        bike1.setModel("Turbo");
        BikeType bikeType1 = new BikeType();
        bikeType1.setId(1L);
        bikeType1.setType(BikeTypeEnum.ROAD);
        bike1.setBikeType(bikeType1);
        bike1.setBikeStatus(BikeStatusEnum.FREE);
        RentalPoint rentalPoint1 = new RentalPoint();
        rentalPoint1.setId(1L);
        rentalPoint1.setTel("11225455");
        rentalPoint1.setName("First");
        rentalPoint1.setAdress("Some address");
        bike1.setRentalPoint(rentalPoint1);

        bike2 = new Bike();
        bike2.setId(2L);
        bike2.setBrand("STELS");
        bike2.setModel("Turbo");
        BikeType bikeType2 = new BikeType();
        bikeType2.setId(2L);
        bikeType2.setType(BikeTypeEnum.ROAD);
        bike2.setBikeType(bikeType2);
        RentalPoint rentalPoint2 = new RentalPoint();
        rentalPoint2.setId(2L);
        rentalPoint2.setTel("186541565");
        rentalPoint2.setName("Second");
        rentalPoint2.setAdress("Some address");
        bike2.setRentalPoint(rentalPoint2);
        bike2.setBikeStatus(BikeStatusEnum.FREE);
        bikes.add(bike1);
        bikes.add(bike2);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        setMock(connectionPool);
        when(connectionPool.getConnection()).thenReturn(connection);
    }

    private void setMock(ConnectionPool mock) {
        try {
            Field instance = ConnectionPool.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(instance, mock);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @AfterClass
    public void tearDown() throws Exception {
        bikeDAO = null;
        bike1 = null;
        bike2 = null;
        bikes.clear();
        resultSet.close();
        statement.close();
        connection.close();

    }

//    @Test
//    public void getById() throws SQLException, DAOException {
//        when(resultSet.getLong("bikes.id,")).thenReturn(2L);
//        when(resultSet.getString("bikes.brand")).thenReturn(bike2.getBrand());
//        when(resultSet.getString("bikes.model")).thenReturn(bike2.getModel());
//        when(resultSet.getLong("bikes.bike_type_id")).thenReturn(bike2.getBikeType().getId());
//        when(resultSet.getString("bike_types.type")).thenReturn(bike2.getBikeType().getType().name());
//        when(resultSet.getLong("bikes.rental_point_id")).thenReturn(bike2.getRentalPoint().getId());
//        when(resultSet.getString("rental_points.name")).thenReturn(bike2.getRentalPoint().getName());
//        when(resultSet.getString("rental_points.adress")).thenReturn(bike2.getRentalPoint().getAdress());
//        when(resultSet.getString("rental_points.tel")).thenReturn(bike2.getRentalPoint().getTel());
//        when(resultSet.getString("bikes.status")).thenReturn(bike2.getBikeStatus().name());
//        assertEquals(bikeDAO.getById(2L), bike2);
//
//
//    }

    @Test
    public void add() throws DAOException {
        for (Bike bike : bikes){
            assertTrue(bikeDAO.add(bike));
        }
    }
}
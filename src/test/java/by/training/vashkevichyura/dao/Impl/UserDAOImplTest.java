package by.training.vashkevichyura.dao.Impl;

import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.exception.DAOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertNull;

public class UserDAOImplTest {

    private static UserDAOImpl userDao = null;
    private static User user1 = null;
    private static User user2 = null;

    @BeforeClass
    public static void setUp() throws DAOException {
        userDao = mock(UserDAOImpl.class);
        user1 = new User();
        user1.setId(1);
        user1.setSurname("petrov");
        user1.setName("name");
        user1.setEmail("email@gmail.com");
        user1.setTel("1234567890");
        user1.setPassword("password");

        user2 = new User();
        user2.setId(2);
        user2.setSurname("Surname2");
        user2.setTel("1111111111");

        when(userDao.getAll()).thenReturn(Arrays.asList(user1, user2));
        when(userDao.getById(2)).thenReturn(user2);
        when(userDao.findByLogin(user1.getLogin())).thenReturn(user1);
    }
    @Test
    public void testGetAllUsers() throws DAOException {
        List<User> allUsers = userDao.getAll();
        assertEquals(2, allUsers.size());
        assertEquals("1111111111", allUsers.get(1).getTel());
        assertEquals("email@gmail.com", allUsers.get(0).getEmail());
    }
    @Test
    public void testGetById() throws DAOException {
        User user = userDao.getById(2);
        String phone = user2.getTel();
        assertEquals(phone, user.getTel());
        assertNotNull(user);
    }
    @Test
    public void testGetByLogin() throws DAOException {
        User user3 = userDao.findByLogin("1234567890");
        assertNull(user3);

    }
}
package by.training.vashkevichyura.service.impl;

        import by.training.vashkevichyura.dao.UserDAO;
import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.exception.DAOException;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.generator.HashGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.security.NoSuchAlgorithmException;

        import static org.junit.Assert.assertEquals;
        import static org.junit.Assert.assertNull;
        import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDAO userDAO;
    @Mock
    private HashGenerator hashGenerator;


    @Test
    public void login_userNotFound() throws DAOException, ServiceException {
        when(userDAO.findByLogin("vadik")).thenReturn(null);

        User user = userService.login("vadik", "password");

        assertNull(user);
    }

    @Test
    public void login_userFoundButPasswordWrong() throws DAOException, ServiceException, NoSuchAlgorithmException {
        User user = new User();
        user.setPassword("password");
        user.setSalt("salt");


        when(userDAO.findByLogin("vadk")).thenReturn(user);
        when(hashGenerator.generateHash(user.getPassword(), user.getSalt())).thenReturn("other hash");
        user = userService.login("mks", "password");

        assertNull(user);

    }

    @Test
    public void login_ok() throws DAOException, ServiceException, NoSuchAlgorithmException {
        User user = new User();
        user.setPassword("11111111");
        user.setSalt("salt");

        when(userDAO.findByLogin("virus")).thenReturn(user);
        when(hashGenerator.generateHash(user.getPassword(), user.getSalt())).thenReturn("11111111");

        User user2 = userService.login("virus", "11111111");

        assertEquals(user, user2);

    }
}

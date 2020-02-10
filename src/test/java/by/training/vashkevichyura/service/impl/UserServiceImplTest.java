package by.training.vashkevichyura.service.impl;

import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.entity.UserRoleEnum;
import by.training.vashkevichyura.entity.UserStateEnum;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.service.UserService;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNotNull;

public class UserServiceImplTest {

    private static UserService userService = null;
    private static User user = null;
    private static UserRoleEnum role = null;
    private static String userPassword = null;
    @BeforeClass
    public static void setUp() throws ServiceException {
        userService = mock(UserServiceImpl.class);
        user = new User();
        user.setId(1);
        user.setEmail("email@gmail.com");
        user.setName("yura");
        user.setSurname("Surname");
        user.setRole(UserRoleEnum.USER);
        user.setTel("56486548654");
        user.setBalance(500);
        user.setState(UserStateEnum.ACTIVE);

        when(userService.login(user.getLogin(),userPassword)).thenReturn(user);
    }

    @Test
    public void testLoginUser() throws ServiceException {
        User user1 = userService.login(user.getLogin(),userPassword);
        assertNotNull(user1);
    }
}
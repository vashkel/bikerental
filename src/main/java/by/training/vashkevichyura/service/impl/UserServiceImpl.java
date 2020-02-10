package by.training.vashkevichyura.service.impl;

import by.training.vashkevichyura.dao.DAOFactory;
import by.training.vashkevichyura.dao.Impl.UserDAOImpl;
import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.exception.DAOException;
import by.training.vashkevichyura.exception.ExceptionMessage;
import by.training.vashkevichyura.exception.ServiceException;
import by.training.vashkevichyura.generator.HashGenerator;
import by.training.vashkevichyura.service.UserService;
import by.training.vashkevichyura.util.RequestParameter;
import by.training.vashkevichyura.validator.Validator;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private UserDAOImpl userDAO = DAOFactory.getInstance().getUserDAO();

    @Override
    public User login(String login, String password) throws ServiceException {
        User user;
        if (!Validator.validateLogin(login)) {
            LOGGER.debug("login validation error " + login);
            throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
        }
        if (!Validator.passwordValidate(password)) {
            LOGGER.debug("password validation error");
            throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
        }
        try {
            user = userDAO.findByLogin(login);
            if (user != null) {
                String hash = new HashGenerator().generateHash(password, user.getSalt());
                if (hash.equals(user.getPassword())) {
                    return user;
                }
            }
        } catch (DAOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean register(Map<String, String> requestParameters, String password) throws ServiceException {
        User user = new User();
        Pair<String, String> hashSalt;
        String login = requestParameters.get(RequestParameter.LOGIN.parameter());
        if (!Validator.validateLogin(login)) {
            LOGGER.error("findByLogin validation error= " + login);
            throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
        }
        user.setLogin(login);
        long id;
        try {
            id = userDAO.findIdByLogin(user.getLogin());
        } catch (DAOException e) {
            throw new ServiceException("get user id by login error ", e);
        }
        if (id != -1L) {
            return false;
        }
        if (!Validator.passwordValidate(password)) {
            LOGGER.error("password validation error, password = " + password);
            throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
        }
        try {
            hashSalt = new HashGenerator().generateHashSalt(password);
            user.setPassword(hashSalt.getKey());
            user.setSalt(hashSalt.getValue());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String name = requestParameters.get(RequestParameter.NAME.parameter());
        if (!Validator.nameValidate(name)) {
            LOGGER.error("name validation error, name = " + name);
            throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
        }
        user.setName(name);

        String surname = requestParameters.get(RequestParameter.SURNAME.parameter());
        if (!Validator.nameValidate(surname)) {
            LOGGER.error("surname validation error, surname = " + surname);
            throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
        }
        user.setSurname(surname);

        String email = requestParameters.get(RequestParameter.EMAIL.parameter());
        if (!Validator.emailValidate(email)) {
            LOGGER.error("email validation error, email = " + email);
            throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
        }
        user.setEmail(email);

        try {
            userDAO.register(user);
        } catch (DAOException e) {
            throw new ServiceException("Exception to DB while add user", e);
        }
        return true;
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        List<User> users;
        try {
            users = userDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException("get all users error" ,e);
        }
        return users;
    }

}






package by.training.vashkevichyura.service.impl;

import by.training.vashkevichyura.dao.DAOFactory;
import by.training.vashkevichyura.dao.UserDAO;
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
    private UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

    @Override
    public User login(String login, String password) throws ServiceException {
        User user;
        if (!Validator.validateLogin(login)) {
            LOGGER.debug("login validation error " + login);
            throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
        }
        if (!Validator.validatePassword(password)) {
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
        } catch (DAOException e) {
            LOGGER.error(" error while login user " + e);
            throw new ServiceException(" error while login user " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
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
        if (!Validator.validatePassword(password)) {
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
        if (!Validator.validateName(name)) {
            LOGGER.error("name validation error, name = " + name);
            throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
        }
        user.setName(name);

        String surname = requestParameters.get(RequestParameter.SURNAME.parameter());
        if (!Validator.validateName(surname)) {
            LOGGER.error("surname validation error, surname = " + surname);
            throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
        }
        user.setSurname(surname);

        String email = requestParameters.get(RequestParameter.EMAIL.parameter());
        if (!Validator.validateEmail(email)) {
            LOGGER.error("email validation error, email = " + email);
            throw new ServiceException(ExceptionMessage.VALIDATION_ERROR.toString());
        }
        user.setEmail(email);

        try {
            userDAO.register(user);
        } catch (DAOException e) {
            LOGGER.error("Exception to DB while add user", e);
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
            LOGGER.error("get all users error" ,e);
            throw new ServiceException("get all users error" ,e.getMessage());
        }
        return users;
    }

    @Override
    public void deleteUserById(long id) throws ServiceException {
        try {
         userDAO.deleteUserById(id);
        } catch (DAOException e) {
           LOGGER.error("delete user error : " + e);
           throw new ServiceException("delete user error : " + e.getMessage());
        }
    }

    @Override
    public void changeStateById(long userId, String state) throws ServiceException {
        try {
            userDAO.changeStateById(userId,state);
        } catch (DAOException e) {
            LOGGER.error("change status of user error : " + e);
            throw new ServiceException("change status of user error : " + e.getMessage());
        }
    }

    @Override
    public User getByID(long id) throws ServiceException {
        User user;
        try {
            user = userDAO.getById(id);
        } catch (DAOException e) {
            LOGGER.error("Exception was thrown while getting user by id: " + e);
            throw new ServiceException("Exception was thrown while getting user by id: " + e.getMessage());
        }
        return user;
    }
}







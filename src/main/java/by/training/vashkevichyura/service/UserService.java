package by.training.vashkevichyura.service;

import by.training.vashkevichyura.entity.User;
import by.training.vashkevichyura.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface UserService {

    User login(String login, String password) throws ServiceException;

    boolean register(Map<String, String> requestParameters, String password) throws ServiceException;

    List<User> getAllUsers() throws ServiceException;

    void deleteUserById(long id) throws ServiceException;

    void changeStateById(long userId, String state) throws ServiceException;

    User getByID(long id) throws ServiceException;

}

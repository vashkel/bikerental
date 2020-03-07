package by.training.vashkevichyura.validator;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatorTest {

    @Test
    void ok_ValidateLogin() {
        boolean expected = true;
        boolean actual = Validator.validateLogin("yura");
        assertEquals(expected,actual);

    }


    @Test
    void ok_validatePassword() {
        boolean expected = true;
        boolean actual = Validator.validatePassword("11111111");
        assertEquals(expected,actual);
    }

    @Test
    void ok_validateName() {
        boolean expected = true;
        boolean actual = Validator.validateName("yura");
        assertEquals(expected,actual);
    }

    @Test
    void ok_validateEmail() {
        boolean expected = true;
        boolean actual = Validator.validateEmail("yura@mail.ru");
        assertEquals(expected,actual);
    }

}
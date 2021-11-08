package lapr.project.controller;


import lapr.auth.domain.model.Password;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PasswordTest {

    @Test
    public void validate() {
        Password pwd= new Password("ABCde12");
assertTrue(pwd.checkPassword("ABCde12"));

        assertFalse(pwd.checkPassword("1234567"));
        assertFalse(pwd.checkPassword("abc"));
        assertFalse(pwd.checkPassword("abcde12"));
        assertFalse(pwd.checkPassword("abcdefg"));
    }
}
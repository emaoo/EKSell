package com.eksell.eksell.eksell;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JUnit Test for the TestValidator class
 *
 * @author Katherine Xiao
 * @version May 30, 2016
 *
 * @author Period - 3
 * @author Assignment - EKSell
 *
 * @author Sources: None
 */
public class ValidatorTest {
    /**
     * Test for the isNameValid method in the TestValidator class
     *
     */
    @Test
    public void testIsNameValid(){
        CharSequence falseName = "";
        CharSequence trueName = "Bob";
        assertFalse(TestValidator.isNameValid(falseName));
        assertTrue(TestValidator.isNameValid(trueName));
    }

    /**
     * Test for the isEmailValid method in the TestValidator class
     *
     */
    @Test
    public void testIsEmailValid(){
        CharSequence trueEmail = "bob@gmail.com";
        assertTrue(TestValidator.isEmailValid(trueEmail));
        CharSequence emptyEmail = "";
        assertFalse(TestValidator.isEmailValid(emptyEmail));
        CharSequence falseEmail = "bob";
        assertFalse(TestValidator.isEmailValid(falseEmail));
    }

    /**
     * Test for the isPasswordValid method in the TestValidator class
     */
    @Test
    public void testIsPasswordValid(){
        CharSequence falsePassword = "";
        CharSequence truePassword = "password";
        assertFalse(TestValidator.isPasswordValid(falsePassword));
        assertTrue(TestValidator.isPasswordValid(truePassword));
    }

    /**
     * Test for the isPasswordsMatch method in the TestValidator class
     *
     * Note: there is expected failure (refer to Sources) because TextUtils is used in the
     * isPasswordsMatch method, which causes error in testing
     */
    @Test
    public void testIsPasswordsMatch(){
        CharSequence password = "password";
        CharSequence confirmPass = "password";
        assertTrue(TestValidator.isPasswordsMatch(password, confirmPass));
        CharSequence badPass = "pass";
        assertFalse(TestValidator.isPasswordsMatch(password, badPass));
    }

    @Test
    public void testIsNotRobot(){
        CharSequence check = "12345";
        CharSequence confirm = "54321";
        assertTrue(TestValidator.isNotRobot(check, confirm));
        CharSequence robot = "82811";
        assertFalse(TestValidator.isNotRobot(check, robot));
    }
}

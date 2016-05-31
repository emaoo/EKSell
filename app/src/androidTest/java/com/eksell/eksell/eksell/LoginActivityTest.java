package com.eksell.eksell.eksell;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Instrumentation test for the LoginActivity class
 *
 * @author Katherine Xiao
 * @version May 30, 2016
 *
 * @author Period - 3
 * @author Assignment - EKSell
 *
 * @author Sources - Android Developers API
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    /**
     * Mock user
     */
    private Solo solo;
    
    /**
     * editable email field
     */
    private EditText emailField;
    
    /**
     * editable password field
     */
    private EditText passwordField;
    
    /**
     * example valid email
     */
    private String validEmail = "emao@gmail.com";
    /**
     * example valid password
     */
    private String validPassword = "myPassw0rd";
    
    /**
     * Constructor
     */
    public LoginActivityTest()
    {
        super( LoginActivity.class );
    }
    
    /**
     * sets up the information received
     * @throws Exception if an error occurs
     */
    public void setUp() throws Exception
    {
        solo = new Solo( getInstrumentation(), getActivity() );

        emailField = (EditText) solo.getView( R.id.emailField );
        passwordField = (EditText) solo.getView( R.id.passwordField );
    }
    
    /**
     * tests if there is an empty email (aka nothing entered in email field)
     */
    public void testEmptyEmail()
    {
        solo.enterText( emailField, "" );
        solo.enterText( passwordField, validPassword );

        solo.clickOnButton( R.string.button_login );

        assertTrue( "Warning in Toast is missing or invalid", solo.waitForText(
                solo.getString( R.string.warning_email_empty ) ) );
    }
    
    /**
     * tests for invalid email (aka does not follow email format)
     */
    public void testInvalidEmail()
    {
        solo.enterText( emailField, "not_email" );
        solo.enterText( passwordField, validPassword );

        solo.clickOnButton( R.string.button_login);

        assertTrue( "Warning in Toast is missing or invalid", solo.waitForText(
                solo.getString( R.string.warning_email_invalid ) ) );
    }
    
    /**
     * tests for an empty password (aka nothing entered in password field)
     */
    public void testEmptyPassword()
    {
        solo.enterText( emailField, validEmail );
        solo.enterText( passwordField, "" );

        solo.clickOnButton( solo.getString( R.string.button_login ) );

        assertTrue( "Warning in Toast is missing or invalid", solo.waitForText(
                solo.getString( R.string.warning_password_empty ) ) );
    }
    
    /**
     * exits the activity
     * @throws Exception if error occurs
     */
    @Override
    public void tearDown() throws Exception
    {
        solo.finishOpenedActivities();
    }
}

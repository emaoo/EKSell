package com.eksell.eksell.eksell;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Instrumentation Testing for the Registration Activity class
 */
public class RegistrationActivityTest extends ActivityInstrumentationTestCase2<RegistrationActivity> {
    /**
     * Mock user
     */
    private Solo solo;
    /**
     * editable name field
     */
    private EditText nameField;
    /**
     * editable email field
     */
    private EditText emailField;
    /**
     * editable password field
     */
    private EditText passwordField;

    /**
     * editable confirm password field
     */
    private EditText passwordConfirmField;

    /**
     * test valid name
     */
    private String validName = "Eileen Mao";
    /**
     * test valid email
     */
    private String validEmail = "emao@gmail.com";
    /**
     * test valid password
     */
    private String validPassword = "myPassw0rd";

    /**
     * Constructor
     */
    public RegistrationActivityTest()
    {
        super( RegistrationActivity.class );
    }

    /**
     * sets up the information retrieved
     * @throws Exception if error occurs
     */
    public void setUp() throws Exception
    {
        solo = new Solo( getInstrumentation(), getActivity() );

        nameField = (EditText) solo.getView( R.id.nameField );
        emailField = (EditText) solo.getView( R.id.emailField );
        passwordField = (EditText) solo.getView( R.id.passwordField );
        passwordConfirmField = (EditText) solo.getView( R.id.passwordConfirmField );
    }

    /**
     * tests if there is an empty name (aka nothing entered in name field)
     */
    public void testEmptyNameValidation()
    {
        solo.enterText( nameField, "" );
        solo.enterText( emailField, validEmail );
        solo.enterText( passwordField, validPassword );
        solo.enterText( passwordConfirmField, validPassword );

        solo.clickOnButton( "Register" );

        assertTrue( "Warning in Toast is missing or invalid", solo.waitForText(
                solo.getString( R.string.warning_name_empty ) ) );
    }

    /**
     * tests if there is an empty email (aka nothing entered in email field)
     */
    public void testEmptyEmail()
    {
        solo.enterText( nameField, validName );
        solo.enterText( emailField, "" );
        solo.enterText( passwordField, validPassword );
        solo.enterText( passwordConfirmField, validPassword );

        solo.clickOnButton( "Register" );

        assertTrue( "Warning in Toast is missing or invalid", solo.waitForText(
                solo.getString( R.string.warning_email_empty ) ) );
    }

    /**
     * tests if there is an invalid email (aka does not follow email format)
     */
    public void testInvalidEmail()
    {
        solo.enterText( nameField, validName );
        solo.enterText( emailField, "not_email" );
        solo.enterText( passwordField, validPassword );
        solo.enterText( passwordConfirmField, validPassword );

        solo.clickOnButton( "Register");

        assertTrue( "Warning in Toast is missing or invalid", solo.waitForText(
                solo.getString( R.string.warning_email_invalid ) ) );
    }

    /**
     * tests if there is an empty password (aka nothing entered in password field)
     */
    public void testEmptyPassword()
    {
        solo.enterText( nameField, validName );
        solo.enterText( emailField, validEmail );
        solo.enterText( passwordField, "" );
        solo.enterText( passwordConfirmField, validPassword );

        solo.clickOnButton( "Register" );

        assertTrue( "Warning in Toast is missing or invalid", solo.waitForText(
                solo.getString( R.string.warning_password_empty ) ) );
    }

    /**
     * tets if the password and confirmation password do not match
     */
    public void testNotMatchingPasswords()
    {
        solo.enterText( nameField, validName );
        solo.enterText( emailField, validEmail );
        solo.enterText( passwordField, validPassword );
        solo.enterText( passwordConfirmField, passwordConfirmField+"1" );

        solo.clickOnButton( "Register" );

        assertTrue( "Warning in Toast is missing or invalid", solo.waitForText(
                solo.getString( R.string.warning_passwords_do_not_match ) ) );
    }

    /**
     * exits the activity
     * @throws Exception if an error occurs
     */
    @Override
    public void tearDown() throws Exception
    {
        solo.finishOpenedActivities();
    }
}

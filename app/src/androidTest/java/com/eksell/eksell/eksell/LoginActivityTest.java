package com.eksell.eksell.eksell;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by tmao on 5/24/16.
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    private Solo solo;

    private EditText emailField;
    private EditText passwordField;

    private String validEmail = "emao@gmail.com";
    private String validPassword = "myPassw0rd";

    public LoginActivityTest()
    {
        super( LoginActivity.class );
    }

    public void setUp() throws Exception
    {
        solo = new Solo( getInstrumentation(), getActivity() );

        emailField = (EditText) solo.getView( R.id.emailField );
        passwordField = (EditText) solo.getView( R.id.passwordField );
    }

    public void testEmptyEmail()
    {
        solo.enterText( emailField, "" );
        solo.enterText( passwordField, validPassword );

        solo.clickOnButton( R.string.button_login );

        assertTrue( "Warning in Toast is missing or invalid", solo.waitForText(
                solo.getString( R.string.warning_email_empty ) ) );
    }

    public void testInvalidEmail()
    {
        solo.enterText( emailField, "not_email" );
        solo.enterText( passwordField, validPassword );

        solo.clickOnButton( R.string.button_login);

        assertTrue( "Warning in Toast is missing or invalid", solo.waitForText(
                solo.getString( R.string.warning_email_invalid ) ) );
    }

    public void testEmptyPassword()
    {
        solo.enterText( emailField, validEmail );
        solo.enterText( passwordField, "" );

        solo.clickOnButton( solo.getString( R.string.button_login ) );

        assertTrue( "Warning in Toast is missing or invalid", solo.waitForText(
                solo.getString( R.string.warning_password_empty ) ) );
    }

    @Override
    public void tearDown() throws Exception
    {
        solo.finishOpenedActivities();
    }
}

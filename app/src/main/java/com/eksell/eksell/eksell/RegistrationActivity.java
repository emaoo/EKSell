package com.eksell.eksell.eksell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.eksell.eksell.utility.BackendSettings;
import com.eksell.eksell.utility.LoadingCallback;
import com.eksell.eksell.utility.Validator;

/**  	
  * Registers the user through entered entries
  * 
  * @author Eileen Mao
  * @version May 12, 2016
  * 
  * @author Period - 3
  * @author Assignment - EKSell
  * 
  * @author Sources - Backendless API
  */
public class RegistrationActivity extends AppCompatActivity {
    /**
     * Creates the screen for the activity and initializes the BackEndless Application. The method
     * then creates the registration button and saves the entered entries (if valid) and saves them
     * into a user on BackEndless
     * @param savedInstanceState is the state of the app
     */
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_registration );

        Backendless.initApp( this, BackendSettings.APPLICATION_ID, BackendSettings.SECRET_KEY,
                BackendSettings.VERSION );

        // click registration button
        Button registerButton = (Button) findViewById( R.id.registerButton );

        View.OnClickListener registerButtonClickListener = createRegisterButtonClickListener();

        registerButton.setOnClickListener( registerButtonClickListener );
    }

    /**
     * Checks to see if the entered values are valid
     * @param name is the name of the user
     * @param email is the user's email
     * @param password is the user's desired password
     * @param passwordConfirm is a confirmation of the password
     * @return true if the registration is valid, false otherwise
     */
    public boolean isRegistrationValuesValid( CharSequence name, CharSequence email, CharSequence password,
                                              CharSequence passwordConfirm )
    {
        return Validator.isNameValid( this, name )
                && Validator.isEmailValid( this, email )
                && Validator.isPasswordValid( this, password )
                && Validator.isPasswordsMatch( this, password, passwordConfirm );
    }

    public void registerUser( String name, String email, String password,
                              AsyncCallback<BackendlessUser> registrationCallback )
    {
        BackendlessUser user = new BackendlessUser();
        user.setEmail( email );
        user.setPassword( password );
        user.setProperty( "name", name );

        Backendless.UserService.register( user, registrationCallback );
    }

    /**
     * Retrieves the result of the registration call
     * @return failure or success
     */
    public LoadingCallback<BackendlessUser> createRegistrationCallback()
    {
        return new LoadingCallback<BackendlessUser>( this, getString( R.string.loading_register ) )
        {
            /**
             * If the response is successful, register the user by their email, set the result to
             * okay, and finish the activity
             * @param registeredUser is the user that is being registered
             */
            @Override
            public void handleResponse( BackendlessUser registeredUser )
            {
                super.handleResponse( registeredUser );
                Intent registrationResult = new Intent();
                registrationResult.putExtra( BackendlessUser.EMAIL_KEY, registeredUser.getEmail() );
                setResult( RESULT_OK, registrationResult );
                RegistrationActivity.this.finish();
            }
        };
    }

    public View.OnClickListener createRegisterButtonClickListener()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                EditText nameField = (EditText) findViewById( R.id.nameField );
                EditText emailField = (EditText) findViewById( R.id.emailField );
                EditText passwordField = (EditText) findViewById( R.id.passwordField );
                EditText passwordConfirmField = (EditText) findViewById( R.id.passwordConfirmField );

                CharSequence name = nameField.getText();
                CharSequence email = emailField.getText();
                CharSequence password = passwordField.getText();
                CharSequence passwordConfirmation = passwordConfirmField.getText();

                if( isRegistrationValuesValid( name, email, password, passwordConfirmation ) )
                {
                    LoadingCallback<BackendlessUser> registrationCallback = createRegistrationCallback();

                    registrationCallback.showLoading();
                    registerUser( name.toString(), email.toString(), password.toString(), registrationCallback );
                }
            }
        };
    }
}

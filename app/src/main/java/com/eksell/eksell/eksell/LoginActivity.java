package com.eksell.eksell.eksell;
/**	  	
  * Creates the login page and logs the user in if the entries are valid or directs the user to register
  * if he or she is a new user and to the feed if he or she is a returning user
  * 
  * @author Eileen Mao
  * @version May 12, 2016
  * 
  * @author Period - 3
  * @author Assignment - EKSell
  *
  * @author Sources - Backendless API
  */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.eksell.eksell.utility.BackendSettings;
import com.eksell.eksell.utility.LoadingCallback;
import com.eksell.eksell.utility.Validator;

public class LoginActivity extends AppCompatActivity {
    /**
     * Registration request code for starting the registration activity
     */
    private static final int REGISTER_REQUEST_CODE = 1;
    
    /**
     * Creates the screen for the activity with buttons directing the user to either login or
     * register and then initializes the Backendless Application. This method then creates the respective
     * login and registration buttons which the user can click when done, and, if the entries are valid,
     * registrates the user or logs the user in onto the Backendless server (depending on which button is
     * clicked)
     * @param savedInstanceState is the saved state of the application
     */
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        Backendless.initApp( this, BackendSettings.APPLICATION_ID,
                BackendSettings.SECRET_KEY, BackendSettings.VERSION );

        // click login button
        Button loginButton = (Button) findViewById( R.id.loginButton );
        loginButton.setOnClickListener(new View.OnClickListener() {
            /**
             * When the user clicks the log in button, you retrieve the entered information and, if they are valid, log the
             * user in onto the server
             * @param v is the current view
             */
            @Override
            public void onClick(View v) {
                EditText emailField = (EditText) findViewById( R.id.emailField );
                EditText passwordField = (EditText) findViewById( R.id.passwordField );

                CharSequence email = emailField.getText();
                CharSequence password = passwordField.getText();

                if( isLoginValuesValid( email, password ) )
                {
                    LoadingCallback<BackendlessUser> loginCallback = createLoginCallback();

                    loginCallback.showLoading();

                    Backendless.UserService.login( email.toString(), password.toString(), loginCallback );
                }
            }
        });

        // click registration button
        Button regButton = (Button) findViewById( R.id.regButton );
        regButton.setOnClickListener(new View.OnClickListener() {
            /**
             * When the "Register here" button is clicked, the user is directed to the registration
             * page 
             * @param v is the current view
             */
            @Override
            public void onClick(View v) {
                Intent registrationIntent = new Intent( LoginActivity.this, RegistrationActivity.class );
                startActivityForResult( registrationIntent, REGISTER_REQUEST_CODE );
            }
        });
    }
    /**
     * Checks if the entered Login values are valid
     * @param email is the email entered
     * @param password is the password entered
     * @return true if valid, false otherwise
     */
    public boolean isLoginValuesValid( CharSequence email, CharSequence password )
    {
        return Validator.isEmailValid( this, email ) && Validator.isPasswordValid( this, password );
    }
    
    /**
     * Retrieves the result of the log in and handles it based on the result
     * @return success or failure
     */
    public LoadingCallback<BackendlessUser> createLoginCallback()
    {
        return new LoadingCallback<BackendlessUser>( this, getString( R.string.loading_login ) )
        {
            /**
             * If the response is successful, directs the user to the feed
             * @param loggedInUser is the user that is logged in
             */
            @Override
            public void handleResponse( BackendlessUser loggedInUser )
            {
                super.handleResponse( loggedInUser );

                Intent itemListingIntent = new Intent( LoginActivity.this, ItemListingActivity.class );
                startActivity( itemListingIntent );
                finish();
            }
        };
    }
    /**
     * When the Login Activity is called, the user is able to enter text into the fields and, if
     * successful, is shown a message
     * @param requestCode is the request code
     * @param resultCode is the result code
     * @param data is the data received
     */
    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        if( resultCode == RESULT_OK )
        {
            switch( requestCode )
            {
                case REGISTER_REQUEST_CODE:
                    String email = data.getStringExtra( BackendlessUser.EMAIL_KEY );
                    EditText emailField = (EditText) findViewById( R.id.emailField );
                    emailField.setText( email );

                    EditText passwordField = (EditText) findViewById( R.id.passwordField );
                    passwordField.requestFocus();

                    Toast.makeText( this, getString( R.string.info_registered_success ), Toast.LENGTH_SHORT ).show();
            }
        }
    }
}

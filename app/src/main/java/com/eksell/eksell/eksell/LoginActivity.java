package com.eksell.eksell.eksell;

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
    private static final int REGISTER_REQUEST_CODE = 1;

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
            @Override
            public void onClick(View v) {
                Intent registrationIntent = new Intent( LoginActivity.this, RegistrationActivity.class );
                startActivityForResult( registrationIntent, REGISTER_REQUEST_CODE );
            }
        });
    }

    public boolean isLoginValuesValid( CharSequence email, CharSequence password )
    {
        return Validator.isEmailValid( this, email ) && Validator.isPasswordValid( this, password );
    }

    public LoadingCallback<BackendlessUser> createLoginCallback()
    {
        return new LoadingCallback<BackendlessUser>( this, getString( R.string.loading_login ) )
        {
            @Override
            public void handleResponse( BackendlessUser loggedInUser )
            {
                super.handleResponse( loggedInUser );

                Intent restaurantListingIntent = new Intent( LoginActivity.this, ItemListingActivity.class );
                startActivity( restaurantListingIntent );
                finish();
            }
        };
    }

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

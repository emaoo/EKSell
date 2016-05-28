package com.eksell.eksell.eksell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.eksell.eksell.utility.BackendSettings;
import com.eksell.eksell.utility.LoadingCallback;
import com.eksell.eksell.utility.Validator;


public class RegistrationActivity extends AppCompatActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_registration );

        Backendless.initApp( this, BackendSettings.APPLICATION_ID, BackendSettings.SECRET_KEY,
                BackendSettings.VERSION );

        // click registration button
        Button registerButton = (Button) findViewById( R.id.registerButton );
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    BackendlessUser user = new BackendlessUser();
                    user.setEmail( email.toString() );
                    user.setPassword( password.toString() );
                    user.setProperty( "name", name );

                    LoadingCallback<BackendlessUser> registrationCallback = createRegistrationCallback();
                    registrationCallback.showLoading();
                    Backendless.UserService.register( user, registrationCallback );
                }
            }
        });
    }

    // handle response from register call
    public LoadingCallback<BackendlessUser> createRegistrationCallback()
    {
        return new LoadingCallback<BackendlessUser>( this, getString( R.string.loading_register ) )
        {
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

    private boolean isRegistrationValuesValid( CharSequence name, CharSequence email,
                                              CharSequence password, CharSequence passwordConfirm )
    {
        return Validator.isNameValid( this, name )
                && Validator.isEmailValid( this, email )
                && Validator.isPasswordValid( this, password )
                && Validator.isPasswordsMatch( this, password, passwordConfirm );
    }
}

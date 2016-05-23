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
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.eksell.eksell.utility.BackendSettings;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Backendless.initApp( this, BackendSettings.APP_ID, BackendSettings.SECRET_KEY, BackendSettings.VERSION );

        Button loginButton = (Button) findViewById(R.id.loginButton);
        Button registerButton = (Button) findViewById(R.id.regButton);

        assert loginButton != null;
        loginButton.setOnClickListener( createLoginButtonListener() );

        assert registerButton != null;
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }

        });
    }

    private View.OnClickListener createLoginButtonListener() {
        return new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                EditText usernameField = (EditText) findViewById( R.id.usernameField );
                EditText passwordField = (EditText) findViewById( R.id.passwordField );

                if( isLoginValuesValid(usernameField.getText(), passwordField.getText() ) )
                {
                    String username = usernameField.getText().toString();
                    String password = passwordField.getText().toString();
                    Backendless.UserService.login(username, password, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            Intent mainActivityIntent = new Intent( LoginActivity.this, MainActivity.class );
                            startActivity( mainActivityIntent );
                            finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(LoginActivity.this, "Error Logging In, Try Again.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        };
    }

    public boolean isLoginValuesValid( CharSequence username, CharSequence password )
    {
        return username.length() > 2 && password.length() > 2;
    }
}

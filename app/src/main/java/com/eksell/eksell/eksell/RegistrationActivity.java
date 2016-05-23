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

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Backendless.initApp( this, BackendSettings.APP_ID, BackendSettings.SECRET_KEY, BackendSettings.VERSION );

        Button registerButton = (Button) findViewById( R.id.registerButton );

        assert registerButton != null;
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText usernameField = (EditText) findViewById(R.id.usernameField);
                EditText passwordField = (EditText) findViewById(R.id.passwordField);

                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();

                //registers a user
                BackendlessUser backendlessUser = new BackendlessUser();
                backendlessUser.setPassword(password);
                backendlessUser.setProperty("name", username);

                Backendless.UserService.register(backendlessUser, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        Intent loginActivityIntent = new Intent( RegistrationActivity.this, LoginActivity.class );
                        startActivity( loginActivityIntent );
                        finish();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(RegistrationActivity.this, "User not registered.!", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
    }
}

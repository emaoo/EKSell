package com.eksell.eksell.eksell;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;


public class LoginFragment extends Fragment {

    private EditText usernameField;
    private EditText passwordField;

    public LoginFragment() {
        // Required empty public constructor
    }
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

    View view = inflater.inflate(R.layout.fragment_login, container, false);

    Button loginButton = (Button) view.findViewById(R.id.loginButton);
    usernameField = (EditText) view.findViewById(R.id.usernameField);
    passwordField = (EditText) view.findViewById(R.id.passwordField);
    Button registerButton = (Button) view.findViewById(R.id.regButton);


    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
        public void onClick(View v)
      {
          String username = usernameField.getText().toString();
          String password = passwordField.getText().toString();

          Backendless.UserService.login(username, password, new AsyncCallback<BackendlessUser>() {
              @Override
              public void handleResponse(BackendlessUser response) {
                  Toast.makeText(getActivity(), "You Logged In!", Toast.LENGTH_SHORT).show();
              }

              @Override
              public void handleFault(BackendlessFault fault) {
                  Toast.makeText(getActivity(), "Error Logging In, Try Again.", Toast.LENGTH_SHORT).show();
              }
          });

      }

    });

    registerButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {

            Intent intent = new Intent(getActivity(), RegisterActivity.class);
            startActivity(intent);

        }

    });

    return view;

}
}


package com.eksell.eksell.eksell;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);



        RegisterFragment register = new RegisterFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.registerContainer, register).commit();
    }
}

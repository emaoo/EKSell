package com.eksell.eksell.eksell;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.backendless.Backendless;

public class MainActivity extends AppCompatActivity {

    public static final String APP_ID = "BCDE225E-345E-E826-FFCB-7F28BAA05400";
    public static final String SECRET_KEY = "1408C444-50DE-08EE-FF5D-D6CA01BA9100";
    public static final String  VERSION= "v1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainMenuFragment mainMenu = new MainMenuFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container,mainMenu).commit();

        Backendless.initApp(this, APP_ID, SECRET_KEY, VERSION);
    }
}

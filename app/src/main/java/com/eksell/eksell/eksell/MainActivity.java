package com.eksell.eksell.eksell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.backendless.Backendless;
import com.eksell.eksell.utility.BackendSettings;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Backendless.initApp( this, BackendSettings.APP_ID, BackendSettings.SECRET_KEY, BackendSettings.VERSION );

        String[] menuItems = { "Register", "Login"};
        ListView listView = (ListView) findViewById(R.id.mainMenu);
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, menuItems);
        listView.setAdapter(listViewAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                    startActivity(intent);

                } else if (position == 1) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);

                }
            }
        });
    }
}

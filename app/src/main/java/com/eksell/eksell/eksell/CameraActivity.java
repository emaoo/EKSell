package com.eksell.eksell.eksell;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.backendless.Backendless;
import com.backendless.Persistence;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.eksell.eksell.entities.Item;

/**
 * Created by emao on 5/27/16.
 */
public class CameraActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

    }
    public void saveNewContact()
    {
        String applicationID = "YOUR-APPLICATION-ID";
        String secretKey = "YOUR-JAVA/ANDROID-SECRET-KEY";
        String version = "v1";
        Backendless.initApp( applicationID, secretKey, version );

        Item item = new Item();
        //item.setSeller();
        item.setPrice( 147 );
        //item.setEmail( "777-777-777" );
        item.setDescription( "Favorites" );

        // save object synchronously
        Item savedContact = Backendless.Persistence.save( item );

        // save object asynchronously
        Backendless.Persistence.save( item, new AsyncCallback<Item>() {
            public void handleResponse( Item response )
            {
                // new Contact instance has been saved
            }

            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be retrieved with fault.getCode()
            }
        });
    }
}

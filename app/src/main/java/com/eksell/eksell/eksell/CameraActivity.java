package com.eksell.eksell.eksell;

import java.util.Random;

import android.app.ProgressDialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.eksell.eksell.entities.Item;
import com.eksell.eksell.utility.DialogHelper;
import com.eksell.eksell.utility.LoadingCallback;
import com.backendless.files.BackendlessFile;

/**
 * Created by emao on 5/27/16.
 */

public class CameraActivity extends AppCompatActivity {
    public static final String DEFAULT_PATH_ROOT = "img";
    public final static String PHOTO_CAMERA_URL = "PHOTO_CAMERA_URL";
    public static final int ADD_NEW_PHOTO_RESULT = 4;

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageViewPhoto;
    private Random random = new Random();
    Item item = new Item();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Button submitButton = (Button) findViewById( R.id.submitButton );
        submitButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                final ProgressDialog progressDialog = new ProgressDialog( CameraActivity.this );
                progressDialog.setMessage( getString( R.string.loading_submit_order ) );
                progressDialog.show();

                Backendless.Counters.incrementAndGet( "photo_id", new AsyncCallback<Integer>()
                {
                    @Override
                    public void handleResponse( Integer response )
                    {
                        progressDialog.dismiss();

                        EditText editTextItemName = (EditText) findViewById( R.id.editTextItemName);
                        EditText editTextItemDescription = (EditText) findViewById( R.id.editTextItemDescription);
                        EditText editTextItemPrice = (EditText) findViewById( R.id.editTextItemPrice);

                        item.setName(editTextItemName.getText().toString());
                        item.setPrice( (Integer.parseInt(editTextItemPrice.getText().toString())));
                        item.setDescription(editTextItemDescription.getText().toString());
                        item.setSeller( Backendless.UserService.CurrentUser());

                        Bundle extras = getIntent().getExtras();
                        if(extras != null) {
                            item.setImageUrl(extras.getString(PHOTO_CAMERA_URL));
                        }

                        // save Order on backend
                        Backendless.Data.of( Item.class ).save( item, new LoadingCallback<Item>(
                                CameraActivity.this, getString( R.string.loading_placing_order ), true )
                        {
                            @Override
                            public void handleResponse( Item response )
                            {
                                super.handleResponse( response );
                                Intent orderSuccessIntent = new Intent( CameraActivity.this, ItemListingActivity.class );
                                startActivity( orderSuccessIntent );
                                finish();
                            }
                        } );
                    }

                    @Override
                    public void handleFault( BackendlessFault fault )
                    {
                        progressDialog.dismiss();

                        DialogHelper.createErrorDialog( CameraActivity.this, "BackendlessFault", fault.getMessage() ).show();
                    }
                } );
            }
        } );


        imageViewPhoto = (ImageView)this.findViewById(R.id.ivPhoto);
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            String pictureName = "camera" + random.nextInt() + ".png";
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageViewPhoto = (ImageView) findViewById( R.id.ivPhoto );
            imageViewPhoto.setImageBitmap(bitmap);
            Backendless.Files.Android.upload( bitmap, Bitmap.CompressFormat.PNG, 100, pictureName,
                     DEFAULT_PATH_ROOT, new AsyncCallback<BackendlessFile>()
            {
                @Override
                public void handleResponse( BackendlessFile response )
                {
                    String photoCameraUrl = response.getFileURL();
                    item.setImageUrl(photoCameraUrl);
                }

                @Override
                public void handleFault( BackendlessFault fault )
                {
                     Toast.makeText( CameraActivity.this, fault.toString(), Toast.LENGTH_LONG ).show();
                }
            } );
        }
        else {
            imageViewPhoto = (ImageView) findViewById( R.id.ivPhoto );
            Intent intent = new Intent( android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
            startActivityForResult( intent, CAMERA_REQUEST );
        }
    }
}

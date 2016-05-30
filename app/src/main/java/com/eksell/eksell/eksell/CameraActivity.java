package com.eksell.eksell.eksell;

import java.util.Random;

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
import com.eksell.eksell.utility.LoadingCallback;
import com.backendless.files.BackendlessFile;

/**
 * Allows the user to sell an item by taking a picture, adding a name, price,
 * and description before uploading it onto the server and sending it to the Feed.
 * 
 * @author Katherine Xiao
 * @version May 24, 2016
 * 
 * @author Period - 3
 * @author Assignment - EKSell
 * 
 * @author Sources: Android API, Backendless API, Youtube tutorial
 */

public class CameraActivity extends AppCompatActivity {
    /**
     * Default path root for the image
     */
    public static final String DEFAULT_PATH_ROOT = "img";
    /**
     * String for the photo URL
     */ 
    public final static String PHOTO_URL = "PHOTO_URL";

    /**
     * Camera request code for onActivityResult()
     */
    private static final int CAMERA_REQUEST = 1888;
    
    /**
     * ImageView of photo taken
     */
    private ImageView imageViewPhoto;
    
    /**
     * Initialized Random object
     */
    private Random random = new Random();
    Item item = new Item();
    /**
     * Creates the layout and handles the response when the "POST" button is
     * clicked
     * @param savedInstanceState is the current saved state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Button submitButton = (Button) findViewById( R.id.submitButton );
        submitButton.setOnClickListener( new View.OnClickListener()
        {
            /**
             * When the "POST" button is clicked, this method retrieves this photo's unique
             * photo ID and subsequently handles the response
             * @param v
             */
            @Override
            public void onClick( View v )
            {
                Backendless.Counters.incrementAndGet( "photo_id", new AsyncCallback<Integer>()
                {
                    /**
                     * This method handles the response by retrieving the respective entered
                     * texts, assigns the image its unique image Url to be saved on
                     * Backendless, and saves it as an Item to be saved onto the server
                     * @param response is the response returned by the server
                     */
                    @Override
                    public void handleResponse( Integer response )
                    {
                        EditText editTextItemName = (EditText) findViewById( R.id.editTextItemName);
                        EditText editTextItemDescription = (EditText) findViewById( R.id.editTextItemDescription);
                        EditText editTextItemPrice = (EditText) findViewById( R.id.editTextItemPrice);

                        item.setName(editTextItemName.getText().toString());
                        item.setPrice( (Integer.parseInt(editTextItemPrice.getText().toString())));
                        item.setDescription(editTextItemDescription.getText().toString());
                        // set seller as current user
                        item.setSeller( Backendless.UserService.CurrentUser());

                        // populate image Url if available
                        Bundle extras = getIntent().getExtras();
                        if(extras != null) {
                            item.setImageUrl(extras.getString(PHOTO_URL));
                        }

                        // save item on backend
                        Backendless.Data.of( Item.class ).save( item, new LoadingCallback<Item>(
                                CameraActivity.this, "saving item ...", true )
                        {
                            /**
                             * Once the item has been sent to the server, this method handles
                             * the response. If successful, it then sends the activity to the
                             * ItemListingActivity class
                             * @param response is the response returned by the server
                             */
                            @Override
                            public void handleResponse( Item response )
                            {
                                super.handleResponse( response );
                                Intent saveSuccessIntent = new Intent( CameraActivity.this, ItemListingActivity.class );
                                startActivity( saveSuccessIntent );
                                finish();
                            }
                        } );
                    }
                    /**
                     * If it is unsuccessful, sends a message to the user of the error through a
                     * Toast
                     * @param fault
                     */
                    @Override
                    public void handleFault( BackendlessFault fault )
                    {
                        Toast.makeText(CameraActivity.this, fault.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } );
            }
        } );


        imageViewPhoto = (ImageView)this.findViewById(R.id.ivPhoto);
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    /**
     * If the user has taken a picture and confirms it by clicking the check, this saves
     * the picture, displays it on the ImageView, and uploads it onto the server into the
     * descignated image URL. If not, it tries again.
     * @param requestCode is the request code
     * @param resultCode is the result code
     * @param data is the intent passed
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            String pictureName = "camera" + random.nextInt() + ".png";
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            imageViewPhoto = (ImageView) findViewById( R.id.ivPhoto );
            imageViewPhoto.setImageBitmap(bitmap);

            Backendless.Files.Android.upload( bitmap, Bitmap.CompressFormat.PNG, 100, pictureName,
                     DEFAULT_PATH_ROOT, new AsyncCallback<BackendlessFile>()
            {
                /**
                 * If the upload response is successful, this method sets the image's url
                 * @param response
                 */
                @Override
                public void handleResponse( BackendlessFile response )
                {
                    String photoCameraUrl = response.getFileURL();
                    item.setImageUrl(photoCameraUrl);
                }

                /**
                 * If not, a message (Toast) of the error is sent to the user
                 * @param fault
                 */
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

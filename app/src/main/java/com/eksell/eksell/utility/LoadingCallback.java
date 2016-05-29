package com.eksell.eksell.utility;

import android.app.ProgressDialog;
import android.content.Context;

import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

/**
 * Created by emao on 5/18/16.
 */
public class LoadingCallback<T> implements AsyncCallback<T> {
    /**
     * The state of the data
     */ 
    private Context context;
    /**
     * The progress indicator 
     */
    private ProgressDialog progressDialog;
    
    /**
     * Constructor that initializes the message to "Loading..."
     * @param context is the context/state of the data
     */
    public LoadingCallback( Context context )
    {
        this( context, context.getString( com.eksell.eksell.eksell.R.string.loading_empty ) );
    }
    
    /**
     * Constructor that initializes the context and the progess dialog message
     * @param context is the context/state of the data
     * @param progressDialogMessage is the message to be passed and displayed
     */
    public LoadingCallback( Context context, String progressDialogMessage )
    {
        this.context = context;
        progressDialog = new ProgressDialog( context );
        progressDialog.setMessage( progressDialogMessage );
    }

    /**
     * Constructor that initializes the context and progess dialog message and shows the progress dialog
     */
    public LoadingCallback( Context context, String progressDialogMessage, boolean showProgressDialog )
    {
        this( context, progressDialogMessage );
        progressDialog.show();
    }
    /**
     * When there is a response available that is not an error, this method dismisses the dialog and removes it
     * from the screen
     */
    @Override
    public void handleResponse( T response )
    {
        progressDialog.dismiss();
    }

    @Override
    public void handleFault( BackendlessFault fault )
    {
        progressDialog.dismiss();
    }

    public void showLoading()
    {
        progressDialog.show();
    }

}

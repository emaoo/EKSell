package com.eksell.eksell.utility;

import android.app.ProgressDialog;
import android.content.Context;

import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

/**
 * @author Sean Meng
 * @version May 12, 2016
 * 
 * @author Period - 3
 * @author Assignment - EKSell
 * 
 * @author Sources - Backendless API
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
     * Creates an instance and can immediately show ProgressDialog with a given message
     * @param context is the context/state of the data
     * @param progressDialogMessage is the message to be passed and displayed
     * @param showProgressDialog is set to true if want to immediately show ProgressDialog
     */
    public LoadingCallback( Context context, String progressDialogMessage, boolean showProgressDialog )
    {
        this( context, progressDialogMessage );
        progressDialog.show();
    }
    
    /**
     * When there is a response available that is not an error, this method dismisses the dialog and removes it
     * from the screen
     * @param response is the response retrieved from Backendless
     */
    @Override
    public void handleResponse( T response )
    {
        progressDialog.dismiss();
    }
    
    /**
     * When there is an error, tbis method dismisses the progress dialog from the
     * screen
     * @param fault is the error retrieved from Backendless
     */
    @Override
    public void handleFault( BackendlessFault fault )
    {
        progressDialog.dismiss();
    }
    
    /**
     * Shows a progress dialog.
     */
    public void showLoading()
    {
        progressDialog.show();
    }

}

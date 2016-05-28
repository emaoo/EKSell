package com.eksell.eksell.utility;

import android.app.ProgressDialog;
import android.content.Context;

import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

/**
 * Created by emao on 5/18/16.
 */
public class LoadingCallback<T> implements AsyncCallback<T> {
    private Context context;
    private ProgressDialog progressDialog;

    public LoadingCallback( Context context )
    {
        this( context, context.getString( com.eksell.eksell.eksell.R.string.loading_empty ) );
    }

    public LoadingCallback( Context context, String progressDialogMessage )
    {
        this.context = context;
        progressDialog = new ProgressDialog( context );
        progressDialog.setMessage( progressDialogMessage );
    }

    public LoadingCallback( Context context, boolean showProgressDialog )
    {
        this( context );
        progressDialog.show();
    }

    public LoadingCallback( Context context, String progressDialogMessage, boolean showProgressDialog )
    {
        this( context, progressDialogMessage );
        progressDialog.show();
    }

    @Override
    public void handleResponse( T response )
    {
        progressDialog.dismiss();
    }

    @Override
    public void handleFault( BackendlessFault fault )
    {
        progressDialog.dismiss();
        DialogHelper.createErrorDialog( context, "BackendlessFault", fault.getMessage() ).show();
    }

    public void showLoading()
    {
        progressDialog.show();
    }

    public void hideLoading()
    {
        progressDialog.dismiss();
    }

}

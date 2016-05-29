package com.eksell.eksell.utility;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Class for creating the error message for the User Interface
 * 
 * @author Eileen Mao
 * @version May 10, 2016
 * 
 * @author Period - 3
 * @author Assignment - EKSell
 * 
 * @author Sources - Android Developer API
 */
public class DialogHelper {

    /**
     * Creates the error message
     * @param context the context of the object
     * @param title the title of the error
     * @param message the message of the error
     * @return an alert of the error
     */
    public static AlertDialog createErrorDialog(Context context, String title, String message )
    {
        return new AlertDialog.Builder( context )
                .setTitle( title )
                .setMessage( message )
                .setIcon( android.R.drawable.ic_dialog_alert )
                .create();
    }
}

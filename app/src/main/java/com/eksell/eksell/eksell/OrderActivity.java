package com.eksell.eksell.eksell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.eksell.eksell.utility.BackendSettings;

import java.util.ArrayList;
/**
 * When the user clicks the "BUY" button, they are directed to this activity, which lets him or her
 * write and send an email to the seller (and to himerself/herself) expressing their interest and
 * purchase details
 *
 * @author Sean Meng
 * @version May 28, 2016
 * 
 * @author Period - 3
 * @author Assignment - EKSell
 * 
 * @author Sources: Backendless API, Android API
 */
public class OrderActivity  extends AppCompatActivity {
    
    /**
     * The person who is receiving the email (aka the seller)
     */
    String emailRecipient;
    
    /**
     * Creates the layout and determines who the recipient should be. Then, when the buyer has
     * written the email and clicks the "SEND" button, am email is sent to the seller and the buyer
     * @param savedInstanceState is the current state of the screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Backendless.initApp( this, BackendSettings.APPLICATION_ID, BackendSettings.SECRET_KEY,
                BackendSettings.VERSION );

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            emailRecipient = extras.getString("email");
        }

        Button sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener( new View.OnClickListener()
        {
            /**
             * When the button is clicked, an email is sent to the buyer and seller and a message
             * notifies the user of its success. He or she is then directed back to the feed.
             * @param v is the current view
             */
            @Override
            public void onClick( View v )
            {
            Backendless.Counters.incrementAndGet( "email_id", new AsyncCallback<Integer>()
            {
                /**
                 * If the order is successful, sends the email, displays a success message, and then
                 * directs the user back to the feed
                 * @param response is the response from the server
                 */
                @Override
                public void handleResponse( Integer response )
                {
                    sendEmailAsync();

                    Toast.makeText(OrderActivity.this,
                            "Message sent, thanks for your purchase!",
                            Toast.LENGTH_LONG).show();

                    Intent intent = new Intent (OrderActivity.this, ItemListingActivity.class);
                    startActivity(intent);
                }
                
                /**
                 * If there is an error, relays the error back to the uesr through a toast
                 * @param fault is the error
                 */
                @Override
                public void handleFault( BackendlessFault fault )
                {
                    Toast.makeText(OrderActivity.this, fault.getMessage(), Toast.LENGTH_LONG).show();
                }
            } );
            }
        } );

    }

    /**
     * Sends the email entered in the text box to the seller and buyer
     */
    private void sendEmailAsync()
    {
        AsyncCallback<Void> sendEmailCallback = new AsyncCallback<Void>()
        {
            /**
             * If the email is sent successfully, notifies the user of the scces
             * @param response is the response from the server
             */
            @Override
            public void handleResponse( Void response )
            {
                Toast.makeText( OrderActivity.this, "email has been sent",
                        Toast.LENGTH_LONG ).show();
            }
            /**
             * If there is an error sending an email, displays the error to the user
             * @param fault is the error
             */
            @Override
            public void handleFault( BackendlessFault fault )
            {
                Toast.makeText( OrderActivity.this, "error sending email - "
                        + fault.getMessage(), Toast.LENGTH_LONG ).show();
            }
        };

        EditText messageText = (EditText) findViewById(R.id.messageText);
        EditText subjectText = (EditText) findViewById(R.id.subjectText);

        String subject = subjectText.getText().toString();
        String body = messageText.getText().toString();

        ArrayList<String> recipients = new ArrayList<String>();
        recipients.add(Backendless.UserService.CurrentUser().getEmail());

        if (emailRecipient !=null && !emailRecipient.trim().isEmpty())
            recipients.add( emailRecipient );

        Backendless.Messaging.sendHTMLEmail( subject, body, recipients, sendEmailCallback );
    }
}

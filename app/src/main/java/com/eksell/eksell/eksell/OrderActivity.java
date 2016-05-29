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

public class OrderActivity  extends AppCompatActivity {

    String emailRecipient;

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
            @Override
            public void onClick( View v )
            {
            Backendless.Counters.incrementAndGet( "email_id", new AsyncCallback<Integer>()
            {
                @Override
                public void handleResponse( Integer response )
                {
                    sendEmailAsync();

                    Toast.makeText(OrderActivity.this,
                            "we have send a message to the seller about your order. Thanks! ",
                            Toast.LENGTH_LONG).show();

                    Intent intent = new Intent (OrderActivity.this, ItemListingActivity.class);
                    startActivity(intent);
                }

                @Override
                public void handleFault( BackendlessFault fault )
                {
                    Toast.makeText(OrderActivity.this, fault.getMessage(), Toast.LENGTH_LONG).show();
                }
            } );
            }
        } );

    }

    private void sendEmailAsync()
    {
        AsyncCallback<Void> sendEmailCallback = new AsyncCallback<Void>()
        {
            @Override
            public void handleResponse( Void response )
            {
                Toast.makeText( OrderActivity.this, "email has been sent",
                        Toast.LENGTH_LONG ).show();
            }
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

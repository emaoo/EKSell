package com.eksell.eksell.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eksell.eksell.eksell.OrderActivity;
import com.eksell.eksell.eksell.R;
import com.eksell.eksell.entities.Item;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by emao on 5/20/16.
 */
public class ItemAdaptor extends ArrayAdapter<Item> {
    private LayoutInflater mInflater;
    private int mResource;
    Item item;

    public ItemAdaptor(Context context, int resource, List<Item> restaurants )
    {
        super( context, resource, restaurants );
        mResource = resource;
        mInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent )
    {
        View view = convertView == null ? mInflater.inflate( mResource, parent, false ) : convertView;

        // get the item data from Adaptor
        item = this.getItem( position );

        // populate the view
        TextView itemNameView = (TextView) view.findViewById( R.id.itemName);
        TextView descriptionView = (TextView) view.findViewById( R.id.itemDescription);
        TextView itemPriceView = (TextView) view.findViewById( R.id.itemPrice);

        itemNameView.setText( item.getSeller().getProperty("name") + " - " + item.getName() );
        descriptionView.setText( item.getDescription() );
        DecimalFormat df = new DecimalFormat("#.00");
        itemPriceView.setText( "$" + df.format(item.getPrice() / 100.0) );

        // Click Buy it Now Button
        ImageView btBuyNow = (ImageView) view.findViewById( R.id.itemBuyNow);
        btBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),
                        "we are going to send a message to the seller about your order. Thanks! ",
                        Toast.LENGTH_LONG).show();
                /*
                Intent showOrderIntent = new Intent( getContext(), OrderActivity.class );
                showOrderIntent.putExtra( "eksell", item);
                getContext().startActivity( showOrderIntent );
                */
            }
        });

        return view;
    }
}

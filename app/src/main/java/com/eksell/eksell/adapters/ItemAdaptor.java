package com.eksell.eksell.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eksell.eksell.eksell.CameraActivity;
import com.eksell.eksell.eksell.OrderActivity;
import com.eksell.eksell.eksell.R;
import com.eksell.eksell.entities.Item;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
        ImageView ivItemImage = (ImageView) view.findViewById(R.id.ivItemImage);

        itemNameView.setText( item.getSeller().getProperty("name") + " - " + item.getName() );
        descriptionView.setText( item.getDescription() );
        DecimalFormat df = new DecimalFormat("#.00");
        itemPriceView.setText( "$" + df.format(item.getPrice() / 100.0) );

        if ((item.getImageUrl() != null) && (!item.getImageUrl().trim().isEmpty())) {
            Picasso.with(this.getContext())
                    .load(item.getImageUrl())
                    .into(ivItemImage);
        } else {
            ivItemImage.setImageResource(R.drawable.sample_image);
        }

        // Click Buy it Now Button
        ImageView btBuyNow = (ImageView) view.findViewById( R.id.itemBuyNow);
        btBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),
                        "we are going to send a message to the seller about your order. Thanks! ",
                        Toast.LENGTH_LONG).show();

                Intent showOrderIntent = new Intent( getContext(), OrderActivity.class );
                getContext().startActivity( showOrderIntent );

            }
        });

        return view;
    }

}

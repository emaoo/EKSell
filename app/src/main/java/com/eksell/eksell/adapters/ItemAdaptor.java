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
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

/**
 * An adaptor that converts the item to a viewable layout
 *
 * @author Eileen Mao, Katherine Xiao, Sean Meng
 * @version May 26, 2016
 * 
 * @author Period - 3
 * @author Assignment - EKSell
 *
 * @author Sources: Android API, StackOverflow
 */
public class ItemAdaptor extends ArrayAdapter<Item> {
    /**
     * Inflator that instantiates the layout XML file into its View
     */
    private LayoutInflater mInflater;
    
    /**
     * The resource ID for a layout file containing a TextView to use when instantiating views.
     */
    private int mResource;
    
    /**
     * item instance
     */
    Item item;
    
    /**
     * ItemAdaptor constructor
     * @param context is the current context
     * @param resource is the resource ID for a layout file containing a TextView
     *                 to use when instantiating views
     * @param restaurants is the objects to represent in the ListView
     */
    public ItemAdaptor(Context context, int resource, List<Item> restaurants )
    {
        super( context, resource, restaurants );
        mResource = resource;
        mInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
    }
    
    /**
     * Converts the item from their respective instances and pictures into a presentable layout.
     * Also, when the buyer clicks the "BUY" button, directs the buyer to the Order Activity where
     * he or she  can email the seller demonstrating interest
     * @param position is the position of the item within the adapter's data set of the item whose
     *                 view we want
     * @param convertView is the old view to reuse
     * @param parent is the parent that this view will eventually be attached to
     * @return the view that displays the data at the specified position in the data set
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent )
    {
        View view = convertView == null ? mInflater.inflate( mResource, parent, false ) : convertView;

        // get the item data from Adaptor
        item = this.getItem( position );

        TextView itemNameView = (TextView) view.findViewById( R.id.itemName);
        TextView descriptionView = (TextView) view.findViewById( R.id.itemDescription);
        TextView itemPriceView = (TextView) view.findViewById( R.id.itemPrice);
        ImageView ivItemImage = (ImageView) view.findViewById(R.id.ivItemImage);

        itemNameView.setText( item.getSeller().getProperty("name") + " - " + item.getName() );
        descriptionView.setText( item.getDescription() );
        DecimalFormat df = new DecimalFormat("#.00");
        itemPriceView.setText( "$" + df.format(item.getPrice() / 100.0) );

        if ((item.getImageUrl() != null) && (!item.getImageUrl().trim().isEmpty())) {
            // Use google Picasso library to retrieve the image from Url
            Picasso.with(this.getContext()).load(item.getImageUrl()).into(ivItemImage);
        } else {
            ivItemImage.setImageResource(R.drawable.sample_image);
        }

        // Click Buy it Now Button
        ImageView btBuyNow = (ImageView) view.findViewById( R.id.itemBuyNow);
        btBuyNow.setOnClickListener(new View.OnClickListener() {
            /**
             * When the user clicks on the "BUY" button, a message is displayed to fill in
             * the fields and is subsequently directed to the Order Activity.
             * @param v is the current view
             */
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),
                        "Please fill the message out in detail! ",
                        Toast.LENGTH_LONG).show();

                Intent showOrderIntent = new Intent( getContext(), OrderActivity.class );
                showOrderIntent.putExtra("email", item.getSeller().getEmail());
                getContext().startActivity( showOrderIntent );
            }
        });

        return view;
    }

}

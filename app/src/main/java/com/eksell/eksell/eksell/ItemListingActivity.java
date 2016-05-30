package com.eksell.eksell.eksell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.eksell.eksell.adapters.ItemAdaptor;
import com.eksell.eksell.entities.Item;
import com.eksell.eksell.utility.LoadingCallback;


import java.util.ArrayList;
import java.util.List;
/**
 * This is the feed class that projects the pictures in the feed in a List View, three at a time, and determines
 * if more pictures need to be loaded/added, and if so, retrieves the next few to be loaded from the server
 * 
 * @author Eileen Mao, Katherine Xiao, Sean Meng
 * @version May 27, 2016
 *
 * @author Period - 3
 * @author Assignment - EKSell
 *
 * @author Sources - Backendless API, Android Developers API & Reference 
 */

public class ItemListingActivity extends AppCompatActivity {
    /**
     * Number of items displayed on the screen at a time
     */
    private final int pageSize = 3;
    
    /**
     * Collection of items to be retrieved
     */
    private BackendlessCollection<Item> itemsBackendless;
    
    /**
     * List of all the items
     */ 
    private List<Item> totalItems = new ArrayList<>();
    
    /**
     * Whether or not the user requests to load more items
     */ 
    private boolean isLoadingItems = false;
    
    /**
     * Adaptor that serves as a bridge between the list and the ListView
     */ 
    private ItemAdaptor adapter;
    
    /**
     * Displayed list on the screen
     */ 
    ListView itemListView;
    
    /**
     * Creates the layout of the page by calling the XML layout, setting the ListView through using the adaptor to
     * convert from a list, creating the number of items to be displayed, and retrieving them if the user is
     * scrolling
     * @param savedInstanceState is the current state of the app
     */ 
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_item_listing );

        adapter = new ItemAdaptor( ItemListingActivity.this, R.layout.list_item, totalItems);

        itemListView = (ListView) findViewById(R.id.list);
        itemListView.setAdapter( adapter );

        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setPageSize(pageSize);

        BackendlessDataQuery query = new BackendlessDataQuery( queryOptions );

        Backendless.Data.of( Item.class ).find( query, new LoadingCallback<BackendlessCollection<Item>>(
                this, getString( R.string.loading_items), true )
        {
            /**
             * Handles the response (which contains the collection of items) from the server by
             * initializing the collection instance and adding the items to the total list of
             * items
             * @param itemBackendlessCollection is the response from the server that contains the
             *                                  collection of items to be added
             */
            @Override
            public void handleResponse( BackendlessCollection<Item> itemBackendlessCollection )
            {
                itemsBackendless = itemBackendlessCollection;

                addMoreItems( itemBackendlessCollection );

                super.handleResponse( itemBackendlessCollection );
            }
        } );

        itemListView.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            /**
             * If the ListView is being scrolled through, this method will be called before the
             * next frame of the scroll is rendered.
             * @param view is the current view of the scroll state
             * @param scrollState is the current scroll state
             */
            @Override
            public void onScrollStateChanged( AbsListView view, int scrollState ) { }
            
            /**
             * When the user is scrolling and more items need to be loaded, this method loads
             * the next page of items
             * @param view is the current scroll state
             * @param firstVisibleItem is the index of the first item
             * @param visibleItemCount is the number of visible items
             * @param totalItemCount is the number of items in the adaptor
             */
            @Override
            public void onScroll( AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount )
            {
                if( needToLoadItems( firstVisibleItem, visibleItemCount, totalItemCount ) )
                {
                    isLoadingItems = true;

                    itemsBackendless.nextPage(new LoadingCallback<BackendlessCollection<Item>>( ItemListingActivity.this )
                    {
                        /**
                         * Handles the response of the next collections of items to be retrieved
                         * by adding it to the total list of items
                         * @param nextPage is the next collection of items to be retrieved
                         */
                        @Override
                        public void handleResponse( BackendlessCollection<Item> nextPage )
                        {
                            itemsBackendless = nextPage;

                            addMoreItems( nextPage );

                            isLoadingItems = false;
                        }
                    } );
                }
            }
        } );

        // click sell button
        Button sellButton = (Button) findViewById( R.id.sellButton );
        sellButton.setOnClickListener(new View.OnClickListener() {
            /**
             * If the sell button is clicked, the user is directed to the Camera Activity class where
             * he or she can take a picture and sell their item
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent( ItemListingActivity.this, CameraActivity.class );
                startActivity( cameraIntent );
            }
        });
    }
    /**
     * Determines whether there is a need to load more items
     * @param firstVisibleItem is the index of the first item
     * @param visibleItemCount is the number of visible items
     * @param totalItemCount is the number of items in the adaptor
     * @return true if there is a need, false otherwise
     */
    private boolean needToLoadItems( int firstVisibleItem, int visibleItemCount, int totalItemCount )
    {
        return !isLoadingItems && totalItemCount != 0 &&
                totalItemCount - (visibleItemCount + firstVisibleItem) < visibleItemCount / 2;
    }
    
    /**
     * Adds the next few items to the total list
     * @param nextPage is the next few items to be added
     */
    private void addMoreItems( BackendlessCollection<Item> nextPage )
    {
        totalItems.addAll( nextPage.getCurrentPage() );
        adapter.notifyDataSetChanged();
    }

}

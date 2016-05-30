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
            @Override
            public void onScrollStateChanged( AbsListView view, int scrollState ) { }

            @Override
            public void onScroll( AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount )
            {
                if( needToLoadItems( firstVisibleItem, visibleItemCount, totalItemCount ) )
                {
                    isLoadingItems = true;

                    itemsBackendless.nextPage(new LoadingCallback<BackendlessCollection<Item>>( ItemListingActivity.this )
                    {
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
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent( ItemListingActivity.this, CameraActivity.class );
                startActivity( cameraIntent );
            }
        });
    }

    private boolean needToLoadItems( int firstVisibleItem, int visibleItemCount, int totalItemCount )
    {
        return !isLoadingItems && totalItemCount != 0 &&
                totalItemCount - (visibleItemCount + firstVisibleItem) < visibleItemCount / 2;
    }

    private void addMoreItems( BackendlessCollection<Item> nextPage )
    {
        totalItems.addAll( nextPage.getCurrentPage() );
        adapter.notifyDataSetChanged();
    }

}

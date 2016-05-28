package com.eksell.eksell.eksell;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AbsListView;
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

public class ItemListingActivity extends AppCompatActivity {

    private final int pageSize = 3;
    private BackendlessCollection<Item> itemsBackendless;
    private List<Item> totalItems = new ArrayList<>();
    private boolean isLoadingItems = false;
    private ItemAdaptor adapter;
    ListView itemListView;

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
    }

    private boolean needToLoadItems( int firstVisibleItem, int visibleItemCount, int totalItemCount )
    {
        return !isLoadingItems && totalItemCount != 0 && totalItemCount - (visibleItemCount + firstVisibleItem) < visibleItemCount / 2;
    }

    private void addMoreItems( BackendlessCollection<Item> nextPage )
    {
        totalItems.addAll( nextPage.getCurrentPage() );
        adapter.notifyDataSetChanged();
    }

}

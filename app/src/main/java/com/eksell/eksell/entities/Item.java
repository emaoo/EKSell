package com.eksell.eksell.entities;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.BackendlessDataQuery;

import java.io.Serializable;

public class Item implements Serializable
{
    private String Name;
    private String Description;
    private String ImageUrl;
    private java.util.Date updated;
    private java.util.Date created;
    private String objectId;
    private Integer Price;
    private String ownerId;
    private BackendlessUser Seller;
    public String getName()
    {
        return Name;
    }

    public void setName( String Name )
    {
        this.Name = Name;
    }

    public String getDescription()
    {
        return Description;
    }

    public void setDescription( String Description )
    {
        this.Description = Description;
    }

    public String getImageUrl()
    {
        return ImageUrl;
    }

    public void setImageUrl( String ImageUrl )
    {
        this.ImageUrl = ImageUrl;
    }

    public java.util.Date getUpdated()
    {
        return updated;
    }

    public java.util.Date getCreated()
    {
        return created;
    }

    public String getObjectId()
    {
        return objectId;
    }

    public Integer getPrice()
    {
        return Price;
    }

    public void setPrice( Integer Price )
    {
        this.Price = Price;
    }

    public String getOwnerId()
    {
        return ownerId;
    }

    public BackendlessUser getSeller()
    {
        return Seller;
    }

    public void setSeller( BackendlessUser Seller )
    {
        this.Seller = Seller;
    }


    public Item save()
    {
        return Backendless.Data.of( Item.class ).save( this );
    }

    public Future<Item> saveAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Item> future = new Future<Item>();
            Backendless.Data.of( Item.class ).save( this, future );

            return future;
        }
    }

    public void saveAsync( AsyncCallback<Item> callback )
    {
        Backendless.Data.of( Item.class ).save( this, callback );
    }

    public Long remove()
    {
        return Backendless.Data.of( Item.class ).remove( this );
    }

    public Future<Long> removeAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Long> future = new Future<Long>();
            Backendless.Data.of( Item.class ).remove( this, future );

            return future;
        }
    }

    public void removeAsync( AsyncCallback<Long> callback )
    {
        Backendless.Data.of( Item.class ).remove( this, callback );
    }

    public static Item findById( String id )
    {
        return Backendless.Data.of( Item.class ).findById( id );
    }

    public static Future<Item> findByIdAsync( String id )
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Item> future = new Future<Item>();
            Backendless.Data.of( Item.class ).findById( id, future );

            return future;
        }
    }

    public static void findByIdAsync( String id, AsyncCallback<Item> callback )
    {
        Backendless.Data.of( Item.class ).findById( id, callback );
    }

    public static Item findFirst()
    {
        return Backendless.Data.of( Item.class ).findFirst();
    }

    public static Future<Item> findFirstAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Item> future = new Future<Item>();
            Backendless.Data.of( Item.class ).findFirst( future );

            return future;
        }
    }

    public static void findFirstAsync( AsyncCallback<Item> callback )
    {
        Backendless.Data.of( Item.class ).findFirst( callback );
    }

    public static Item findLast()
    {
        return Backendless.Data.of( Item.class ).findLast();
    }

    public static Future<Item> findLastAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Item> future = new Future<Item>();
            Backendless.Data.of( Item.class ).findLast( future );

            return future;
        }
    }

    public static void findLastAsync( AsyncCallback<Item> callback )
    {
        Backendless.Data.of( Item.class ).findLast( callback );
    }

    public static BackendlessCollection<Item> find( BackendlessDataQuery query )
    {
        return Backendless.Data.of( Item.class ).find( query );
    }

    public static Future<BackendlessCollection<Item>> findAsync( BackendlessDataQuery query )
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<BackendlessCollection<Item>> future = new Future<BackendlessCollection<Item>>();
            Backendless.Data.of( Item.class ).find( query, future );

            return future;
        }
    }

    public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<Item>> callback )
    {
        Backendless.Data.of( Item.class ).find( query, callback );
    }
}
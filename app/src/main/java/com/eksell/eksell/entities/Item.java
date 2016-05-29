package com.eksell.eksell.entities;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;

import java.io.Serializable;

public class Item implements Serializable
{
    private String Name;
    private String Description;
    private String ImageUrl;
    private java.util.Date updated;
    private java.util.Date created;
    private Integer Price;
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

    public Integer getPrice()
    {
        return Price;
    }

    public void setPrice( Integer Price )
    {
        this.Price = Price;
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

    public void saveAsync( AsyncCallback<Item> callback )
    {
        Backendless.Data.of( Item.class ).save( this, callback );
    }

    public static Item findById( String id )
    {
        return Backendless.Data.of( Item.class ).findById( id );
    }
}
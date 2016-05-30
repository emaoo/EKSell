package com.eksell.eksell.entities;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;

import java.io.Serializable;

public class Item implements Serializable
{
    /**
     * Item name
     */
    private String Name;

    /**
     * Item description
     */
    private String Description;

    /**
     * Image url
     */
    private String ImageUrl;

    /**
     * Date the item characteristics last updated
     */
    private java.util.Date updated;

    /**
     * Date the item characteristics were created
     */
    private java.util.Date created;

    /**
     * Price of the item
     */
    private Integer Price;

    /**
     * The seller of the item
     */
    private BackendlessUser Seller;

    /**
     * Returns the name of the item
     * @return the name
     */
    public String getName()
    {
        return Name;
    }

    /**
     * Sets the name of the item
     * @param Name the name
     */
    public void setName( String Name )
    {
        this.Name = Name;
    }

    /**
     * Returns the description
     * @return description
     */
    public String getDescription()
    {
        return Description;
    }

    /**
     * Sets the description
     * @param Description of the item
     */
    public void setDescription( String Description )
    {
        this.Description = Description;
    }

    /**
     * Returns the image URL
     * @return image URL
     */
    public String getImageUrl()
    {
        return ImageUrl;
    }

    /**
     * Sets the image URL
     * @param ImageUrl of the item
     */
    public void setImageUrl( String ImageUrl )
    {
        this.ImageUrl = ImageUrl;
    }

    /**
     * Returns the date last updated
     * @return updated (last date)
     */
    public java.util.Date getUpdated()
    {
        return updated;
    }

    /**
     * Returns the date last created
     * @return created (last date)
     */
    public java.util.Date getCreated()
    {
        return created;
    }

    /**
     * Returns the price
     * @return price of the item
     */
    public Integer getPrice()
    {
        return Price;
    }

    /**
     * Sets the price of the item
     * @param Price of the item
     */
    public void setPrice( Integer Price )
    {
        this.Price = Price;
    }

    /**
     * Returns the sender
     * @return seller
     */
    public BackendlessUser getSeller()
    {
        return Seller;
    }

    /**
     *
     * @param Seller
     */
    public void setSeller( BackendlessUser Seller )
    {
        this.Seller = Seller;
    }

    /**
     * Saves the data
     * @returnn Data
     */
    public Item save()
    {
        return Backendless.Data.of( Item.class ).save( this );
    }

    /**
     * Saves the data in an asynchronous manner
     * @param callback Data saved with callback
     */
    public void saveAsync( AsyncCallback<Item> callback )
    {
        Backendless.Data.of( Item.class ).save( this, callback );
    }

    /**
     * Finds the item by its ID
     * @param id identification
     * @return the Item object corresponding to the ID given
     */
    public static Item findById( String id )
    {
        return Backendless.Data.of( Item.class ).findById( id );
    }
}

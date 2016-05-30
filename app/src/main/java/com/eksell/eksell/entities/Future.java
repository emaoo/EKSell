package com.eksell.eksell.entities;

import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;

/**
 * A class required by Backendless to work things in the background and display errors
 *
 * @author Sergey Chupov (Backendless admin), Documentation by Katherine Xiao
 * @version February 25, 2015
 *
 */
public class Future<T> implements AsyncCallback<T>
{
    /**
     * the result
     */
    private T result;
    
    /**
     * runtime exception
     */
    private RuntimeException exception;

     /**
     * if the run is complete
     */
    private boolean complete;
    
    /**
     * if fault exists
     */
    private boolean fault;
    
    /**
     * waits for completion, and if there is an error, throws the exception
     * @return result
     * @throws InterruptedException if the process is interrupted
     */
    public synchronized T get() throws InterruptedException
    {
        while( !complete )
        {
            wait();
        }

        if( fault )
        {
            throw exception;
        }

        return result;
    }
    
    /**
     * sets the results is successful
     * @param t the response
     */
    public synchronized void set( T t )
    {
        result = t;
        complete = true;
        notifyAll();
    }
    
    /**
     * response to an error
     * @param e the runtime exception
     */
    public synchronized void fault( RuntimeException e )
    {
        exception = e;
        fault = true;
        complete = true;
        notifyAll();
    }
    
    /**
     * if the process is complete
     * @return if the process is complete
     */
    public boolean isComplete()
    {
        return complete;
    }
    
    /**
     * if there is an error
     * @return if there is an error
     */
    public boolean isFault()
    {
        return fault;
    }
    
    /**
     * handles the response if it is successful
     * @param response is the response retrieved by the server
     */
    @Override
    public void handleResponse( T response )
    {
        set( response );
    }
    
    /**
     * handles the fault
     * @param fault the fault retrieved by the server
     */
    @Override
    public void handleFault( BackendlessFault fault )
    {
        fault( new BackendlessException( fault.getCode(), fault.getMessage() ) );
    }
}

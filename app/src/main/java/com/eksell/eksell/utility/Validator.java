package com.eksell.eksell.utility;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;
import com.eksell.eksell.eksell.R;

import java.util.Random;
import java.util.Stack;

/**
 * A Supporting class with static methods to help the LogIn and Register Activities to verify if the entries are valid
 * 
 * @author Katherine Xiao, Sean Meng
 * @version May 10, 2016
 * 
 * @author Period - 3
 * @author Assignment - EKSell
 * 
 * @author Sources - StackOverflow (for email matches pattern)
 */
public class Validator
{
  /**
   * Checks to see if the name is valid (not empty)
   * @param currentContext is the current state of the object
   * @param name is the entered name
   * @return true if name is valid, false otherwise (and displays a Toast)
     */
  public static boolean isNameValid( Context currentContext, CharSequence name )
  {
    if( name.toString().isEmpty() )
    {
      Toast.makeText( currentContext, currentContext.getString( R.string.warning_name_empty ), Toast.LENGTH_LONG ).show();
      return false;
    }

    return true;
  }

    public static boolean isNameValid(String name)
    {
        return(name != null && !name.isEmpty());
    }
  /**
   * Checks to see if the email is valid (not empty and follows email format)
   * @param currentContext is the current state of the object
   * @param email is the entered email
   * @return true if email is valid, false otherwise (and displays a Toast based on the type of
   * error
   */
  public static boolean isEmailValid( Context currentContext, CharSequence email )
  {
      String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    if( email.toString().isEmpty() )
    {
      Toast.makeText( currentContext, currentContext.getString( R.string.warning_email_empty ), Toast.LENGTH_LONG ).show();
      return false;
    }

    if( !email.toString().matches(emailPattern))
    {
      Toast.makeText( currentContext, currentContext.getString( R.string.warning_email_invalid ), Toast.LENGTH_LONG ).show();
      return false;
    }

    return true;
  }

  /**
   * Checks to see if the password is valid (not empty)
   * @param currentContext is the current state of the object
   * @param password is the entered password
   * @return if password is valid
   */
  public static boolean isPasswordValid( Context currentContext, CharSequence password )
  {
    if( password.toString().isEmpty() )
    {
      Toast.makeText( currentContext, currentContext.getString( R.string.warning_password_empty ), Toast.LENGTH_LONG ).show();
      return false;
    }

    return true;
  }
  /**
     * Checks to see if the password matches the confirmation password
     * @param currentContext is the current state of the object
     * @param password is the password entered
     * @param passwordConfirm is the confirmation of the password
     * @return true if the passwords match, false otherwise
     */
  public static boolean isPasswordsMatch( Context currentContext, CharSequence password, CharSequence passwordConfirm )
  {
    if( !password.toString().equals(passwordConfirm.toString()) )
    {
      Toast.makeText( currentContext, currentContext.getString( R.string.warning_passwords_do_not_match ), Toast.LENGTH_LONG ).show();
      return false;
    }

    return true;
  }

    /**
     * Checks to see if the user is a robot or not by verifying if the entered text is the reverse
     * of the text displayed
     * @param currentContext is the current state of the object
     * @param checkCaptcha is the text of the randomly generated numbers displayed
     * @param enteredText is the text entered by the user
     * @return true if the entered text is the reverse of the displayed one, false otherwise
     */
    public static boolean isNotRobot(Context currentContext, CharSequence checkCaptcha, CharSequence enteredText)
    {
        Stack secondStack = new Stack();
        Stack firstStack = new Stack();

        for(int i = 0; i < 5; i++)
        {
            firstStack.push(checkCaptcha.toString().substring(i, i+1));
        }

        String afterFlip = "";
        for (int i=0; i<5; i++)
        {
            Integer x =  Integer.valueOf(firstStack.pop().toString());
            secondStack.push(x);

            afterFlip += x.toString();
        }

        if (!enteredText.toString().isEmpty() && !enteredText.toString().equals(afterFlip))
        {
            Toast.makeText(currentContext, "You are a robot", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    //end
}

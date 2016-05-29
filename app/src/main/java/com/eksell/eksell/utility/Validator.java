package com.eksell.eksell.utility;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;
import com.eksell.eksell.eksell.R;
/**
 * A Supporting class with static methods to help the LogIn and Register Activities to verify if the entries are valid
 * 
 * @author Eileen Mao
 * @version May 10, 2016
 * 
 * @author Period - 3
 * @author Assignment - EKSell
 * 
 * @author Sources - None
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
  /**
   * Checks to see if the email is valid (not empty and follows email format)
   * @param currentContext is the current state of the object
   * @param email is the entered email
   * @return true if email is valid, false otherwise (and displays a Toast based on the type of
   * error
   */
  public static boolean isEmailValid( Context currentContext, CharSequence email )
  {
    if( email.toString().isEmpty() )
    {
      Toast.makeText( currentContext, currentContext.getString( R.string.warning_email_empty ), Toast.LENGTH_LONG ).show();
      return false;
    }

    if( !Patterns.EMAIL_ADDRESS.matcher( email ).matches() )
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
     * @param password is the password entered
     * @param passwordConfirm is the confirmation of the password
     * @return true if the passwords match, false otherwise
     */
  public static boolean isPasswordsMatch( Context currentContext, CharSequence password, CharSequence passwordConfirm )
  {
    if( !TextUtils.equals( password, passwordConfirm ) )
    {
      Toast.makeText( currentContext, currentContext.getString( R.string.warning_passwords_do_not_match ), Toast.LENGTH_LONG ).show();
      return false;
    }

    return true;
  }
}

package com.eksell.eksell.utility;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;
import com.eksell.eksell.eksell.R;

public class Validator
{
  public static boolean isNameValid( Context currentContext, CharSequence name )
  {
    if( name.toString().isEmpty() )
    {
      Toast.makeText( currentContext, currentContext.getString( R.string.warning_name_empty ), Toast.LENGTH_LONG ).show();
      return false;
    }

    return true;
  }

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

  public static boolean isPasswordValid( Context currentContext, CharSequence password )
  {
    if( password.toString().isEmpty() )
    {
      Toast.makeText( currentContext, currentContext.getString( R.string.warning_password_empty ), Toast.LENGTH_LONG ).show();
      return false;
    }

    return true;
  }

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

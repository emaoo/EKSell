package com.eksell.eksell.eksell;

   import android.content.Context;
   import android.text.TextUtils;
    import android.util.Patterns;
    import android.widget.Toast;

   import java.util.Stack;

    /**
     * JUnit Testing class for the Validator class (gets rid of the Toasts in Validator)
     *
     * @author Sean Meng
     * @version May 29, 2016
     *
     * @author Period - 3
     * @author Assignment - EKSell
     *
     * @sources - None
     */
    public class TestValidator
    {

        /**
         * Checks to see if the name is valid (not empty)
         * @param name is the entered name
         * @return true if name is valid, false otherwise (and displays a Toast)
         */
        public static boolean isNameValid( CharSequence name )
        {
            return !name.toString().isEmpty();
        }
        /**
         * Checks to see if the email is valid (not empty and follows email format)
         * @param email is the entered email
         * @return true if email is valid, false otherwise (and displays a Toast based on the type of
         * error
         */
        public static boolean isEmailValid( CharSequence email )
        {
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            return !email.toString().isEmpty() && email.toString().matches(emailPattern);
        }

        /**
         * Checks to see if the password is valid (not empty)
         * @param password is the entered password
         * @return if password is valid
         */
        public static boolean isPasswordValid( CharSequence password )
        {
            return !password.toString().isEmpty();
        }
        /**
         * Checks to see if the password matches the confirmation password
         * @param password is the password entered
         * @param passwordConfirm is the confirmation of the password
         * @return true if the passwords match, false otherwise
         */
        public static boolean isPasswordsMatch( CharSequence password, CharSequence passwordConfirm )
        {
            return password.toString().equals(passwordConfirm.toString());
        }

        public static boolean isNotRobot( CharSequence checkCaptcha, CharSequence enteredText)
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
                return false;
            }
            return true;
        }
    }


package com.grew.sw.cashlawn.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


public class SoftKeyboardUtils {
    public static void hideSoftKeyboard(Context context){
        View focus_view = ((Activity)context).getCurrentFocus();
        if(focus_view != null){
            InputMethodManager inputManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(focus_view.getWindowToken(), 0);
        }
    }

}

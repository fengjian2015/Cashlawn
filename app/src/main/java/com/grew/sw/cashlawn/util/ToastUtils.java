package com.grew.sw.cashlawn.util;

import android.widget.Toast;
import com.grew.sw.cashlawn.App;


public class ToastUtils {
   public static void showLong(String message){
       try {
           Toast.makeText(App.get(),message,Toast.LENGTH_LONG).show();
       }catch (Exception e){
           e.printStackTrace();
       }
   }

   public static void showShort(String message){
       try {
           Toast.makeText(App.get(),message,Toast.LENGTH_SHORT).show();
       }catch (Exception e){
           e.printStackTrace();
       }
   }
}

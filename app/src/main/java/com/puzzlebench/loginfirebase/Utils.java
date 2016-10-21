package com.puzzlebench.loginfirebase;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created on 22/09/16.
 */
public class Utils {

    public static Boolean isConnected(Context context)
    {
        Boolean rtaisConnected  =false;
        ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            rtaisConnected=true;
        }
        return rtaisConnected;
    }

    public static void  setShapeReference(Activity activity,String key, String value )
    {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();

    }
    public  static  String getShapePreference(Activity activity,String key)
    {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);

        return sharedPref.getString(key, key);
    }
}

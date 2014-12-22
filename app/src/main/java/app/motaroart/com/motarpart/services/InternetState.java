package app.motaroart.com.motarpart.services;

/**
 * Created by AnilU on 22-12-2014.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by AnilU on 22-12-2014.
 */
public class InternetState {

    public static boolean getState(Context con)
    {
        ConnectivityManager conMgr = (ConnectivityManager)con.getSystemService(Context.CONNECTIVITY_SERVICE);

        if ( conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING ) {

            return true;

        }
        else if ( conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED
                || conMgr.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {

            return false;
        }
        return false;
    }
}


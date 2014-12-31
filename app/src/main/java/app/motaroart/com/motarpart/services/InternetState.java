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
        ConnectivityManager connManager = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            return true;
        }
        else
        {
            return false;

        }
    }
}


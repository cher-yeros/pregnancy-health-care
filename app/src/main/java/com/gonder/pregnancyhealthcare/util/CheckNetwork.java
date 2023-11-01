package com.gonder.pregnancyhealthcare.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;

import androidx.annotation.NonNull;

import java.net.InetAddress;

public class CheckNetwork {
    Context context;

    public CheckNetwork(Context context) {
        this.context = context;
    }

    public void registerNetworkCallback() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                NetworkRequest.Builder builder = new NetworkRequest.Builder();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    assert connectivityManager != null;
                    connectivityManager.registerDefaultNetworkCallback(
                            new ConnectivityManager.NetworkCallback(){
                                @Override
                                public void onAvailable(@NonNull Network network) {
                                    Utility.isNetworkConnected = true; // Global Static Variable
                                }
                                @Override
                                public void onLost(@NonNull Network network) {
                                    Utility.isNetworkConnected = false; // Global Static Variable
                                }
                            }
                    );


                }
            }


            Utility.isNetworkConnected = false;
        }catch (Exception e){
            Utility.isNetworkConnected = false;
        }
    }

    public static boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");
        } catch (Exception e) {
            return false;
        }
    }
}

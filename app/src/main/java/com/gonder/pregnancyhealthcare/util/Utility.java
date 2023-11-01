package com.gonder.pregnancyhealthcare.util;

import android.content.Context;
import android.widget.Toast;

import com.gonder.pregnancyhealthcare.util.util.Etc;

public class Utility {
    public static String PATH_ADMINS = "Admins";
    public static String PATH_MOTHERES = "Mothers";
    public static String PATH_LABRESULTS = "Lab Results";
    public static String PATH_PHYSICIANS = "Physicians";
    public static String PATH_LABTECHNICIANS = "Lab Technicians";
    public static String PATH_NOTIFICATIONS = "Notifications";

    public static void showToast(Context context,String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static Etc toETc  (long gregDate) {
        Etc dateTime = new Etc();
        Etc dateTime1 = dateTime.fromMillisecondsSinceEpoch(gregDate);
        return dateTime1;
    };

    public static void makeCall() {

    }

    public static void sendSMS() {

    }

    public static void makeNotification() {

    }

    public static boolean isNetworkConnected = false;
}
